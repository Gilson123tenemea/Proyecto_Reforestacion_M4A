package com.example.demo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.IRegistroActividadRealizadaDao;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.RegistroActividadRealizadaService;
import com.example.demo.service.IVoluntariosService;

@Controller
@RequestMapping("/registro-actividad")
public class RegistroActividadRealizadaController {

	@Autowired
	private RegistroActividadRealizadaService registroActividadService;

	@Autowired
	private IVoluntariosService voluntariosService;

	@Autowired
	private IRegistroActividadRealizadaDao iregistroActividadRealizadaDao;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	}

///////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/crear")
	public String mostrarActividad(@RequestParam(value = "id_voluntario", required = false) Long voluntarioId,
			Model model) {
		System.out.println("📢 Método mostrarActividad iniciado");

		model.addAttribute("actividad", new RegistroActividadRealiza());
		model.addAttribute("voluntarios", voluntariosService.findAll());

		if (voluntarioId != null) {
			System.out.println("📌 ID del voluntario recibido: " + voluntarioId);
			model.addAttribute("id_voluntario", voluntarioId); // Agregar al modelo

			List<Object[]> resultados = registroActividadService.findActividadesRealizadas2(voluntarioId);
			System.out.println("🔍 Resultados obtenidos: " + resultados.size());

			if (!resultados.isEmpty()) {
				Object[] datos = resultados.get(0);
				model.addAttribute("voluntarioNombre", datos[4]);
				model.addAttribute("actividadNombre", datos[0]);
				model.addAttribute("actividadDuracion", datos[1]);
				model.addAttribute("proyectoNombre", datos[2]);
				model.addAttribute("equipoNombre", datos[3]);

				System.out.println("✅ Datos agregados al modelo correctamente.");
			} else {
				System.out.println(
						"⚠️ No se encontraron actividades realizadas para el voluntario con ID: " + voluntarioId);
			}
		} else {
			System.out.println("⚠️ No se proporcionó un ID de voluntario en la solicitud.");
		}

		return "crearRegistroActividadRealizada";
	}

	@PostMapping("/guardarActividad")
	public String guardarActividad(@RequestParam("id_voluntario") Long voluntarioId,
			@RequestParam("cantidad_realizada") Integer cantidadRealizada,
			@RequestParam("descripcion") String descripcion,
			@RequestParam(value = "validacion_admin_tareaRealizada", defaultValue = "false") boolean validacionAdmin,
			@RequestParam(value = "validacion_voluntario_tareaRealizada", defaultValue = "false") boolean validacionVoluntario,
			@RequestParam(value = "foto", required = false) MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			RegistroActividadRealiza registroActividad = new RegistroActividadRealiza();
			registroActividad.setId_voluntario(voluntarioId);
			registroActividad.setCantidad_realizada(cantidadRealizada);
			registroActividad.setDescripcion(descripcion);
			registroActividad.setValidacion_admin_tareaRealizada(validacionAdmin);
			registroActividad.setValidacion_voluntario_tareaRealizada(validacionVoluntario);

			if (file != null && !file.isEmpty()) {
				registroActividad.setFoto(file.getBytes());
			}

			registroActividadService.save(registroActividad);
			redirectAttributes.addFlashAttribute("mensaje", "Actividad guardada con éxito");

		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al subir la imagen.");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado.");
		}

		return "redirect:/registro-actividad/crear?id_voluntario=" + voluntarioId;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping("/{voluntarioId}")
	public String mostrarFormulario(@PathVariable Long voluntarioId, Model model) {
		List<Object[]> datosActividad = iregistroActividadRealizadaDao.findActividadesRealizadas(voluntarioId);
		if (!datosActividad.isEmpty()) {
			Object[] actividad = datosActividad.get(0);

			model.addAttribute("voluntarioNombre", actividad[0]);
			model.addAttribute("proyectoNombre", actividad[2]);
			model.addAttribute("areaNombre", actividad[1]);
			model.addAttribute("intervencionSueloId", actividad[3]);
			model.addAttribute("actividadNombre", actividad[4]);
		}

		model.addAttribute("voluntarioId", voluntarioId);
		return "crearRegistroActividadRealizada";
	}

	@GetMapping("/editar")
	public String editarRegistroActividad(@RequestParam(value = "id", required = false) Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<RegistroActividadRealiza> registroOpt = registroActividadService.findOne(id);

		if (!registroOpt.isPresent()) {
			redirectAttributes.addFlashAttribute("error", "El registro de actividad no existe");
			return "redirect:/registro-actividad/crear";
		}

		RegistroActividadRealiza registro = registroOpt.get();

		model.addAttribute("actividad", registro);
		model.addAttribute("voluntarios", voluntariosService.findAll());
		model.addAttribute("idRegistro", id);

		return "editarRegistroActividadRealizada";
	}

	
	
	
	//-------------------------
	
	@PostMapping("/actualizar")
	public String actualizarActividad(
	    @RequestParam("id_registroactividadrealizada") Long id,
	    @RequestParam("cantidad_realizada") Integer cantidadRealizada,@RequestParam("descripcion") String descripcion,
	    @RequestParam("foto") MultipartFile foto,  // Aquí se recibe el MultipartFile
	    RedirectAttributes redirectAttributes) {
	    
	    try {
	        // Crear el objeto de actividad o actualizarlo según sea necesario
	        Optional<RegistroActividadRealiza> actividadPrim = iregistroActividadRealizadaDao.findOne(id);
	        RegistroActividadRealiza actividad = actividadPrim.get();
	        actividad.setCantidad_realizada(cantidadRealizada);
	        actividad.setDescripcion(descripcion);
	        
	        // Validar si el archivo foto no está vacío
	        if (foto != null && !foto.isEmpty()) {
	            byte[] fotoBytes = foto.getBytes();  // Convierte el archivo a byte[]
	            actividad.setFoto(fotoBytes);  // Asignar el byte[] a la propiedad foto
	        }

	  
	        iregistroActividadRealizadaDao.save(actividad);

	        // Mensaje de éxito
	        redirectAttributes.addFlashAttribute("mensaje", "Actividad actualizada correctamente.");
	    } catch (IOException e) {
	        redirectAttributes.addFlashAttribute("error", "Error al procesar la imagen.");
	    }

	    return "redirect:/actividades/voluntario";  // Redirige a la vista de actualización
	}


	//-----------------
	
	
	
	@PostMapping("/actualizarrrrrr")
	public String actualizarActividadd(@RequestParam("id_registroactividadrealizada") Long id,
			@RequestParam("cantidad_realizada") Integer cantidadRealizada,
			@RequestParam("descripcion") String descripcion,
			@RequestParam(value = "foto", required = false) MultipartFile file, RedirectAttributes redirectAttributes
			) {

		Optional<RegistroActividadRealiza> registroOpt = registroActividadService.findById(id);

		if (!registroOpt.isPresent()) {
			redirectAttributes.addFlashAttribute("error", "El registro no existe");
			return "redirect:/registro-actividad/crear";
		}

		try {
			RegistroActividadRealiza registro = registroOpt.get();
			registro.setCantidad_realizada(cantidadRealizada);
			registro.setDescripcion(descripcion);

			if (file != null && !file.isEmpty()) {
				registro.setFoto(file.getBytes());
			}

			registroActividadService.save(registro);
			redirectAttributes.addFlashAttribute("mensaje", "Actividad actualizada con éxito");

			return "redirect:/registro-actividad/crear";

		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Error al actualizar la imagen");
			return "redirect:/registro-actividad/editar/" + id;
		}
	}

	@GetMapping("/{voluntarioId}/{idActividad}")
	public String mostrarFormulario(@PathVariable Long idVoluntario, @PathVariable Long intervencionSueloId,
			Model model) {
		model.addAttribute("idVoluntario", idVoluntario);
		model.addAttribute("idIntervencionSuelo", intervencionSueloId);

		return "crearRegistroActividadRealizada";
	}
	
	
	@GetMapping("/confirmar_actividadreal")
	public String mostrarActividadesPendientes(Model model) {
	    List<RegistroActividadRealiza> actividades = registroActividadService.findAllActividades();
	    model.addAttribute("actividades", actividades);
	    return "confirmar_actividadreal"; 
	}

	@GetMapping("/confirmar_actividad/{id}")
	public String confirmarActividad(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    registroActividadService.confirmarActividad(id);
	    redirectAttributes.addFlashAttribute("mensaje", "Actividad confirmada correctamente.");
	    return "redirect:/registro-actividad/confirmar_actividadreal";
	}
	
}
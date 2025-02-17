package com.example.demo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.demo.entity.Administrador;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.RegistroActividadRealizadaService;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.IUsuarioServices;
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

	@Autowired
	private IProyectoServices proyectoService;
	@Autowired
	private IAdministradorServices administradorService;

	@Autowired
	private IUsuarioServices usuarioService;
	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	}

///////////////////////////////////////////////////////////////////////////////////////
	/*@GetMapping("/crear")
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
	}*/
	@GetMapping("/crear")
	public String mostrarActividad(
			@RequestParam("id_actividad") Long idactividad,
			@SessionAttribute("idVoluntario") Long voluntarioId,
			Model model) {
		System.out.println("📢 Método mostrarActividad iniciado");
		System.out.println("📢 Id Actividad ======================" +idactividad);
		model.addAttribute("actividad", new RegistroActividadRealiza());
		model.addAttribute("voluntarios", voluntariosService.findAll());
		model.addAttribute("Id_Tipo_Actividad", idactividad);

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


/*	@PostMapping("/guardarActividad")
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
	}*/
	
	@PostMapping("/guardarActividad")
	public String guardarActividad(
			@RequestParam(value ="id_tipo_actividad") Long idactividad,
			@SessionAttribute("idVoluntario") Long idVoluntario,
	        @RequestParam("cantidad_realizada") Integer cantidadRealizada,
	        @RequestParam("descripcion") String descripcion,
	        @RequestParam(value = "validacion_admin_tareaRealizada", defaultValue = "false") boolean validacionAdmin,
	        @RequestParam(value = "validacion_voluntario_tareaRealizada", defaultValue = "false") boolean validacionVoluntario,
	        @RequestParam(value = "foto", required = false) MultipartFile file, 
	        RedirectAttributes redirectAttributes) {

		

		
	    try {
	        // Obtener el ID de tipo de actividad basado en el voluntario
	        List<Object[]> actividades = iregistroActividadRealizadaDao.findActividadesRealizadas(idVoluntario);
	      //  Long idTipoActividades = actividades.
	        		;

	        RegistroActividadRealiza registroActividad = new RegistroActividadRealiza();
	        registroActividad.setId_voluntario(idVoluntario);
	        registroActividad.setId_tipoActividades(idactividad); // Asignamos el ID de la actividad
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

	    return "redirect:/actividades/voluntario";
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
	//-------------------------=======================================================

		@GetMapping("/Infor")
		public String InfoRegistroActividad(
				@SessionAttribute("idVoluntario") Long idVoluntario,
				@RequestParam(value = "id", required = false) Long id, 
				Model model, 
				RedirectAttributes redirectAttributes) {
			
			
			List<Object[]> resultados = iregistroActividadRealizadaDao.findInfo_RegistroRealizado(idVoluntario, id);
			

	   
			if (!resultados.isEmpty()) {
				Object[] datos = resultados.get(0);
				model.addAttribute("UsuarioNombre", datos[0]);
				model.addAttribute("ProyectoNombre", datos[1]);
				model.addAttribute("CantidadRealizada", datos[2]);
				// model.addAttribute("foto", datos[3]);
				model.addAttribute("EquipoNombre", datos[4]);
				model.addAttribute("TipoActividadNombre", datos[5]);
				
				model.addAttribute("valAdmin", datos[6]);
				model.addAttribute("ValVoluntario", datos[7]);
				model.addAttribute("Id_RegistroAdtividades", datos[8]);
				model.addAttribute("Id_TipoActividades", datos[9]);
				model.addAttribute("duracion", datos[10]);
				
				if (datos[11] != null) {
				Administrador Admin = administradorService.findOne((Long)datos[11]);		
				Usuarios usuarioAdmin =usuarioService.findOne(Admin.getId_usuarios());
				
				model.addAttribute("AdminNombre",usuarioAdmin.getNombre());	
				}
				
				model.addAttribute("descripcion", datos[12]);
				

				System.out.println("✅ Datos agregados al modelo correctamente.");
			} else {
				System.out.println(
						"⚠️ No se encontraron actividades realizadas para el voluntario con ID: " + idVoluntario);
			}
			
			

			return "Info_RegistroActvRealizadas";
		}
		
		
		
		
		//-------------------------=======================================================

	
	
	
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
	
	
	@GetMapping("/EdicionActivRealizada/imagen/{id}")
	@ResponseBody
	public byte[] obtenerImagenProyecto(@PathVariable("id") Long id) {
	    Optional<RegistroActividadRealiza> registroOpt = iregistroActividadRealizadaDao.findOne(id);
	    
	    if (registroOpt.isPresent() && registroOpt.get().getFoto() != null) {
	        return registroOpt.get().getFoto();
	    }
	    // Puedes retornar un arreglo vacío o una imagen por defecto
	    return new byte[0]; // Retorna un arreglo vacío si no hay imagen
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
	    List<Map<String, Object>> actividadesConPorcentaje = new ArrayList<>();

	    for (RegistroActividadRealiza actividad : actividades) {
	        List<Double> porcentajes = registroActividadService.obtenerPorcentajesPorTipoActividad(actividad.getId_tipoActividades());
	        Double porcentaje = (porcentajes.isEmpty()) ? 0.0 : porcentajes.get(0);

	        Map<String, Object> actividadMap = new HashMap<>();
	        actividadMap.put("actividad", actividad);
	        actividadMap.put("porcentaje", porcentaje);

	        actividadesConPorcentaje.add(actividadMap); 
	    }

	    model.addAttribute("actividadesConPorcentaje", actividadesConPorcentaje); 

	    return "confirmar_actividadreal"; 
	}

	@GetMapping("/confirmar_actividad/{id}")
	public String confirmarActividad(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    Optional<RegistroActividadRealiza> actividadOpt = registroActividadService.findOne(id);
	    
	    if (actividadOpt.isPresent()) {
	        RegistroActividadRealiza actividad = actividadOpt.get();
	        registroActividadService.confirmarActividad(id);
	        Long idTipoActividad = actividad.getId_tipoActividades();
	        List<Double> porcentajes = registroActividadService.obtenerPorcentajesPorTipoActividad(idTipoActividad);
	        Double porcentajeActividad = (porcentajes.isEmpty()) ? 0.0 : porcentajes.get(0);

	        Long idProyecto = registroActividadService.obtenerIdProyectoPorTipoActividad(idTipoActividad);

	        if (idProyecto != null) {
	            Proyecto proyecto = proyectoService.findOne(idProyecto); 
	            if (proyecto != null) { 
	                double nuevoPorcentaje = proyecto.getPorcentaje() + porcentajeActividad; 
	                proyecto.setPorcentaje(nuevoPorcentaje); 
	                proyectoService.save(proyecto); 
	            } else {
	                redirectAttributes.addFlashAttribute("error", "Proyecto no encontrado con ID: " + idProyecto);
	            }
	        } else {
	            redirectAttributes.addFlashAttribute("error", "ID de proyecto no encontrado para el tipo de actividad: " + idTipoActividad);
	        }

	        redirectAttributes.addFlashAttribute("mensaje", "Actividad confirmada correctamente y porcentaje actualizado en el proyecto.");
	    } else {
	        redirectAttributes.addFlashAttribute("error", "Actividad no encontrada.");
	    }

	    return "redirect:/registro-actividad/confirmar_actividadreal";
	}
	
	@GetMapping("/informacion_registroacti_real/{idRegistro}")
	public String informacionRegistroActiReal(@PathVariable Long idRegistro, Model model) {
	    List<Object[]> detalles = registroActividadService.obtenerDetallesPorRegistroNuevo(idRegistro);

	    // Obtener todos los voluntarios asignados a la actividad
	    List<Object[]> voluntarios = registroActividadService.obtenerVoluntariosPorActividad(idRegistro);

	    for (Object[] detalle : detalles) {
	        System.out.println(Arrays.toString(detalle));
	    }

	    model.addAttribute("detalles", detalles);
	    model.addAttribute("voluntarios", voluntarios); // Agregar voluntarios al modelo
	    return "informacion_registroacti_real";
	}

}
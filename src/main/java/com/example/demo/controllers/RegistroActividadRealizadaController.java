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
import com.example.demo.entity.Area;
import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.RegistroActividadRealizadaService;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.IParcelaService;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IPatrocinioService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.ISueloService;
import com.example.demo.service.IUsuarioServices;
import com.example.demo.service.IVoluntariosService;

@Controller
@SessionAttributes("idAdministrador")
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
	
	
	
	
	@Autowired
	private IUsuarioServices usuarioServices;
	
	@Autowired
	private IParcelaService parcelaService;

	@Autowired
	private IAreaServices areaService;

	@Autowired
	private IPatrocinioService patrocinioService;

	@Autowired
	private IPatrocinadorServices PatrocinadorService;
	
	  @Autowired
	    private ISueloService sueloservice;
	  
	  @Autowired
	    private EquiposServiceImpl equiposimpl;
	  
	  
	  
	

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
				
				
				//====================\
				
				Long id = (Long)datos[5];
				
				 Long idArea = 0L;
				    Long idPatrocinio = 0L;
				    Long idPatrocinador = 0L;

				    // Buscar el proyecto
				    Proyecto proyecto = proyectoService.findOne(id);
				    if (proyecto == null) {
				        model.addAttribute("error", "No se encontró el proyecto.");
				        return "listaProyectos";
				    }

				    // Obtener todas las listas
				    List<Patrocinio> patrocinioList = patrocinioService.findAll();
				    List<Area> areaList = areaService.findAll();
				    List<Parcelas> parcelaList = parcelaService.findAll();

				    List<Area> areaIngre = new ArrayList<>();
				    List<Parcelas> parcelaIngre = new ArrayList<>();

				    // Buscar Patrocinio relacionado
				    for (Patrocinio p : patrocinioList) {
				        if (p.getId_proyecto() != null && p.getId_proyecto().equals(id)) { // Verificación null
				            idPatrocinador = p.getId_patrocinador() != null ? p.getId_patrocinador() : 0L;
				            idPatrocinio = p.getId_patrocina() != null ? p.getId_patrocina() : 0L;
				        }
				    }

				    // Buscar Áreas relacionadas
				    for (Area a : areaList) {
				        if (a.getId_proyecto() != null && a.getId_proyecto().equals(id)) { // Verificación null
				            areaIngre.add(a);
				            idArea = a.getId_area() != null ? a.getId_area() : 0L;
				        }
				    }

				    // Buscar Parcelas relacionadas
				    for (Parcelas p : parcelaList) {
				        if (p.getId_area() != null && p.getId_area().equals(idArea)) { // Verificación null
				            parcelaIngre.add(p);
				        }
				    }

				    // Obtener información del patrocinador solo si hay un ID válido
				    Patrocinio patro = Optional.ofNullable(patrocinioService.findOne(idPatrocinio)).orElse(null);
				    Patrocinador patrocinador = patro != null ? 
				        Optional.ofNullable(PatrocinadorService.findOne(patro.getId_patrocinador())).orElse(null) 
				        : null;

				    if (patro != null && patrocinador != null) {
				        model.addAttribute("usuario", Optional.ofNullable(
				            usuarioServices.findOne(patrocinador.getId_usuarios())
				        ).orElse(null));
				    }

				    model.addAttribute("patrocinio", patro);
				    model.addAttribute("patrocinador", patrocinador);
				    model.addAttribute("proyecto", proyecto);
				    model.addAttribute("areas", areaIngre);
				    model.addAttribute("id_proyecto", id);
				    model.addAttribute("parcelas", parcelaIngre);

				    // Mapas de nombres de proyectos y suelos con validación de nulos
				    Map<Long, String> proyectoNombres = new HashMap<>();
				    Map<Long, String> sueloNombres = new HashMap<>();
				    
				    for (Parcelas parcela : parcelaIngre) {
				        String proyectoNombre = Optional.ofNullable(areaService.findProyectoNameByAreaId(parcela.getId_area()))
				                                        .orElse("No disponible");
				        String sueloNombre = Optional.ofNullable(sueloservice.findSueloName(parcela.getId_suelo()))
				                                     .orElse("No disponible");
				        
				        proyectoNombres.put(parcela.getId_parcelas(), proyectoNombre);
				        sueloNombres.put(parcela.getId_parcelas(), sueloNombre);
				    }

				    model.addAttribute("proyectoNombres", proyectoNombres);
				    model.addAttribute("sueloNombres", sueloNombres);
				
				
				
				
				
				
				
				
				//====================
				
				
				

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

		@GetMapping("/Infor_cumplido")
		public String InfoRegistroActividadCumplido(
				@SessionAttribute("idVoluntario") Long idVoluntario,
				@RequestParam(value = "id", required = false) Long id, 
				Model model, 
				RedirectAttributes redirectAttributes) {
			
			
			List<Object[]> resultados = iregistroActividadRealizadaDao.findInfo_RegistroRealizado_cumplido(idVoluntario, id);
			

	   
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
		
	    List<RegistroActividadRealiza> actividades = registroActividadService.findAll();
	    List<Map<String, Object>> actividadesConPorcentaje = new ArrayList<>();

	    for (RegistroActividadRealiza actividad : actividades) {
	        List<Double> porcentajes = registroActividadService.obtenerPorcentajesPorTipoActividad(actividad.getId_tipoActividades());
	        Double porcentaje = (porcentajes.isEmpty() || porcentajes.get(0) == null) ? 0.0 : porcentajes.get(0);

	        
	        // Obtener nombre y apellido del voluntario
	        List<Object[]> voluntarioInfo = iregistroActividadRealizadaDao.findVoluntariosPorActividad(actividad.getId_registroactividadrealizada());
	        String voluntarioNombre = "";
	        if (!voluntarioInfo.isEmpty()) {
	            Object[] info = voluntarioInfo.get(0);
	            voluntarioNombre = info[0] + " " + info[1]; // Suponiendo que el índice 0 es nombre y 1 es apellido
	        }

	        Map<String, Object> actividadMap = new HashMap<>();
	        actividadMap.put("actividad", actividad);
	        actividadMap.put("porcentaje", porcentaje);
	        actividadMap.put("voluntarioNombre", voluntarioNombre); // Agregar nombre del voluntario

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
	    Map<Long, List<Usuarios>> voluntariosPorEquipo = new HashMap<>();

	    for (Object[] detalle : detalles) {
	        Long id = (long) detalle[0];

	        List<Asignar_equipos> voluntariosEquipos = registroActividadService.listarvoluntariosporequipos(id);
	        List<Usuarios> voluntarios = new ArrayList<>();

	        for (Asignar_equipos voluntarioEquipo : voluntariosEquipos) {     
	            Usuarios usuario = registroActividadService.listarvoluntariosUsuarios(voluntarioEquipo.getId_voluntario()).get(0); // Suponiendo que solo hay uno
	            voluntarios.add(usuario);
	        }

	        voluntariosPorEquipo.put(id, voluntarios);
	    }

	    model.addAttribute("detalles", detalles);
	    model.addAttribute("voluntariosPorEquipo", voluntariosPorEquipo); 
	    return "informacion_registroacti_real";
	}

}
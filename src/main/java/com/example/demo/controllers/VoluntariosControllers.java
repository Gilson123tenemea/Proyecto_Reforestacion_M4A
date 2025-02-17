package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.dao.EquiposDaoImpl;
import com.example.demo.dao.RegistroActividadRealizadaDaoImpl;
import com.example.demo.entity.Administrador;
import com.example.demo.entity.Area;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IInscripcionServices;
import com.example.demo.service.IParcelaService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IPatrocinioService;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.ISueloService;
import com.example.demo.service.IUsuarioServices;
import com.example.demo.service.IVoluntariosService;
import com.example.demo.service.RegistroActividadRealizadaService;

@Controller
public class VoluntariosControllers {

	@Autowired
	private IVoluntariosService voluntariosServices;

	@Autowired
	private IUsuarioServices usuarioServices;

	@Autowired
	private IParroquiaService parroquiaService;
	@Autowired
	private ICantonService cantonService;
	@Autowired
	private IProvinciaService provinciaService;

	@Autowired
	private EquiposServiceImpl equipoService;

	@Autowired
	private RegistroActividadRealizadaService registroActividadRealizadaService;

	@Autowired
	private EquiposDaoImpl equipoDao;

	@Autowired
	private RegistroActividadRealizadaDaoImpl registroActDao;

	@Autowired
	private IProyectoServices proyectoService;

	@Autowired
	private IInscripcionServices inscripcionService;

	// =============================================================
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
	// =============================================================

	@GetMapping("/proyectosvoluntario")
	public String proyectos(Model model, @SessionAttribute("idVoluntario") Long idVoluntario) {

		List<Inscripcion> inscripciones = inscripcionService.findAll();
		List<Proyecto> todosLosProyectos = proyectoService.findAll();

		// Crear un Set para almacenar los IDs de los proyectos en los que el voluntario
		// ya está inscrito
		Set<Long> proyectosInscritos = inscripciones.stream()
				.filter(inscripcion -> inscripcion.getId_voluntario().equals(idVoluntario))
				.map(Inscripcion::getId_proyecto).collect(Collectors.toSet());

		// Filtrar los proyectos disponibles:
		List<Proyecto> proyectosDisponibles = todosLosProyectos.stream()
				.filter(proyecto -> !proyectosInscritos.contains(proyecto.getId_proyecto())
						&& "activo".equals(proyecto.getEstado()))
				.collect(Collectors.toList());

		model.addAttribute("proyectos", proyectosDisponibles);
		model.addAttribute("titulo", "Proyectos Disponibles");

		return "proyectosvoluntario";
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
// Método para obtener actividades pendientes de validación
	
	@GetMapping("/voluntario/actividades")
	public String getActividadesPorAceptar(@SessionAttribute("idVoluntario") Long idVoluntario, Model model) {
		List<Object[]> actividadesPorAceptar = registroActividadRealizadaService
				.findActividadesPorAceptar(idVoluntario);
		model.addAttribute("actividadesPorAceptar", actividadesPorAceptar);
		return "actividades_voluntariosss"; // Asegúrate de que esta es la vista correcta
	}
	
	
	//mas actifvidades realizadas-----------------------------------
	
	
	//------------------------------

// Método para obtener actividades completamente realizadas
	@GetMapping("/actividades-realizadas")
	public String getActividadesRealizadas(@SessionAttribute("idVoluntario") Long idVoluntario, Model model) {
		List<Object[]> actividadesRealizadas = registroActividadRealizadaService
				.obtenerActividadesRealizadas(idVoluntario);
		model.addAttribute("actividadesRealizadas", actividadesRealizadas);
		return "voluntarioActividadesRealizadas"; // Vista correcta en templates/
	}

	@GetMapping("/actividades/voluntariossssssssssss")
	public String getActividadesPorVoluntariossssssss(@SessionAttribute("idVoluntario") Long idVoluntario,
			Model model) {
		List<Object[]> actividades = equipoService.obtenerActividadesPorHacer(idVoluntario);
		model.addAttribute("actividades", actividades);
		model.addAttribute("idVoluntario", idVoluntario);
		return "actividades_voluntariossss";
	}

	//voluntarios actividades realizadas mas.....
	
	@GetMapping("/proyectosVoluntariosMas")
	public String mostrarProyectosVoluntariosMas(
	    @SessionAttribute(name = "idVoluntario", required = false) Long idVoluntario,
	    Model model) {
	    
	    if (idVoluntario == null) {
	        return "redirect:/inicio";
	    }

	    try {
	        // Cargar actividades del voluntario
	        model.addAttribute("actividades", Optional.ofNullable(equipoService.obtenerActividadesPorHacer(idVoluntario))
	            .orElseGet(Collections::emptyList));
	        model.addAttribute("actividadesPorAceptar", Optional.ofNullable(registroActividadRealizadaService.findActividadesPorAceptar(idVoluntario))
	            .orElseGet(Collections::emptyList));
	        model.addAttribute("actividadesRealizadas", Optional.ofNullable(registroActividadRealizadaService.obtenerActividadesRealizadas(idVoluntario))
	            .orElseGet(Collections::emptyList));

	        // Cargar provincias
	        model.addAttribute("provincias", Optional.ofNullable(provinciaService.findAll())
	            .orElseGet(Collections::emptyList));

	        // Obtener el proyecto asociado al voluntario
	        Proyecto proyecto = Optional.ofNullable(proyectoService.findOne(idVoluntario)).orElse(new Proyecto());
	        model.addAttribute("proyecto", proyecto);

	        // Cargar áreas y parcelas si el proyecto tiene un ID válido
	        Long idProyecto = proyecto.getId_proyecto();
	        if (idProyecto != null) {
	            model.addAttribute("areas", Optional.ofNullable(areaService.findByProyectoIdAdministrador(idProyecto))
	                .orElseGet(Collections::emptyList));
	            model.addAttribute("parcelas", Optional.ofNullable(parcelaService.findByAreaId(idProyecto))
	                .orElseGet(Collections::emptyList));
	        } else {
	            model.addAttribute("areas", Collections.emptyList());
	            model.addAttribute("parcelas", Collections.emptyList());
	        }

	        // Cargar patrocinio, patrocinador y representante si el proyecto tiene administrador
	        Long idAdministrador = proyecto.getId_administrador();
	        if (idAdministrador != null) {
	            Patrocinio patrocinio = Optional.ofNullable(patrocinioService.findOne(idAdministrador))
	                .orElse(new Patrocinio());
	            model.addAttribute("patrocinio", patrocinio);

	            Patrocinador patrocinador = Optional.ofNullable(PatrocinadorService.findOne(patrocinio.getId_patrocinador()))
	                .orElse(new Patrocinador());
	            model.addAttribute("patrocinador", patrocinador);

	            Usuarios representante = Optional.ofNullable(usuarioServices.findOne(patrocinador.getId_usuarios()))
	                .orElse(new Usuarios());
	            model.addAttribute("representante", representante);
	        } else {
	            model.addAttribute("patrocinio", new Patrocinio());
	            model.addAttribute("patrocinador", new Patrocinador());
	            model.addAttribute("representante", new Usuarios());
	        }

	        return "proyectosVoluntariosMas";

	    } catch (Exception e) {
	        model.addAttribute("mensajeError", "Hubo un error al cargar los datos: " + e.getMessage());
	        return "error";
	    }
	}
	
	
	
	
	// --------------------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------------------
		// --------------------------------------------------------------------------------------------
	
	
	
	

	
	
	
	
	
	@GetMapping("/actividades/voluntario")
	public String getActividadesPorVoluntario(@SessionAttribute("idVoluntario") Long idVoluntario, Model model) {
		System.out.println(">>> Obteniendo actividades del voluntario con ID: " + idVoluntario);
	    System.out.println(">>> Obteniendo actividades del voluntario con ID: " + idVoluntario);

	    List<Object[]> actividades = equipoService.obtenerActividadesPorHacer(idVoluntario);
	    System.out.println(">>> Actividades por hacer encontradas: " + (actividades != null ? actividades.size() : 0));

	    List<Object[]> actividadesPorAceptar = registroActividadRealizadaService.findActividadesPorAceptar(idVoluntario);
	    System.out.println(">>> Actividades pendientes de validación encontradas: " 
	        + (actividadesPorAceptar != null ? actividadesPorAceptar.size() : 0));

	    List<Object[]> actividadesRealizadas = registroActividadRealizadaService.obtenerActividadesRealizadas(idVoluntario);
	    System.out.println(">>> Actividades realizadas encontradas: " 
	        + (actividadesRealizadas != null ? actividadesRealizadas.size() : 0));

		// Imprimir detalles de las actividades realizadas
		if (actividadesRealizadas != null && !actividadesRealizadas.isEmpty()) {
			System.out.println(">>> Listado de actividades realizadas:");
			for (Object[] actividad : actividadesRealizadas) {
				System.out.println("   - Voluntario: " + actividad[0] + ", Proyecto: " + actividad[1] + ", Cantidad: "
						+ actividad[2] + ", Foto: " + actividad[3]);
			}
		} else {
			System.out.println(">>> No hay actividades realizadas para este voluntario.");
		}

		model.addAttribute("actividades", actividades);
		model.addAttribute("actividadesPorAceptar", actividadesPorAceptar);
		model.addAttribute("actividadesRealizadas", actividadesRealizadas);
		model.addAttribute("idVoluntario", idVoluntario);

		return "actividades_voluntario";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@GetMapping("/{actividad}/detalle")
	public String getDetalleActividad(@PathVariable Long actividadId, Model model) {
		// Obtener los detalles de la actividad por su ID
		Optional<Object[]> actividadDetalle = registroActividadRealizadaService.obtenerDetalleActividad(actividadId);

		if (actividadDetalle.isPresent()) {
			model.addAttribute("actividadDetalle", actividadDetalle.get());
			return "detalleActividad"; // Nombre de la vista que mostrará los detalles
		} else {
			model.addAttribute("error", "No se encontraron detalles para esta actividad.");
			return "error"; // Vista de error si la actividad no existe
		}
	}

	// --------------------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------------------------

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/iniciovoluntario")
	public String mostrarInicioVoluntario(Model model, @SessionAttribute("idVoluntario") Long idVoluntario) {
		Voluntarios voluntario = voluntariosServices.findOne(idVoluntario);

		Usuarios usuario = usuarioServices.findOne(voluntario.getId_usuarios());

		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Inicio Vountario");

		return "iniciovoluntario";
	}

	@GetMapping("/inscribirproyecto")
	public String inscribirproyectos(Model model) {
		model.addAttribute("titulo", "proyectos");
		return "inscribirproyecto";
	}

	@GetMapping("/proyectosinscritos")
	public String proyectosinscritos(Model model) {
		model.addAttribute("titulo", "proyectos");
		return "proyectosinscritos";
	}

	@RequestMapping(value = "/voluntarios", method = RequestMethod.GET)
	public String listarYEditar(@RequestParam(value = "id", required = false) Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Voluntarios voluntario = new Voluntarios();
		Usuarios usuario = new Usuarios();

		if (id != null && id > 0) {
			voluntario = voluntariosServices.findOne(id);
			if (voluntario != null) {
				usuario = usuarioServices.findOne(voluntario.getId_usuarios());
			} else {
				flash.addFlashAttribute("info", "El voluntario no existe en la base de datos");
				return "redirect:/voluntarios";
			}
		}

		model.put("voluntarios", voluntario);
		model.put("usuario", usuario);
		model.put("titulo", "Editar o Crear Voluntario");
		return "voluntarios";
	}

	// ---------------------------------------------------------------------------
	 @PostMapping("/guardarvolun")
	    public String guardarVoluntarioYUsuario(
	            @ModelAttribute Voluntarios voluntarios,
	            @ModelAttribute Usuarios usuario,
	            Model model) {
	    	
	    	
	        try {
	         

	            usuarioServices.save(usuario);
	            voluntarios.setId_usuarios(usuario.getId_usuarios());
	            voluntarios.setFechaRegistro(new Date());
	            voluntarios.setAsiste_si_no(true);
	            voluntarios.setDisponibilidad(true);
	            voluntarios.setEstado(true);
	            voluntariosServices.save(voluntarios);

	            return "redirect:/login";
	        } catch (Exception e) {
	            model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
	            return "error";
	        }
	    }

	@GetMapping("/crearVoluntario")
	public String crearVoluntario(Model model) {
		model.addAttribute("voluntarios", new Voluntarios());

		model.addAttribute("provincias", provinciaService.findAll());
		return "voluntarios";
	}

	// ----------------------------------------------------------------------------

	@GetMapping("/listarVoluntarios")
	public String listarVoluntarios(Model model) {
		model.addAttribute("titulo", "Lista de Voluntarios");

		List<Voluntarios> voluntarios = voluntariosServices.findAll();
		List<Usuarios> usuarios = usuarioServices.findAll();

		List<Map<String, Object>> combinados = new ArrayList<>();
		for (Usuarios usuario : usuarios) {
			if (usuario.getVoluntarios() != null && !usuario.getVoluntarios().isEmpty()) {
				Voluntarios voluntario = usuario.getVoluntarios().get(0);
				Map<String, Object> datosCombinados = new HashMap<>();
				datosCombinados.put("voluntarios", voluntario);
				datosCombinados.put("usuario", usuario);
				combinados.add(datosCombinados);
			}
		}

		model.addAttribute("combinados", combinados);
		return "listarVoluntarios";
	}

	@PostMapping("/deletevoluntario/{id}")
	public String deleteVoluntarios(@PathVariable Long id, Model model) {
		try {
			System.out.println("Eliminando voluntario con ID: " + id); // Debugging log
			Voluntarios voluntarios = voluntariosServices.findOne(id);

			if (voluntarios == null) {
				model.addAttribute("mensaje", "Voluntario no encontrado con ID: " + id);
				return "error";
			}

			voluntariosServices.delete(id);
			model.addAttribute("mensaje", "Voluntario y Usuario eliminados exitosamente");
			return "redirect:/listarVoluntarios"; // Verifica que esta URL sea correcta
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al eliminar el voluntario: " + e.getMessage());
			return "error";
		}
	}

	@GetMapping("/editarVoluntario")
	public String editarVoluntario(@SessionAttribute("idVoluntario") Long idVoluntario, Map<String, Object> model) {
		Voluntarios voluntario = voluntariosServices.findOne(idVoluntario);

		// Si el voluntario no existe, inicializa uno vacío
		if (voluntario == null) {
			voluntario = new Voluntarios();
		}

		Usuarios usuario = usuarioServices.findOne(voluntario.getId_usuarios());

		// Si el usuario no existe, inicializa uno vacío
		if (usuario == null) {
			usuario = new Usuarios();
		}

		model.put("voluntarios", voluntario);
		model.put("usuario", usuario);
		model.put("provincias", provinciaService.findAll());

		return "EditarVoluntario";
	}

	@PostMapping("/ActualizarVoluntario")
	public String guardarVoluntario(@RequestParam(value = "id_voluntario", required = false) Long idVoluntario,
			@RequestParam(value = "id_usuarios", required = false) Long idUsuario,
			@ModelAttribute("usuario") Usuarios usuario, @ModelAttribute("voluntarios") Voluntarios voluntarios,
			Model model) {
		try {
			// Validación de IDs
			if (idUsuario == null || idVoluntario == null) {
				model.addAttribute("mensaje", "Error: El ID de usuario o de voluntario no puede estar vacío.");
				return "error";
			}

			// Procesamiento de Usuario
			Usuarios usuarioExistente = usuarioServices.findOne(idUsuario);
			if (usuarioExistente != null) {
				usuarioExistente.setId_usuarios(idUsuario);

				// Validación de cada campo para no asignar valores nulos
				if (usuario.getCedula() != null) {
					usuarioExistente.setCedula(usuario.getCedula());
				}
				if (!usuario.getNombre().equalsIgnoreCase("")) {
					usuarioExistente.setNombre(usuario.getNombre());
				}
				if (!usuario.getApellido().equalsIgnoreCase("")) {
					usuarioExistente.setApellido(usuario.getApellido());
				}
				if (!usuario.getCorreo().equalsIgnoreCase("")) {
					usuarioExistente.setCorreo(usuario.getCorreo());
				}
				if (usuario.getFecha_nacimiento() != null) {
					usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
				}
				if (usuario.getId_parroquia() != null) {
					usuarioExistente.setId_parroquia(usuario.getId_parroquia());
				}
				if (!usuario.getCelular().equalsIgnoreCase("")) {
					usuarioExistente.setCelular(usuario.getCelular());
				}
				if (!usuario.getContraseña().equalsIgnoreCase("")) {
					usuarioExistente.setContraseña(usuario.getContraseña());
				}
				usuarioServices.save(usuarioExistente);
			} else {
				model.addAttribute("mensaje", "Error: Usuario no encontrado.");
				return "error";
			}

			// Procesamiento de Voluntario
			Voluntarios voluntarioExistente = voluntariosServices.findOne(idVoluntario);
			if (voluntarioExistente != null) {
				voluntarioExistente.setId_voluntario(idVoluntario);

				// Validación de cada campo para no asignar valores nulos
				if (voluntarios.getEstado() != null) {
					voluntarioExistente.setEstado(true);
				}
				if (!voluntarios.getExperiencia().equalsIgnoreCase("")) {
					voluntarioExistente.setExperiencia(voluntarios.getExperiencia());
				}
				voluntarioExistente.setId_usuarios(usuario.getId_usuarios());
				voluntariosServices.save(voluntarioExistente);
			} else {
				voluntarios.setId_usuarios(usuario.getId_usuarios());
				
				voluntariosServices.save(voluntarios);
			}

			return "redirect:/proyectosvoluntario";
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			return "error";
		}
	}

	@GetMapping("/voluntario/cantones/{idProvincia}")
	@ResponseBody
	public List<Canton> getCantonesByProvinciaVoluntario(@PathVariable Long idProvincia) {
		return cantonService.findByProvincia(idProvincia);
	}

	@GetMapping("/voluntario/parroquias/{idCanton}")
	@ResponseBody
	public List<Parroquia> getParroquiasByCantonVoluntario(@PathVariable Long idCanton) {
		return parroquiaService.findByCanton(idCanton);
	}

	// =======================================================================

	@GetMapping("/InfromacionProyecto")
	public String InfoVoluntarioProy(Model model) {
		model.addAttribute("voluntarios", new Voluntarios());

		model.addAttribute("provincias", provinciaService.findAll());
		return "voluntarios";
	}

	@GetMapping("/inscribir")
	public String inscribir(@SessionAttribute("idVoluntario") Long idVoluntario,
			@RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAttributes) {

		List<Inscripcion> inscripciones = inscripcionService.findAll();
		List<Proyecto> todosLosProyectos = proyectoService.findAll();

		Set<Long> proyectosInscritos = inscripciones.stream()
				.filter(inscripcion -> inscripcion.getId_voluntario().equals(idVoluntario) && todosLosProyectos.stream()
						.anyMatch(proyecto -> proyecto.getId_proyecto().equals(inscripcion.getId_proyecto())
								&& "activo".equals(proyecto.getEstado())))
				.map(Inscripcion::getId_proyecto).collect(Collectors.toSet());

		if (proyectosInscritos.isEmpty()) {
			Inscripcion inscribirse = new Inscripcion();

			inscribirse.setId_voluntario(idVoluntario);
			inscribirse.setId_proyecto(id);
			inscribirse.setFecha(new Date());

			inscripcionService.save(inscribirse);

			System.out.println("Se recibió el ID: " + id);
			redirectAttributes.addFlashAttribute("mensaje", "Te has inscrito al proyecto con exito");
			redirectAttributes.addFlashAttribute("error", false);

		} else {
			redirectAttributes.addFlashAttribute("mensaje", "Ingreso negado, ya se encuentra en un proyecto en curso");
			redirectAttributes.addFlashAttribute("error", true);
		}
		return "redirect:/proyectosvoluntario"; // 🔄 Importante: Redirección con el mensaje
	}

	// ======================================================================================================

	@GetMapping("/informacion")
	public String informacion(@SessionAttribute("idVoluntario") Long idVoluntario,
			@RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAttributes) {
		if (id == null) {
			redirectAttributes.addFlashAttribute("error", "true");
			redirectAttributes.addFlashAttribute("mensaje", "No se recibió un ID válido.");
			return "redirect:/listaProyectos"; // Redirige si no hay ID
		}

		return "redirect:/InfoProyecto?id=" + id; // Pasa el ID en la URL
	}

	@GetMapping("/InfoProyecto")
	public String mostrarInfoProyecto(@RequestParam("id") Long id, Model model) {
	    
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

	    return "InfoProyecto";
	}

	// ----------------------------------------------------------------------------

	@GetMapping("/proyectoVoluntario/imagen/{id}")
	@ResponseBody
	public byte[] obtenerImagenProyecto(@PathVariable("id") Long id) {
		Proyecto proyecto = proyectoService.findOne(id);
		if (proyecto != null && proyecto.getImagen() != null) {
			return proyecto.getImagen();
		}
		return new byte[0];
	}

	// ==============================================================
	
	@GetMapping("/cedulaVoltuntarui/{cedula}")
	 @ResponseBody
    public boolean ExistenciaCedulaVol(@PathVariable String cedula) {
        return voluntariosServices.BuscarCedulaVoluntario(cedula);
    }
	
	//===============================================================
	

}
package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.example.demo.entity.Administrador;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IProyectoServices;
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
	private IProyectoServices proyectoService; 

	// Método para obtener las actividades pendientes de un voluntario
	@GetMapping("/{voluntarioId}/actividades")
	public String getActividadesPorHacer(@PathVariable Long voluntarioId, Model model) {
		// Obtener actividades pendientes
		List<Object[]> actividadesPorHacer = equipoService.obtenerActividadesPorHacer(voluntarioId);
		model.addAttribute("actividadesPorHacer", actividadesPorHacer);
		return "voluntarioActividades"; // Nombre de la vista
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	
	@GetMapping("/actividad/{actividadId}/detalle")
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

	@GetMapping("/{voluntarioId}/actividades-realizadas")
	public String getActividadesRealizadas(@PathVariable Long voluntarioId, Model model) {
		// Obtener actividades realizadas con validación de administrador en TRUE
		List<Object[]> actividadesRealizadas = registroActividadRealizadaService
				.obtenerActividadesRealizadas(voluntarioId);

		model.addAttribute("actividadesRealizadas", actividadesRealizadas);

		return "voluntarioActividadesRealizadas"; // Asegúrate de que este es el nombre correcto del archivo HTML en
													// `templates/`
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------

	@GetMapping("/todos")
	public String getActividades(Model model) {
		List<Object[]> actividadesPorHacer = equipoDao.findActividadesPorHacerDeTodos();

		// Pasamos la lista de actividades por hacer al modelo
		model.addAttribute("actividadesPorHacer", actividadesPorHacer);

		return "actividadestodoHacer"; // Vista donde mostraremos las actividades
	}

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

	@GetMapping("/proyectosvoluntario")
    public String proyectos (Model model,@SessionAttribute("idVoluntario") Long idVoluntario) {

          model.addAttribute("proyectos", proyectoService.findAll());
         
        model.addAttribute("titulo", "proyectos");
        return "proyectosvoluntario";
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

	@PostMapping("/guardarvolun")
	public String guardarVoluntarioYUsuario(@ModelAttribute("voluntarios") Voluntarios voluntarios,
			@ModelAttribute("usuario") Usuarios usuario, Model model) {
		try {
			if (usuario.getId_usuarios() != null) {
				Usuarios usuarioExistente = usuarioServices.findOne(usuario.getId_usuarios());
				if (usuarioExistente != null) {
					usuarioExistente.setCedula(usuario.getCedula());
					usuarioExistente.setNombre(usuario.getNombre());
					usuarioExistente.setApellido(usuario.getApellido());
					usuarioExistente.setCorreo(usuario.getCorreo());

					if (usuario.getFecha_nacimiento() != null) {
						usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
					}

					usuarioExistente.setGenero(usuario.getGenero());
					usuarioExistente.setCelular(usuario.getCelular());
					usuarioExistente.setContraseña(usuario.getContraseña());
					usuario = usuarioExistente;
				}
			}

			usuarioServices.save(usuario);
			voluntarios.setId_usuarios(usuario.getId_usuarios());
			voluntariosServices.save(voluntarios);

			return "redirect:/listarVoluntarios";
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
			return "error";
		}
	}

	@GetMapping("/crearVoluntario")
	public String crearVoluntario(Map<String, Object> model) {
		model.put("voluntarios", new Voluntarios());
		model.put("usuarios", usuarioServices.findAll());
		return "voluntarios";
	}

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
					voluntarioExistente.setEstado(voluntarios.getEstado());
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

			return "redirect:/listarVoluntarios";
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

}
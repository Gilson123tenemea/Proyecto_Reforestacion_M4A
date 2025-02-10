package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IUsuarioServices;

@Controller
@SessionAttributes("idPatrocinador")
public class PatrocinadorController {
	
	
	@Autowired
	private IPatrocinadorServices patrocinadorservice;
	
	@Autowired
	private IUsuarioServices usuarioservice;
    @Autowired
    private IParroquiaService parroquiaService;
    @Autowired
	private ICantonService cantonService; 
	@Autowired
	private IProvinciaService provinciaService; 
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

	@GetMapping("/listar")
	 public String listarVoluntarios(Model model) {
        model.addAttribute("titulo", "Lista de Patrocinadores");

        List<Patrocinador> patrocinadores = patrocinadorservice.findAll();
        List<Usuarios> usuarios = usuarioservice.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            if (!usuario.getPatrocinador().isEmpty()) {
            	Patrocinador patrocinador = usuario.getPatrocinador().get(0); 
                Map<String, Object> datosCombinados = new HashMap<>();
                datosCombinados.put("patrocinadores", patrocinador);
                datosCombinados.put("usuario", usuario);
                combinados.add(datosCombinados);
            }
        }

        model.addAttribute("combinados", combinados);
        return "Lista";
    }
	
	//Inicio Patrocinador
	@GetMapping("/iniciopatrocinador")
	public String iniciopatrocinador(@SessionAttribute("idPatrocinador") Long idPatrocinador, Model model) {
	    model.addAttribute("titulo", "Inicio Patrocinador");

	    // Obtener el patrocinador por su ID
	    Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);
	    if (patrocinador != null) {
	        // Obtener el usuario relacionado con el patrocinador
	        Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
	        if (usuario != null) {
	            // Pasar el nombre y apellido del patrocinador al modelo
	            model.addAttribute("nombrePatrocinador", usuario.getNombre());
	            model.addAttribute("apellidoPatrocinador", usuario.getApellido());
	        }
	    }

	    return "iniciopatrocinador";  // Asegúrate de que este sea el nombre correcto del archivo de la vista
	}




	@GetMapping("/formularioPatrocinador")
	public String crear(Model model) {
	    model.addAttribute("patrocinador", new Patrocinador());
	    model.addAttribute("provincias", provinciaService.findAll());
	    return "formularioPatrocinador"; 
	}
	
	//@GetMapping("/editarPatrocinador")
	public String listar (Model model) {
		return "editarPatrocinador";
	}

    // Método para guardar el patrocinador
    @PostMapping("/guardarPatrocinador")
    public String guardarPatrocinadorYUsuario(@ModelAttribute Patrocinador patrocinador, @ModelAttribute Usuarios usuario, Model model) {
        try {
            usuarioservice.save(usuario);
            patrocinador.setId_usuarios(usuario.getId_usuarios());
            patrocinadorservice.save(patrocinador);

            model.addAttribute("mensaje", "Patrocinador guardado exitosamente");
            return "redirect:/login"; 
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Voluntario y Usuario: " + e.getMessage());
            return "error";
        }
    }	
	
    //@RequestMapping(value = "/patrocinadores", method = RequestMethod.GET)
    public String listarYEditar(
        @RequestParam(value = "id", required = false) Long id,
        Map<String, Object> model,
        RedirectAttributes flash) {

        Patrocinador patrocinador = new Patrocinador();
        Usuarios usuario = new Usuarios();

        if (id != null && id > 0) {
            patrocinador = patrocinadorservice.findOne(id);
            if (patrocinador != null) {
                usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
            } else {
                flash.addFlashAttribute("info", "El patrocinador no existe en la base de datos");
                return "redirect:/formularioPatrocinador";
            }
        }

        model.put("patrocinadores", patrocinador);
        model.put("usuario", usuario);
        model.put("titulo", "Editar o Crear Patrocinadores");

        return "";
    }


  
    @RequestMapping(value="/formulario/{id}")
    public String guardarVoluntarioYUsuario(
            @ModelAttribute("patrocinador") Patrocinador patrocinador,
            @ModelAttribute("usuario") Usuarios usuario,
            Model model) {
        try {
            if (usuario.getId_usuarios() != null) {
                Usuarios usuarioExistente = usuarioservice.findOne(usuario.getId_usuarios());
                if (usuarioExistente != null) {
                    usuarioExistente.setCedula(usuario.getCedula());
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setApellido(usuario.getApellido());
                    usuarioExistente.setCorreo(usuario.getCorreo());
                    // Aquí se asegura de que el ID de la parroquia se esté estableciendo
                    usuarioExistente.setId_parroquia(usuario.getId_parroquia());
                    
                    if (usuario.getFecha_nacimiento() != null) {
                        usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                    }
                    
                    usuarioExistente.setGenero(usuario.getGenero());
                    usuarioExistente.setCelular(usuario.getCelular());
                    usuarioExistente.setContraseña(usuario.getContraseña());
                    
                    usuario = usuarioExistente;
                }
            }

            usuarioservice.save(usuario);
            patrocinador.setId_usuarios(usuario.getId_usuarios());
            patrocinadorservice.save(patrocinador);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
            return "error";
        }
    }
    
    
    
    
    
    
    @GetMapping("/editarPatrocinador")
    public String editarPatrocinador(@SessionAttribute Long idPatrocinador, Map<String, Object> model) {
        Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);

        // Si el voluntario no existe, inicializa uno vacío
        if (patrocinador == null) {
        	patrocinador = new Patrocinador();
        }

        Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());

        // Si el usuario no existe, inicializa uno vacío
        if (usuario == null) {
            usuario = new Usuarios();
        }

        model.put("patrocinadores", patrocinador);
        model.put("usuario", usuario);
        model.put("provincias", provinciaService.findAll());

        return "editarPatrocinador";
    }
    
    
    
    @PostMapping("/ActualizarPatrocinador")
    public String guardarPatrocinador(@RequestParam(value = "id_usuarios", required = false) Long idUsuario,
            @RequestParam(value = "id_patrocinador", required = false) Long idPatrocinador,
            @ModelAttribute("usuario") Usuarios usuario, 
            @ModelAttribute("patrocinador") Patrocinador patrocinador, 
            Model model) {
        try {
            // Validación de ID de usuario
            if (idUsuario == null) {
                model.addAttribute("mensaje", "Error: El ID de usuario no puede estar vacío.");
                return "error";
            }

            // Procesamiento de Usuario
            Usuarios usuarioExistente = usuarioservice.findOne(idUsuario);
            if (usuarioExistente != null) {
                usuarioExistente.setId_usuarios(idUsuario);

                // Validación de cada campo para no asignar valores nulos
                if (usuario.getCedula() != null) {
                    usuarioExistente.setCedula(usuario.getCedula());
                }
                if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                    usuarioExistente.setNombre(usuario.getNombre());
                }
                if (usuario.getApellido() != null && !usuario.getApellido().isEmpty()) {
                    usuarioExistente.setApellido(usuario.getApellido());
                }
                if (usuario.getCorreo() != null && !usuario.getCorreo().isEmpty()) {
                    usuarioExistente.setCorreo(usuario.getCorreo());
                }
                if (usuario.getFecha_nacimiento() != null) {
                    usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                }
                if (usuario.getId_parroquia() != null) {
                    usuarioExistente.setId_parroquia(usuario.getId_parroquia());
                }
                if (usuario.getCelular() != null && !usuario.getCelular().isEmpty()) {
                    usuarioExistente.setCelular(usuario.getCelular());
                }
                if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                    usuarioExistente.setContraseña(usuario.getContraseña());
                }
                usuarioservice.save(usuarioExistente);
            } else {
                model.addAttribute("mensaje", "Error: Usuario no encontrado.");
                return "error";
            }

            // Procesamiento de Patrocinador
            if (idPatrocinador != null) {
                Patrocinador patrocinadorExistente = patrocinadorservice.findOne(idPatrocinador);
                if (patrocinadorExistente != null) {
                    patrocinadorExistente.setId_patrocinador(idPatrocinador);

                    // Validación de cada campo para no asignar valores nulos
                    if (patrocinador.getNombreEmpresa() != null && !patrocinador.getNombreEmpresa().isEmpty()) {
                        patrocinadorExistente.setNombreEmpresa(patrocinador.getNombreEmpresa());
                    }
                    if (patrocinador.getRuc() != null && !patrocinador.getRuc().isEmpty()) {
                        patrocinadorExistente.setRuc(patrocinador.getRuc());
                    }
                    patrocinadorservice.save(patrocinadorExistente);
                } else {
                    model.addAttribute("mensaje", "Error: Patrocinador no encontrado.");
                    return "error";
                }
            } else {
                // Si idPatrocinador es null, se guarda un nuevo patrocinador
                patrocinadorservice.save(patrocinador);
            }

            return "redirect:/verproyectospatrocinador";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
            return "error";
        }
    }
   
    
    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        patrocinadorservice.delete(id); 
        // Llama al servicio para eliminar el patrocinador
        return "redirect:/listar"; // Redirige a la lista de patrocinadores
    }
	
    @GetMapping("/patrocinador/cantones/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvinciaPatrocinador(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }

    @GetMapping("/patrocinador/parroquias/{idCanton}")
    @ResponseBody
    public List<Parroquia> getParroquiasByCanton(@PathVariable Long idCanton) {
        return parroquiaService.findByCanton(idCanton);
    }
}
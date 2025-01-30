package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Administrador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.IUsuarioServices;

@Controller
public class ControladorAdministrador {
	
	@Autowired
	private IAdministradorServices administradorServices;
	
	@Autowired
	private IUsuarioServices usuarioServices;
	
	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);
	        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	    }

	

	@GetMapping("/listarAdministradores")
	public String listarAdministradores(Model model) {
	    model.addAttribute("titulo", "Lista de Administradores");

	    List<Administrador> administradores = administradorServices.findAll();
	    List<Usuarios> usuarios = usuarioServices.findAll();

	    List<Map<String, Object>> combinados = new ArrayList<>();

	    for (Usuarios usuario : usuarios) {
	        if (!usuario.getAdministrador().isEmpty()) {
	            Administrador administrador = usuario.getAdministrador().get(0); 
	            Map<String, Object> datosCombinados = new HashMap<>();
	            datosCombinados.put("administrador", administrador);
	            datosCombinados.put("usuario", usuario);
	            combinados.add(datosCombinados);
	        }
	    }

	    model.addAttribute("combinados", combinados);
	    return "listarAdministradores";
	}

	@RequestMapping(value = "/administrador", method = RequestMethod.GET)
	public String listarYEditar(
	        @RequestParam(value = "id", required = false) Long id,
	        Map<String, Object> model,
	        RedirectAttributes flash) {
	    Administrador administrador = new Administrador();
	    Usuarios usuario = new Usuarios();

	    if (id != null && id > 0) {
	        administrador = administradorServices.findOne(id);
	        if (administrador != null) {
	            usuario = usuarioServices.findOne(administrador.getId_usuarios());
	        } else {
	            flash.addFlashAttribute("info", "El administrador no existe en la base de datos");
	            return "redirect:/administrador";
	        }
	    }

	    model.put("administrador", administrador);
	    model.put("usuario", usuario);
	    model.put("titulo", "Editar o Crear Administrador");
	    return "administrador";
	}




	@PostMapping("/guardar")
	public String guardarAdministradorYUsuario(
	        @ModelAttribute("administrador") Administrador administrador,
	        @ModelAttribute("usuario") Usuarios usuario,
	        Model model) {
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

	        administrador.setId_usuarios(usuario.getId_usuarios());
	        administradorServices.save(administrador);
	        return "redirect:/listarAdministradores";
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
	        return "error";
	    }
	}



    
    @RequestMapping("/administrador")
    public String crear(Map<String, Object> model) {
        model.put("administrador", new Administrador());
        model.put("usuarios", usuarioServices.findAll());
        return "administrador"; 
    }
   
    
    @PostMapping("/delete/{id}")
    public String deleteAdministrador(@PathVariable Long id, Model model) {
        try {
            Administrador administrador = administradorServices.findOne(id);

            if (administrador == null) {
                model.addAttribute("mensaje", "Administrador no encontrado con ID: " + id);
                return "error";
            }

            administradorServices.delete(id);
            model.addAttribute("mensaje", "Administrador eliminado exitosamente");
            return "redirect:/listarAdministradores";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el administrador: " + e.getMessage());
            return "error";
        }
    }

}

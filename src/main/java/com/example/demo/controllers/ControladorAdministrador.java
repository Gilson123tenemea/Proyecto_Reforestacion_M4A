package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	

	 // Lista de administradores
    @RequestMapping(value="/listarAdministradores", method=RequestMethod.GET)
    public String listarAdministradores(Model model) {
        model.addAttribute("titulo", "Lista de Administradores");
        model.addAttribute("administradores", administradorServices.findAll());
        return "listarAdministradores";  
    }

    // Ver administrador
    @GetMapping("/{id}")
    public String verAdministrador(@PathVariable Long id, Model model) {
        Administrador administrador = administradorServices.findOne(id);
        if (administrador == null) {
            model.addAttribute("mensaje", "Administrador no encontrado");
            return "error";  
        }
        model.addAttribute("administrador", administrador);
        return "verAdministrador";  
    }

 
    @PostMapping("/guardar")
    public String guardarAdministradorYUsuario(@ModelAttribute Administrador administrador, @ModelAttribute Usuarios usuario, Model model) {
        try {
            usuarioServices.save(usuario);
            administrador.setId_usuarios(usuario.getId_usuarios());
            administradorServices.save(administrador);

            model.addAttribute("mensaje", "Administrador y Usuario guardados exitosamente");
            return "redirect:/listarAdministradores"; 
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Administrador y Usuario: " + e.getMessage());
            return "error";
        }
    }

    
    @RequestMapping("/Inicio")
    public String crear(Map<String, Object> model) {
        model.put("administrador", new Administrador()); 
        return "Inicio"; 
    }

    // Eliminar Administrador
    @DeleteMapping("/delete/{id}")
    public String deleteAdministrador(@PathVariable Long id, Model model) {
        try {
            Administrador administrador = administradorServices.findOne(id);

            if (administrador == null) {
                model.addAttribute("mensaje", "Administrador no encontrado con ID: " + id);
                return "error";  
            }

            administradorServices.delete(id);
            model.addAttribute("mensaje", "Administrador eliminado exitosamente");
            return "redirect:/administrador/list";  
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el administrador: " + e.getMessage());
            return "error";  
        }
    }

}

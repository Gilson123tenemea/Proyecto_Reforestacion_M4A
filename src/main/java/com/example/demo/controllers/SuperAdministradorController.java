package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.ISuperAdministradorDao;
import com.example.demo.entity.SuperAdministrador;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.ISuperAdministradorServices;
import com.example.demo.service.IUsuarioServices;

@Controller
public class SuperAdministradorController {

	@Autowired
	private ISuperAdministradorServices superadministradorService;
	@Autowired
    private IUsuarioServices usuarioServices;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	@PostMapping("/")
    public String guardarVoluntarioYUsuario(@ModelAttribute SuperAdministrador superadministrador, @ModelAttribute Usuarios usuario, Model model) {
        try {
            usuarioServices.save(usuario);
            superadministrador.setId_usuarios(usuario.getId_usuarios());
            superadministradorService.save(superadministrador);

            model.addAttribute("mensaje", "Voluntario y Usuario guardados exitosamente");
            return "redirect:/listarVoluntarios"; 
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Voluntario y Usuario: " + e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/iniciosuperadmin")
    public String iniciosuperadmin(Model model) {
        model.addAttribute("titulo", "Inicio SuperAdmin");
        return "iniciosuperadmin";
    }
	
	  // Crear nuevo voluntario
    @RequestMapping("/")
    public String crear(Map<String, Object> model) {
        model.put("superadmin", new SuperAdministrador()); 
        return "voluntarios"; 
    }
    
	
}

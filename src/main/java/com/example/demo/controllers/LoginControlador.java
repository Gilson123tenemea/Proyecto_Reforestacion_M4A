package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.UsuariosServiceImpl;


@Controller
@SessionAttributes({"idPatrocinador","idVoluntario","idAdministrador","idSuperAdministrador"})

public class LoginControlador {

	
	@Autowired
    private UsuariosServiceImpl usuarioService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
        
    }
    
    @GetMapping({"/","","/Inicio"})
	public String Inicio() {
		return "/layout/layout";
	}

    @PostMapping("/login")
    public String login(@RequestParam String cedula, @RequestParam String contraseña, Model model) {
        String rol = usuarioService.authenticate(cedula, contraseña);

        if (rol == null) {
            model.addAttribute("error", "Credenciales inválidas");
            return "login"; 
        }

        Usuarios usuario = usuarioService.findAll().stream()
            .filter(u -> u.getCedula().equals(cedula) && u.getContraseña().equals(contraseña))
            .findFirst()
            .orElse(null);

        if (usuario != null && !usuario.getPatrocinador().isEmpty()) {
            Long idPatrocinador = usuario.getPatrocinador().get(0).getId_patrocinador();
            model.addAttribute("idPatrocinador", idPatrocinador);
        }
        
        if (usuario != null && !usuario.getVoluntarios().isEmpty()) {
            Long idVoluntario = usuario.getVoluntarios().get(0).getId_voluntario();
            model.addAttribute("idVoluntario", idVoluntario); 
        }
        
        if (usuario != null && !usuario.getAdministrador().isEmpty()) {
            Long idAdministrador = usuario.getAdministrador().get(0).getId_administrador();
            model.addAttribute("idAdministrador", idAdministrador); 
        }
        
        if (usuario != null && !usuario.getSuper_administrador().isEmpty()) {
            Long superadministrador= usuario.getSuper_administrador().get(0).getId_super_administrador();
            model.addAttribute("idSuperAdministrador", superadministrador); 
        }

        switch (rol) {
            case "superadmin":
                return "redirect:/iniciosuperadmin";
            case "administrador":
                return "redirect:/inicioadmin";
            case "voluntario":
                return "redirect:/iniciovoluntario";
            case "patrocinador":
                return "redirect:/verproyectospatrocinador"; 
            default:
                return "login"; 
        }
    }
    
    }

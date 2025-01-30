package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.UsuariosServiceImpl;

@Controller
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

        switch (rol) {
            case "superadmin":
                return "redirect:/formprovincia"; // Redirigir al dashboard del superadmin
            case "administrador":
                return "redirect:/suelo"; // Redirigir al dashboard del administrador
            case "voluntario":
                return "redirect:/proyectosvoluntario"; // Redirigir al dashboard del voluntario
            case "patrocinador":
            	 return "redirect:/cantones"; 
            default:
                return "login"; // En caso de que no se reconozca el rol
        }
    }
    }

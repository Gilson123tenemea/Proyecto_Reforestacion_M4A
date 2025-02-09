package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Usuarios;
import com.example.demo.service.UsuariosServiceImpl;

@Controller
@SessionAttributes({"idPatrocinador", "idVoluntario", "idAdministrador", "idSuperAdministrador"})
public class LoginControlador {

    @Autowired
    private UsuariosServiceImpl usuarioService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping({"/", "", "/Inicio"})
    public String Inicio() {
        return "/layout/layout";
    }

    @PostMapping("/login")
    public String login(@RequestParam String cedula, @RequestParam String contraseña, Model model) {
        // Intentar autenticar al usuario
        String rol = usuarioService.authenticate(cedula, contraseña);
        if (rol == null) {
            model.addAttribute("error", "Credenciales inválidas. Intente nuevamente.");
            return "login"; 
        }

        // Buscar usuario en la base de datos
        Usuarios usuario = usuarioService.findAll().stream()
            .filter(u -> u.getCedula().equals(cedula) && u.getContraseña().equals(contraseña))
            .findFirst()
            .orElse(null);

        // Validar si el usuario existe
        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "login";
        }

        // Almacenar atributos de sesión según el rol del usuario
        if (!usuario.getPatrocinador().isEmpty()) {
            model.addAttribute("idPatrocinador", usuario.getPatrocinador().get(0).getId_patrocinador());
        }
        if (!usuario.getVoluntarios().isEmpty()) {
            model.addAttribute("idVoluntario", usuario.getVoluntarios().get(0).getId_voluntario());
        }
        if (!usuario.getAdministrador().isEmpty()) {
            model.addAttribute("idAdministrador", usuario.getAdministrador().get(0).getId_administrador());
        }
        if (!usuario.getSuper_administrador().isEmpty()) {
            model.addAttribute("idSuperAdministrador", usuario.getSuper_administrador().get(0).getId_super_administrador());
        }

        // Mensaje de bienvenida con el nombre del usuario
        model.addAttribute("mensaje", "¡Bienvenido, " + usuario.getNombre() + "!");

        // Redirigir según el rol del usuario
        switch (rol) {
            case "superadmin":
                return "redirect:/iniciosuperadmin";
            case "administrador":
                return "redirect:/inicioadmin";
            case "voluntario":
                return "redirect:/proyectosvoluntario";
            case "patrocinador":
                return "redirect:/verproyectospatrocinador";
            default:
                model.addAttribute("error", "Rol no reconocido.");
                return "login";
        }
    }
}


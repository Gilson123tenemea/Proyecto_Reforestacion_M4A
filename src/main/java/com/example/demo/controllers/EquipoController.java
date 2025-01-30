package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipoController {
	
    @GetMapping("/equipos")
    public String mostrarequipos(Model model) {
        model.addAttribute("titulo", "Inicio Equipos");
        return "equipos";
    }

}

package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrarController {

	@GetMapping("/registrar")
    public String registrar() {
        return "registrar"; // Asegúrate de que "registrar.html" esté en la carpeta templates
    }
}

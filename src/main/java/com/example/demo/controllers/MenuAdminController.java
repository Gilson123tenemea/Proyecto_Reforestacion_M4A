package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuAdminController {

	@GetMapping("/menuA")
    public String menuA() {
        return "menuA"; // Asegúrate de que "menuA.html" esté en la carpeta templates
    }
}

package com.example.demo.controllers;

import com.example.demo.entity.Canton;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CantonControllers {

    @Autowired
    ICantonService cantonService;
    @Autowired
    IProvinciaService provinciaService;

    @GetMapping("/crearCanton")
    public String crearCantonForm(Model model) {
        model.addAttribute("canton", new Canton());
        model.addAttribute("provincias", provinciaService.listarProvincias());
        return "crearCanton";
    }

    @PostMapping("/guardarCanton")
    public String guardarCanton(@ModelAttribute Canton canton, Model model) {
        try {
            if (canton.getId_provincia() == null) {
                throw new Exception("Debe seleccionar una provincia.");
            }
            cantonService.save(canton);
            model.addAttribute("mensaje", "Cantón guardado exitosamente");
            return "redirect:/listarCantones";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el cantón: " + e.getMessage());
            return "error";
        }
    }

    // Listar todos los cantones
    @GetMapping("/listarCantones")
    public String listarCantones(Model model) {
        model.addAttribute("titulo", "Lista de Cantones");
        model.addAttribute("cantones", cantonService.findAll());
        return "listarCantones"; 
    }
    
}

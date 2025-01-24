package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Provincia;
import com.example.demo.service.IProvinciaService;



@Controller
public class ProvinciaControllers {

    @Autowired
    private IProvinciaService provinciaService;

    @GetMapping("/provincias")
    public String crearProvinciaForm(Model model) {
        model.addAttribute("provincia", new Provincia());
        return "provincias";
    }

    @PostMapping("/guardarProvincia")
    public String guardarProvincia(@ModelAttribute Provincia provincia, Model model) {
        try {
            provinciaService.save(provincia);
            model.addAttribute("mensaje", "Provincia guardada exitosamente");
            return "redirect:/listarProvincias";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la provincia: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/listarProvincias")
    public String listarProvincias(Model model) {
        model.addAttribute("titulo", "Lista de Provincias");
        model.addAttribute("provincias", provinciaService.findAll());
        return "listarProvincias";
    }
    
    @GetMapping("/provincias/eliminar/{id}")
    public String eliminarProvincia(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            provinciaService.delete(id);
            attributes.addFlashAttribute("mensaje", "Provincia eliminada correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar la provincia");
        }
        return "redirect:/listarProvincias";
    }
}

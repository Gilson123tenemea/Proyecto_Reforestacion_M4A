package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Suelo;

import com.example.demo.service.ISueloService;
import com.example.demo.service.ITipo_SueloService;

@Controller
public class SueloController {
	
	@Autowired
    private ISueloService sueloservice;
	
	@Autowired
    private ITipo_SueloService tipo_SueloService;
	
	@GetMapping("/suelo")
    public String crearsuelo(Model model) {
        model.addAttribute("suelos", new Suelo());
        model.addAttribute("tiposuelos", tipo_SueloService.listartiposuelos());
        return "suelo";
    }
	
	@PostMapping("/guardarsuelo")
    public String guardarSuelo(@ModelAttribute Suelo suelo, Model model) {
		try {
            
            if (suelo.getId_tiposuelo() == null) {
                throw new Exception("Debe seleccionar un Tipo de suelo.");
			}
        	sueloservice.save(suelo);
            model.addAttribute("mensaje", "Suelo guardado exitosamente");
            return "redirect:/listarsuelo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el suelo: " + e.getMessage());
            return "error";
        }
    }
	
	@GetMapping("/listarsuelo")
    public String listarSuelos(Model model) {
        model.addAttribute("titulo", "Lista de suelos");
        model.addAttribute("suelos", sueloservice.findAll());
        return "listarsuelo";
    }
	
	@GetMapping("/suelo/eliminar/{id}")
	public String eliminarSuelo(@PathVariable("id") Long id, RedirectAttributes attributes) {
	    try {
	    	sueloservice.delete(id);
	        attributes.addFlashAttribute("mensaje", "Suelo eliminado correctamente");
	    } catch (IllegalStateException e) {
	        attributes.addFlashAttribute("error", e.getMessage());
	    } catch (Exception e) {
	        attributes.addFlashAttribute("error", "Error al eliminar el Suelo");
	    }
	    return "redirect:/listarsuelo";
	}


}

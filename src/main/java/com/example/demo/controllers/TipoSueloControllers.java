package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.ITipo_SueloService;

@Controller
public class TipoSueloControllers {
	
	@Autowired
    private ITipo_SueloService tipo_SueloService;
	
	@GetMapping("/tiposuelos")
    public String creartiposueloform(Model model) {
        model.addAttribute("tiposuelo", new Tipo_Suelo());
        return "tiposuelos";
    }
	
	@PostMapping("/guardartiposuelo")
    public String guardarTiposuelo(@ModelAttribute Tipo_Suelo tipo_suelos, Model model) {
        try {
        	tipo_SueloService.save(tipo_suelos);
            model.addAttribute("mensaje", "Tipo de suelo guardado exitosamente");
            return "redirect:/listartiposuelo";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la Tipo de suelo: " + e.getMessage());
            return "error";
        }
    }
	
	@GetMapping("/listartiposuelo")
    public String listarTipoSuelos(Model model) {
        model.addAttribute("titulo", "Lista de Tipo de suelos");
        model.addAttribute("tiposuelos", tipo_SueloService.findAll());
        return "listartiposuelo";
    }
	
	
	@GetMapping("/tiposuelo/eliminar/{id}")
	public String eliminarTipoSuelo(@PathVariable("id") Long id, RedirectAttributes attributes) {
	    try {
	        tipo_SueloService.delete(id);
	        attributes.addFlashAttribute("mensaje", "Tipo de suelo eliminado correctamente");
	    } catch (IllegalStateException e) {
	        attributes.addFlashAttribute("error", e.getMessage());
	    } catch (Exception e) {
	        attributes.addFlashAttribute("error", "Error al eliminar el Tipo de Suelo");
	    }
	    return "redirect:/listartiposuelo";
	}


}

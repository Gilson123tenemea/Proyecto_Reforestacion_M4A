package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Especie;
import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.IEspecieService;
import com.example.demo.service.ITipo_SueloService;

@Controller
public class EspecieController {

	@Autowired
    private IEspecieService EspecieService;
	
	@GetMapping("/especie")
    public String crearespecieform(Model model) {
        model.addAttribute("especie", new Especie());
        return "especie";
    }
	
	@PostMapping("/guardarespecie")
    public String guardarespecie(@ModelAttribute Especie especie, Model model) {
        try {
        	EspecieService.save(especie);
            model.addAttribute("mensaje", "Especie guardada exitosamente");
            return "redirect:/listarespecie";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la Especie: " + e.getMessage());
            return "error";
        }
    }
	
	@GetMapping("/listarespecie")
    public String listarTipoSuelos(Model model) {
        model.addAttribute("titulo", "Lista de Especies");
        model.addAttribute("especie", EspecieService.findAll());
        return "listarespecie";
    }
	
	
	@GetMapping("/especie/eliminar/{id}")
	public String eliminarTipoSuelo(@PathVariable("id") Long id, RedirectAttributes attributes) {
	    try {
	    	EspecieService.delete(id);
	        attributes.addFlashAttribute("mensaje", "Especie eliminada correctamente");
	    } catch (IllegalStateException e) {
	        attributes.addFlashAttribute("error", e.getMessage());
	    } catch (Exception e) {
	        attributes.addFlashAttribute("error", "Error al eliminar la Especie");
	    }
	    return "redirect:/listarespecie";
	}

}

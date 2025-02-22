package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Suelo;
import com.example.demo.entity.Tipo_Suelo;
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
	        // Validar que se ha seleccionado un tipo de suelo
	        if (suelo.getId_tiposuelo() == null) {
	            throw new Exception("Debe seleccionar un Tipo de suelo.");
	        }
	        
	        // Guardamos el suelo
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
	    Map<Long, String> nombreTiposSuelo = new HashMap<>();
	    for (Tipo_Suelo tipoSuelo : tipo_SueloService.listartiposuelos()) {
	        nombreTiposSuelo.put(tipoSuelo.getId_tiposuelo(), tipoSuelo.getNombre_suelo());
	    }
	    model.addAttribute("nombreTiposSuelo", nombreTiposSuelo);
	    
	    return "listarsuelo";
	}
	
	 @GetMapping("/suelo/eliminar/{id}")
	    public String eliminarSuelo(@PathVariable("id") Long id, RedirectAttributes attributes) {
	        try {
	            long count = sueloservice.countParcelasBySueloId(id);
	            if (count > 0) {
	                attributes.addFlashAttribute("error", "No se puede eliminar el suelo porque tiene parcelas asociadas.");
	                return "redirect:/listarsuelo"; // Redirige si no se puede eliminar
	            }
	            sueloservice.delete(id);
	            attributes.addFlashAttribute("mensaje", "Suelo eliminado correctamente");
	        } catch (IllegalStateException e) {
	            attributes.addFlashAttribute("error", e.getMessage());
	        } catch (Exception e) {
	            attributes.addFlashAttribute("error", "Error al eliminar el Suelo");
	        }
	        return "redirect:/listarsuelo";
	    }
	
	
	
	@GetMapping("/suelo/editar/{id}")
    public String editarSuelo(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Suelo suelo = sueloservice.findOne(id);
            if (suelo == null) {
                attributes.addFlashAttribute("error", "El Suelo no existe");
                return "redirect:/listarsuelo";
            }
            model.addAttribute("suelos", suelo);
            model.addAttribute("tiposuelos", tipo_SueloService.listartiposuelos());
            model.addAttribute("titulo", "Editar Suelo");
            return "suelo"; // Redirige al formulario de edición
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar el Suelo: " + e.getMessage());
            return "redirect:/listarsuelo";
        }
    }




}

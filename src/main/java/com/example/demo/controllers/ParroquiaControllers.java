package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;

import jakarta.validation.Valid;


@Controller
public class ParroquiaControllers {

    @Autowired
    IParroquiaService parroquiaService;
    @Autowired
    ICantonService cantonService;
    
    @Autowired
    private IProvinciaService provinciaService; 

    @GetMapping("/parroquia")
    public String crearParroquiaForm(Model model) {
        model.addAttribute("parroquia", new Parroquia());
        model.addAttribute("provincias", provinciaService.findAll());
        return "parroquia";
    }

    @PostMapping("/guardarParroquia")
    public String guardarParroquia(@Valid @ModelAttribute("parroquia") Parroquia parroquia, Model model) {
        try {
            if (parroquia.getId_canton() == null) {
                throw new Exception("Debe seleccionar un Cantón.");
            }
            parroquiaService.save(parroquia);
            model.addAttribute("mensaje", "Parroquia guardada exitosamente");
            return "redirect:/listarparroquia";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la parroquia: " + e.getMessage());
            return "error";
        }
    }

    // Listar todas las parroquias
    @GetMapping("/listarparroquia")
    public String listarParroquia(Model model) {
        model.addAttribute("titulo", "Lista de Parroquias");
        model.addAttribute("parroquias", parroquiaService.findAll());
        return "listarparroquia";
    }

    @GetMapping("/parroquia/eliminar/{id}")
    public String eliminarParroquia(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            parroquiaService.delete(id);
            attributes.addFlashAttribute("mensaje", "Parroquia eliminada correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar la parroquia");
        }
        return "redirect:/listarparroquia";
    }

    @GetMapping("/parroquia/editar/{id}")
    public String editarParroquia(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Parroquia parroquia = parroquiaService.findOne(id);
            if (parroquia == null) {
                attributes.addFlashAttribute("error", "La parroquia no existe");
                return "redirect:/listarparroquia";
            }
            model.addAttribute("parroquia", parroquia);
            model.addAttribute("cantones", cantonService.findAll());
            model.addAttribute("titulo", "Editar Parroquia");
            return "parroquia"; // Redirige al formulario de edición
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar la parroquia: " + e.getMessage());
            return "redirect:/listarparroquia";
        }
    }
    

    @GetMapping("/cantones/proyectos/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvinciaProyectos(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }
}


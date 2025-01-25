package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Plantas;
import com.example.demo.service.IPlantasService;
import com.example.demo.service.IEspecieService; // Suponiendo que tienes una entidad Especie

@Controller
public class PlantaController {

    @Autowired
    private IPlantasService plantasService;

    @Autowired
    private IEspecieService especieService; // Servicio para manejar Especie

    // Crear una nueva Planta
    @GetMapping("/planta")
    public String crearPlanta(Model model) {
        model.addAttribute("planta", new Plantas());
        model.addAttribute("especies", especieService.findAll()); // Lista de especies para seleccionar
        return "planta"; // Redirige al formulario de planta
    }

    // Guardar una Planta
    @PostMapping("/guardarplanta")
    public String guardarPlanta(@ModelAttribute Plantas planta, Model model) {
        try {
            // Validar que se ha seleccionado una especie
            if (planta.getId_especie() == null) {
                throw new Exception("Debe seleccionar una Especie.");
            }

            // Guardar la planta
            plantasService.save(planta);
            model.addAttribute("mensaje", "Planta guardada exitosamente");
            return "redirect:/listarplantas";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la Planta: " + e.getMessage());
            return "error";
        }
    }

    // Listar Plantas
    @GetMapping("/listarplantas")
    public String listarPlantas(Model model) {
        model.addAttribute("titulo", "Lista de Plantas");
        model.addAttribute("plantas", plantasService.findAll());
        return "listarplantas";
    }

    // Eliminar una Planta
    @GetMapping("/planta/eliminar/{id}")
    public String eliminarPlanta(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            plantasService.delete(id);
            attributes.addFlashAttribute("mensaje", "Planta eliminada correctamente");
        } catch (IllegalStateException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar la Planta");
        }
        return "redirect:/listarplantas";
    }

    // Editar una Planta
    @GetMapping("/planta/editar/{id}")
    public String editarPlanta(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Plantas planta = plantasService.findOne(id);
            if (planta == null) {
                attributes.addFlashAttribute("error", "La Planta no existe");
                return "redirect:/listarplantas";
            }
            model.addAttribute("planta", planta);
            model.addAttribute("especies", especieService.findAll()); // Lista de especies
            model.addAttribute("titulo", "Editar Planta");
            return "planta"; // Redirige al formulario de edición
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar la Planta: " + e.getMessage());
            return "redirect:/listarplantas";
        }
    }
}

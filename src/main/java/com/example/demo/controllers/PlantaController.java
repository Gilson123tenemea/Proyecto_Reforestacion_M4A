package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Plantas;
import com.example.demo.service.IPlantasService;

import jakarta.validation.Valid;

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

    @PostMapping("/guardarplanta")
    public String guardarPlanta(@Valid @ModelAttribute Plantas planta, BindingResult result, Model model) {
        try {
            // Si hay errores de validación
            if (result.hasErrors()) {
                model.addAttribute("titulo", "Editar o Crear Planta");
                model.addAttribute("especies", especieService.findAll()); // Lista de especies
                model.addAttribute("planta", planta);

                // Extraer los mensajes de error y agregarlos al modelo
                StringBuilder errores = new StringBuilder();
                result.getAllErrors().forEach(error -> errores.append(error.getDefaultMessage()).append("<br>"));
                model.addAttribute("error", errores.toString());

                return "planta"; // Volver a cargar la vista con los errores
            }

            // Validar que se ha seleccionado una especie
            if (planta.getId_especie() == null) {
                model.addAttribute("error", "Debe seleccionar una especie.");
                model.addAttribute("titulo", "Editar o Crear Planta");
                model.addAttribute("especies", especieService.findAll());
                model.addAttribute("planta", planta);
                return "planta"; // Volver a la vista con el error
            }

            // Validar que el nombre común no esté vacío
            if (planta.getNombre_comun() == null || planta.getNombre_comun().isEmpty()) {
                model.addAttribute("error", "El nombre común no puede estar vacío.");
                model.addAttribute("titulo", "Editar o Crear Planta");
                model.addAttribute("especies", especieService.findAll());
                model.addAttribute("planta", planta);
                return "planta"; // Volver a la vista con el error
            }

            // Verificar si el nombre científico ya existe (ignorando mayúsculas y minúsculas)
            String nombreCientifico = planta.getNombre_cientifico().trim().toLowerCase();
            List<Plantas> plantasExistentes = plantasService.findAll(); // Lista de todas las plantas
            for (Plantas plantaExistente : plantasExistentes) {
                if (plantaExistente.getNombre_cientifico().trim().toLowerCase().equals(nombreCientifico)) {
                    model.addAttribute("error", "Ya existe una planta con ese nombre científico.");
                    model.addAttribute("titulo", "Editar o Crear Planta");
                    model.addAttribute("especies", especieService.findAll());
                    model.addAttribute("planta", planta);
                    return "planta"; // Volver a la vista con el mensaje de error
                }
            }

            // Si la planta ya existe, actualizarla
            if (planta.getId_plantas() != null) {
                Plantas plantaExistente = plantasService.findOne(planta.getId_plantas());
                if (plantaExistente != null) {
                    // Actualizar los datos de la planta
                    plantaExistente.setNombre_comun(planta.getNombre_comun());
                    plantaExistente.setNombre_cientifico(planta.getNombre_cientifico());
                    plantaExistente.setClima(planta.getClima());
                    plantaExistente.setTamaño(planta.getTamaño());
                    plantaExistente.setColor_hojas(planta.getColor_hojas());
                    plantaExistente.setId_especie(planta.getId_especie());
                    planta = plantaExistente; // Asignar los cambios a la planta original
                }
            }

            // Guardar la planta
            plantasService.save(planta);
            model.addAttribute("mensaje", "Planta guardada exitosamente");

            return "redirect:/listarplantas"; // Redirigir al listado de plantas

        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar la planta: " + e.getMessage());
            return "error"; // Mostrar una vista de error
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

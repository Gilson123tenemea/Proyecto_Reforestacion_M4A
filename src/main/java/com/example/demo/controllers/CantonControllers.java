package com.example.demo.controllers;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CantonControllers {

    @Autowired
    ICantonService cantonService;
    @Autowired
    IProvinciaService provinciaService;

    @GetMapping("/canton")
    public String crearCantonForm(Model model) {
        model.addAttribute("canton", new Canton());
        model.addAttribute("provincias", provinciaService.listarProvincias());
        return "canton";
    }

    // Guardar cantón
    @PostMapping("/guardarCanton")
    public String guardarCanton(@ModelAttribute Canton canton, RedirectAttributes flash) {
        try {
            // Verificar si el cantón tiene asignada una provincia
            if (canton.getId_provincia() == null) {
                throw new Exception("Debe seleccionar una provincia.");
            }

            // Si el cantón tiene un ID, actualizar sus parroquias asociadas
            if (canton.getId_canton() != null) {
                Canton cantonActual = cantonService.findOne(canton.getId_canton());
                if (cantonActual != null && canton.getParroquia() != null) {
                    for (Parroquia parroquia : canton.getParroquia()) {
                        // Establecer el ID del cantón a las parroquias si no lo tienen
                        if (parroquia.getId_canton() == null) {
                            parroquia.setId_canton(canton.getId_canton());
                        }
                    }
                }
            }

            // Guardar el cantón
            cantonService.save(canton);

            // Mensaje de éxito
            flash.addFlashAttribute("success", "Cantón guardado exitosamente");

            // Redirigir a la lista de cantones
            return "redirect:/listarCanton";
        } catch (Exception e) {
            // Mensaje de error
            flash.addFlashAttribute("error", "Error al guardar el cantón: " + e.getMessage());
            return "error";
        }
    }

    // Listar todos los cantones
    @GetMapping("/listarCanton")
    public String listarCanton(Model model) {
        model.addAttribute("titulo", "Lista de Cantones");
        model.addAttribute("canton", cantonService.findAll());
        return "listarCanton";
    }

    // Eliminar cantón
    @GetMapping("/canton/eliminar/{id}")
    public String eliminarCanton(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            cantonService.delete(id);
            attributes.addFlashAttribute("mensaje", "Cantón eliminado correctamente");
        } catch (IllegalStateException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar el Cantón");
        }
        return "redirect:/listarCanton";
    }

    // Editar cantón
    @GetMapping("/canton/editar/{id}")
    public String editarCanton(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Canton canton = cantonService.findOne(id);
            if (canton == null) {
                attributes.addFlashAttribute("error", "El Cantón no existe");
                return "redirect:/listarCanton";
            }
            model.addAttribute("canton", canton);
            model.addAttribute("provincias", provinciaService.listarProvincias());
            model.addAttribute("titulo", "Editar Cantón");
            return "canton"; // Redirige al formulario de edición
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar el Cantón: " + e.getMessage());
            return "redirect:/listarCanton";
        }
    }
}

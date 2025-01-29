package com.example.demo.controllers;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IProvinciaService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CantonControllers {

    @Autowired
    private ICantonService cantonService;
    
    @Autowired
    private IProvinciaService provinciaService;  // Añadir este servicio


    // Listar Cantones
    @RequestMapping(value = "/listarcantones", method = RequestMethod.GET)
    public String listarCantones(Model model) {
        model.addAttribute("titulo", "Listado de Cantones");
        model.addAttribute("cantones", cantonService.findAll());
        return "listarcantones";
    }

    // Crear un nuevo Canton
    @RequestMapping(value = "/cantones", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Canton canton = new Canton();
        model.put("canton", canton);
        model.put("titulo", "Formulario de Nuevo Cantón");

        // Cargar las provincias y agregarlas al modelo
        model.put("provincias", provinciaService.findAll());  // Asegúrate de que el servicio de provincias esté cargando las provincias

        return "cantones";
    }

    // Editar un Canton existente
    @RequestMapping(value = "/canton/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Canton canton = null;
        if (id > 0) {
            canton = cantonService.findOne(id);
            if (canton == null) {
                flash.addFlashAttribute("error", "El Id del Cantón no existe en la base de datos");
                return "redirect:/listarcantones";
            }
        } else {
            flash.addFlashAttribute("error", "El Id del Cantón no puede ser 0");
            return "redirect:/listarcantones";
        }

        model.put("canton", canton);
        model.put("titulo", "Editar Cantón");

        // Cargar las provincias y agregarlas al modelo
        model.put("provincias", provinciaService.findAll());  // Asegúrate de que las provincias se carguen

        return "cantones";
    }
    
    

    // Guardar un Canton (Crear o Actualizar)
    @RequestMapping(value = "/cantones", method = RequestMethod.POST)
    public String guardarCanton(@Valid @ModelAttribute("canton") Canton canton, RedirectAttributes flash) {
        try {
            // Verificar si la lista de parroquias es null y, en tal caso, inicializarla como una lista vacía
            if (canton.getParroquia() == null) {
                canton.setParroquia(new ArrayList<>());
            }

            if (canton.getId_canton() != null) {
                Canton cantonExistente = cantonService.findOne(canton.getId_canton());
                if (cantonExistente == null) {
                    flash.addFlashAttribute("error", "El Canton con ese ID no existe");
                    return "redirect:/listarcantones";
                }
                cantonExistente.setId_provincia(canton.getId_provincia());
                cantonExistente.setNombreCanton(canton.getNombreCanton());
                cantonExistente.setParroquia(canton.getParroquia()); // Actualizar la lista de parroquias
                cantonService.save(cantonExistente);
                flash.addFlashAttribute("success", "Canton actualizado exitosamente");
            } else {
                cantonService.save(canton);
                flash.addFlashAttribute("success", "Canton creado exitosamente");
            }
            return "redirect:/listarcantones";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el Canton: " + e.getMessage());
            return "redirect:/listarcantones";
        }
    }

    // Eliminar un Canton
    @RequestMapping(value = "/canton/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            cantonService.delete(id);
            flash.addFlashAttribute("success", "Canton eliminado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el Canton: " + e.getMessage());
        }
        return "redirect:/listarcantones";
    }
}

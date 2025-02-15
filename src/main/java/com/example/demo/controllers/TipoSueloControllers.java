package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.ITipo_SueloService;

import jakarta.validation.Valid;

@Controller
public class TipoSueloControllers {

    @Autowired
    private ITipo_SueloService tipo_SueloService;

    // Listar Tipos de Suelo
    @RequestMapping(value = "/listartiposuelo", method = RequestMethod.GET)
    public String listarTipoSuelo(Model model) {
        model.addAttribute("titulo", "Listado de Tipos de Suelo");
        model.addAttribute("tiposuelos", tipo_SueloService.findAll());
        return "listartiposuelo";
    }

    // Crear un nuevo Tipo de Suelo
    @RequestMapping(value = "/tiposuelos", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Tipo_Suelo tipoSuelo = new Tipo_Suelo();
        model.put("tiposuelo", tipoSuelo);  // Se asegura de que el modelo tenga el atributo "tiposuelo"
        model.put("titulo", "Formulario de Nuevo Tipo de Suelo");
        return "tiposuelos";
    }

    // Editar un Tipo de Suelo
    @RequestMapping(value = "/tiposuelo/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Tipo_Suelo tipoSuelo = null;
        if (id > 0) {
            tipoSuelo = tipo_SueloService.findOne(id);
            if (tipoSuelo == null) {
                flash.addFlashAttribute("error", "El Id del Tipo de Suelo no existe en la base de datos");
                return "redirect:/listartiposuelo";
            }
        } else {
            flash.addFlashAttribute("error", "El Id del Tipo de Suelo no puede ser 0");
            return "redirect:/listartiposuelo";
        }
        model.put("tiposuelo", tipoSuelo); // Se pasa el tipo de suelo para que se edite en el formulario
        model.put("titulo", "Editar Tipo de Suelo");
        return "tiposuelos";
    }

 // Guardar o actualizar un Tipo de Suelo
    @RequestMapping(value = "/tiposuelos", method = RequestMethod.POST)
    public String guardarTiposuelo(@Valid @ModelAttribute("tiposuelo") Tipo_Suelo tipo_suelos, BindingResult result, Model model, RedirectAttributes flash) {
        try {
            // Si hay errores de validación
            if (result.hasErrors()) {
                model.addAttribute("titulo", "Editar o Crear Tipo de Suelo");
                model.addAttribute("tiposuelo", tipo_suelos);

                // Extraer los mensajes de error y agregarlos al modelo
                StringBuilder errores = new StringBuilder();
                result.getAllErrors().forEach(error -> errores.append(error.getDefaultMessage()).append("<br>"));
                model.addAttribute("error", errores.toString());

                return "tiposuelos"; // Volver a cargar la vista con los errores
            }

            // Validar que el nombre no esté vacío
            if (tipo_suelos.getNombre_suelo() == null || tipo_suelos.getNombre_suelo().isEmpty()) {
                model.addAttribute("error", "El nombre del tipo de suelo no puede estar vacío.");
                model.addAttribute("titulo", "Editar o Crear Tipo de Suelo");
                model.addAttribute("tiposuelo", tipo_suelos);
                return "tiposuelos"; // Volver a la vista con el error
            }

            // Validar que la descripción no esté vacía
            if (tipo_suelos.getDescripcion() == null || tipo_suelos.getDescripcion().isEmpty()) {
                model.addAttribute("error", "La descripción no puede estar vacía.");
                model.addAttribute("titulo", "Editar o Crear Tipo de Suelo");
                model.addAttribute("tiposuelo", tipo_suelos);
                return "tiposuelos"; // Volver a la vista con el error
            }

            // Verificar si el tipo de suelo ya existe por nombre (ignorando mayúsculas y minúsculas),
            // pero solo si el ID del tipo de suelo no es el mismo que el que se está editando
            String nombreSuelo = tipo_suelos.getNombre_suelo().trim().toLowerCase();
            List<Tipo_Suelo> tiposSueloExistentes = tipo_SueloService.findAll(); // Lista de todos los tipos de suelo
            for (Tipo_Suelo tipoSueloExistente : tiposSueloExistentes) {
                // Si el tipo de suelo existe y el ID no es el mismo (evita comparar el mismo tipo de suelo)
                if (tipoSueloExistente.getNombre_suelo().trim().toLowerCase().equals(nombreSuelo) &&
                    !tipoSueloExistente.getId_tiposuelo().equals(tipo_suelos.getId_tiposuelo())) {
                    model.addAttribute("error", "Ya existe un tipo de suelo con ese nombre.");
                    model.addAttribute("titulo", "Editar o Crear Tipo de Suelo");
                    model.addAttribute("tiposuelo", tipo_suelos);
                    return "tiposuelos"; // Volver a la vista con el mensaje de error
                }
            }

            // Si el ID del tipo de suelo ya existe, actualizarlo
            if (tipo_suelos.getId_tiposuelo() != null) {
                Tipo_Suelo tipoSueloExistente = tipo_SueloService.findOne(tipo_suelos.getId_tiposuelo());
                if (tipoSueloExistente == null) {
                    flash.addFlashAttribute("error", "El Tipo de Suelo con ese ID no existe");
                    return "redirect:/tiposuelos"; // Redirigir si no existe
                }

                // Actualizar los datos del tipo de suelo
                tipoSueloExistente.setNombre_suelo(tipo_suelos.getNombre_suelo());
                tipoSueloExistente.setDescripcion(tipo_suelos.getDescripcion());
                tipoSueloExistente.setColor(tipo_suelos.getColor());
                tipoSueloExistente.setTextura(tipo_suelos.getTextura());
                tipoSueloExistente.setDensidad(tipo_suelos.getDensidad());
                tipoSueloExistente.setPh(tipo_suelos.getPh());
                tipoSueloExistente.setComposicion(tipo_suelos.getComposicion());
                tipoSueloExistente.setFertilidad(tipo_suelos.getFertilidad());
                tipoSueloExistente.setUso_del_suelo(tipo_suelos.getUso_del_suelo());

                tipo_SueloService.save(tipoSueloExistente);
                flash.addFlashAttribute("success", "Tipo de Suelo actualizado exitosamente");
            } else {
                // Si no existe el ID, es una creación
                tipo_SueloService.save(tipo_suelos);
                flash.addFlashAttribute("success", "Tipo de Suelo guardado exitosamente");
            }

            return "redirect:/listartiposuelo"; // Redirigir al listado de tipos de suelo si todo es correcto
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el Tipo de Suelo: " + e.getMessage());
            return "redirect:/tiposuelos"; // Redirigir en caso de error
        }
    }


    // Eliminar un Tipo de Suelo
    @RequestMapping(value = "/tiposuelo/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            tipo_SueloService.delete(id);
            flash.addFlashAttribute("success", "Tipo de Suelo eliminado correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el Tipo de Suelo");
        }
        return "redirect:/listartiposuelo";
    }
}

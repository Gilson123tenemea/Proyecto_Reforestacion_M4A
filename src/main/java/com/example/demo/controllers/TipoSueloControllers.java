package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.ITipo_SueloService;

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
        model.put("tiposuelo", tipoSuelo);
        model.put("titulo", "Formulario de Nuevo Tipo de Suelo");
        return "tiposuelos";
    }

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
        model.put("tiposuelo", tipoSuelo);
        model.put("titulo", "Editar Tipo de Suelo");
        return "tiposuelos";
    }


    @RequestMapping(value = "/tiposuelos", method = RequestMethod.POST)
    public String guardarTiposuelo(Tipo_Suelo tipo_suelos, RedirectAttributes flash) {
        try {
            if (tipo_suelos.getId_tiposuelo() != null) {
                // Si el ID existe, es una actualización
                Tipo_Suelo tipoSueloExistente = tipo_SueloService.findOne(tipo_suelos.getId_tiposuelo());
                if (tipoSueloExistente == null) {
                    flash.addFlashAttribute("error", "El Tipo de Suelo con ese ID no existe");
                    return "redirect:/listartiposuelo";
                }
                // Actualizar los campos necesarios (si es necesario)
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
                // Si el ID no existe, es una creación
                tipo_SueloService.save(tipo_suelos);
                flash.addFlashAttribute("success", "Tipo de Suelo guardado exitosamente");
            }
            
            return "redirect:/listartiposuelo";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar el Tipo de Suelo: " + e.getMessage());
            return "redirect:/listartiposuelo";
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

package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Especie;
import com.example.demo.service.IEspecieService;

@Controller
public class EspecieController {

    @Autowired
    private IEspecieService especieService;

    // Listar Especies
    @RequestMapping(value = "/listarespecie", method = RequestMethod.GET)
    public String listarEspecies(Model model) {
        model.addAttribute("titulo", "Listado de Especies");
        model.addAttribute("especies", especieService.findAll());
        return "listarespecie";
    }

    // Crear una nueva Especie
    @RequestMapping(value = "/especie", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Especie especie = new Especie();
        model.put("especie", especie);
        model.put("titulo", "Formulario de Nueva Especie");
        return "especie";
    }

    @RequestMapping(value = "/especie/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Especie especie = null;
        if (id > 0) {
            especie = especieService.findOne(id);
            if (especie == null) {
                flash.addFlashAttribute("error", "El Id de la Especie no existe en la base de datos");
                return "redirect:/listarespecie";
            }
        } else {
            flash.addFlashAttribute("error", "El Id de la Especie no puede ser 0");
            return "redirect:/listarespecie";
        }
        model.put("especie", especie);
        model.put("titulo", "Editar Especie");
        return "especie";
    }

    @RequestMapping(value = "/especie", method = RequestMethod.POST)
    public String guardarEspecie(Especie especie, RedirectAttributes flash) {
        try {
            if (especie.getId_especie() != null) {
                Especie especieExistente = especieService.findOne(especie.getId_especie());
                if (especieExistente == null) {
                    flash.addFlashAttribute("error", "La Especie con ese ID no existe");
                    return "redirect:/listarespecie";
                }
                // Actualizar campos necesarios
                especieExistente.setNombre(especie.getNombre());
                
                especieService.save(especieExistente);
                flash.addFlashAttribute("success", "Especie actualizada exitosamente");
            } else {
                especieService.save(especie);
                flash.addFlashAttribute("success", "Especie guardada exitosamente");
            }
            return "redirect:/listarespecie";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la Especie: " + e.getMessage());
            return "redirect:/listarespecie";
        }
    }

    // Eliminar una Especie
    @RequestMapping(value = "/especie/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            especieService.delete(id);
            flash.addFlashAttribute("success", "Especie eliminada correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la Especie");
        }
        return "redirect:/listarespecie";
    }
}

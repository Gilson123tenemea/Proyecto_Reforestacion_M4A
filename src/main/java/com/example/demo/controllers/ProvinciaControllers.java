package com.example.demo.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Provincia;
import com.example.demo.service.IProvinciaService;



@Controller
public class ProvinciaControllers {

    @Autowired
    private IProvinciaService provinciaService;

    // Listar Provincias
    @RequestMapping(value="/listarprovincia", method=RequestMethod.GET)
    public String listarProvincia(Model model) {
        model.addAttribute("titulo", "Listado de Provincias");
        model.addAttribute("provincias", provinciaService.findAll());
        return "listarprovincia";
    }

    // Crear una nueva provincia
    @RequestMapping(value="/formprovincia", method=RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Provincia provincia = new Provincia();
        model.put("provincia", provincia);
        model.put("titulo", "Formulario de Nueva Provincia");
        return "formprovincia";
    }

    // Editar provincia
    @RequestMapping(value="/formprovincia/{id}", method=RequestMethod.GET)
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Provincia provincia = null;
        if (id > 0) {
            provincia = provinciaService.findOne(id);
            if (provincia == null) {
                flash.addFlashAttribute("error", "El Id de la provincia no existe en la base de datos");
                return "redirect:/listarprovincia";
            }
        } else {
            flash.addFlashAttribute("error", "El Id de la provincia no puede ser 0");
            return "redirect:/listarprovincia";
        }
        model.put("provincia", provincia);
        model.put("titulo", "Editar Provincia");
        return "formprovincia";
    }

    // Guardar provincia (crear o editar)
    @RequestMapping(value = "/formprovincia", method = RequestMethod.POST)
    public String guardar(Provincia provincia, RedirectAttributes flash) {
        // Si la provincia tiene cantones asociados, asegúrate de que cada canton tenga la provincia correcta.
        if (provincia.getCanton() != null) {
            for (Canton canton : provincia.getCanton()) {
                if (canton.getId_provincia() == null) {
                    canton.setId_provincia(provincia.getId_provincia());  // Establecer la relación si no existe
                }
            }
        }

        String mensajeFls = (provincia.getId_provincia() != null)
            ? "El registro de la provincia se ha editado con éxito"
            : "El registro de la provincia se ha creado con éxito";
        
        provinciaService.save(provincia);  // Guardar la provincia y sus cantones relacionados
        flash.addFlashAttribute("success", mensajeFls);
        return "redirect:/listarprovincia";
    }

    // Eliminar provincia
    @RequestMapping(value="/eliminarprovincia/{id}", method=RequestMethod.GET)
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
        try {
            if (id > 0) {
                provinciaService.delete(id);
                flash.addFlashAttribute("success", "La provincia ha sido eliminada correctamente.");
            }
            return "redirect:/listarprovincia";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se puede eliminar la provincia porque está siendo referenciada por otras entidades.");
            return "redirect:/listarprovincia";
        }
    }
}

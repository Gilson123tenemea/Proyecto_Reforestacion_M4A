package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Provincia;
import com.example.demo.service.IProvinciaService;

import jakarta.validation.Valid;

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
    
    // Crear una nueva provincia (Formulario)
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

    // Guardar una provincia (Nueva o Editada)
    @RequestMapping(value = "/formprovincia", method = RequestMethod.POST)
    public String guardarProvincia(@Valid @ModelAttribute("provincia") Provincia provincia, Model model) {
        // Validación: Verificar si la provincia ya existe (ignorando mayúsculas y minúsculas)
        String nombreProvincia = provincia.getNombreProvincia().trim().toLowerCase();
        List<Provincia> provinciasExistentes = provinciaService.findAll();

        for (Provincia provinciaExistente : provinciasExistentes) {
            if (provinciaExistente.getNombreProvincia().trim().toLowerCase().equals(nombreProvincia)) {
                model.addAttribute("error", "La provincia ya existe. Si deseas, puedes continuar y repetirla.");
                model.addAttribute("titulo", "Formulario de Nueva Provincia");
                return "formprovincia";  // Volver al formulario con el mensaje de error
            }
        }

        // Si la provincia ya existe, mantener los cantones actuales
        if (provincia.getId_provincia() != null) {
            Provincia provinciaActual = provinciaService.findOne(provincia.getId_provincia());
            if (provinciaActual != null) {
                provincia.setCanton(provinciaActual.getCanton());
            }
        }

        // Asignar la provincia a cada cantón si existen
        if (provincia.getCanton() != null) {
            for (Canton canton : provincia.getCanton()) {
                canton.setId_provincia(provincia.getId_provincia());
            }
        }

        provinciaService.save(provincia);
        model.addAttribute("success", "La provincia se ha guardado correctamente.");
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

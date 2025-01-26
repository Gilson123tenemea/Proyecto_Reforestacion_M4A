package com.example.demo.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.service.ITipo_ActividadesService;

@Controller
public class TipoActividadesController {

    @Autowired
    private ITipo_ActividadesService tipoActividadesService;

    @GetMapping(value="/ver/{id}")
    public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Tipo_Actividades tactividad = tipoActividadesService.findOne(id);
        
        if (tactividad == null) {
            flash.addFlashAttribute("error", "La Actividad no se encuentra en la base de datos");
            return "redirect:/listartipo";
        }

        model.put("tactividad", tactividad);
        model.put("titulo", "Información de las actividades");
        return "ver";
    }




    @RequestMapping(value="/listartipo", method=RequestMethod.GET)
    public String listartipo(Model model) {
        model.addAttribute("titulo", "Listado de Actividades");
        model.addAttribute("tactividad", tipoActividadesService.findAll());
        return "listartipo";
    }


    @RequestMapping(value="/formtipo", method=RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Tipo_Actividades tactividad = new Tipo_Actividades();
        model.put("tactividad", tactividad);
        model.put("titulo", "Formulario de Tipo Actividad");
        return "formtipo";
    }

    @RequestMapping(value="/formtipo/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Tipo_Actividades tactividad = null;
        if (id > 0) {
            tactividad = tipoActividadesService.findOne(id);
            if (tactividad == null) {
                flash.addFlashAttribute("error", "El Id del tipo de actividad no existe en la base de datos");
                return "redirect:/listartipo";
            }
        } else {
            flash.addFlashAttribute("error", "El Id del tipo de actividad no puede ser 0");
            return "redirect:/listartipo";
        }
        model.put("tactividad", tactividad);
        model.put("titulo", "Editar Actividad");
        return "formtipo";
    }

    @RequestMapping(value = "/formtipo", method = RequestMethod.POST)
    public String guardar(Tipo_Actividades tactividad, RedirectAttributes flash) {
        String mensajeFls = (tactividad.getId_tipoActividades() != null) 
            ? "El registro del tipo de actividades se ha editado con éxito" 
            : "El registro de tipo de actividad se ha creado con éxito";
        tipoActividadesService.save(tactividad);
        flash.addFlashAttribute("success", mensajeFls);
        return "redirect:/listartipo";
    }


    
    @RequestMapping(value="/eliminartipo/{id}", method=RequestMethod.GET)
    public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
        try {
            if (id > 0) {
                tipoActividadesService.delete(id);
                flash.addFlashAttribute("success", "La actividad ha sido eliminada correctamente.");
            }
            return "redirect:/listartipo";
        } catch (Exception e) {
           
            flash.addFlashAttribute("error", "No se puede eliminar la actividad porque está siendo referenciada por otras entidades.");
            return "redirect:/listartipo";
        }
    }

}



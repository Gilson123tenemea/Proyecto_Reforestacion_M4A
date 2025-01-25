package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.service.IAsignacion_proyectoActiService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.ITipo_ActividadesService;

@Controller
@RequestMapping("/asignaciones") 
public class AsignarProyectosController {

    @Autowired
    private IAsignacion_proyectoActiService asignacionService;

    @Autowired
    private IProyectoServices proyectoService;

    @Autowired
    private ITipo_ActividadesService tpactividadService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listarAsignacion(Model model) {
        model.addAttribute("titulo", "Listado de Asignaciones");
        model.addAttribute("asignaciones", asignacionService.findAll());
        return "listarasignacion";
    }

    @RequestMapping(value = "/nuevo", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Asignacion_proyectoActi asignarpro = new Asignacion_proyectoActi();
        model.put("asignacion", asignarpro);
        model.put("titulo", "Formulario de Nueva Asignación de Actividades");
        model.put("tipo_actividades", tpactividadService.listaractividades());
        model.put("proyectos", proyectoService.listarproyectos());
        return "asignaciones";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id == null || id <= 0) {
            flash.addFlashAttribute("error", "El ID de la Asignación es inválido");
            return "redirect:/asignaciones/listar";
        }
        Asignacion_proyectoActi asignacion = asignacionService.findOne(id);
        if (asignacion == null) {
            flash.addFlashAttribute("error", "El ID de la Asignación no existe en la base de datos");
            return "redirect:/asignaciones/listar";
        }
        model.put("asignacion", asignacion);
        model.put("titulo", "Editar Asignación");
        model.put("tipo_actividades", tpactividadService.listaractividades());
        model.put("proyectos", proyectoService.listarproyectos());
        return "asignaciones";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarAsignacion(Asignacion_proyectoActi asignacion, RedirectAttributes flash) {
        try {
            if (asignacion.getId_asignacionproyecto() != null) {
                asignacionService.save(asignacion); 
                flash.addFlashAttribute("success", "Asignación actualizada exitosamente");
            } else {
                asignacionService.save(asignacion);
                flash.addFlashAttribute("success", "Asignación guardada exitosamente");
            }
            return "redirect:/asignaciones/listar";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la asignación: " + e.getMessage());
            return "redirect:/asignaciones/listar";
        }
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
        try {
            asignacionService.delete(id);
            flash.addFlashAttribute("success", "Asignación eliminada correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la asignación: " + e.getMessage());
        }
        return "redirect:/asignaciones/listar";
    }
}


	


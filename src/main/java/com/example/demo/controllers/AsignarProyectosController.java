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
public class AsignarProyectosController {

    @Autowired
    private IProyectoServices proyectoService;

    @Autowired
    private ITipo_ActividadesService actividadService;
    
    @Autowired
    private IAsignacion_proyectoActiService asignar_p;

    
    @RequestMapping(value = "/listarAsignaciones", method = RequestMethod.GET)
    public String listarAsignaciones(Model model) {
        model.addAttribute("titulo", "Listado de Asignaciones de Proyecto");
        model.addAttribute("asignaciones", asignar_p.findAll()); 
        return "listarAsignaciones";
    }

    
    @RequestMapping(value = "/asignacion", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Asignacion_proyectoActi asignacion = new Asignacion_proyectoActi();
        model.put("asignacion", asignacion);
        model.put("titulo", "Formulario de Nueva Asignación");
        model.put("proyectos", proyectoService.listarproyectos()); 
        model.put("actividades", actividadService.listaractividades());
        return "asignacion";
    }

    
    @RequestMapping(value = "/asignacion/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Asignacion_proyectoActi asignacion = null;
        if (id > 0) {
            asignacion = asignar_p.findOne(id); 
            if (asignacion == null) {
                flash.addFlashAttribute("error", "La Asignación con ese ID no existe");
                return "redirect:/listarAsignaciones";
            }
        } else {
            flash.addFlashAttribute("error", "El Id de la Asignación no puede ser 0");
            return "redirect:/listarAsignaciones";
        }
        model.put("asignacion", asignacion);
        model.put("titulo", "Editar Asignación");
        model.put("proyectos", proyectoService.listarproyectos());
        model.put("actividades", actividadService.listaractividades());
        return "asignacion";
    }

   
    @RequestMapping(value = "/asignacion", method = RequestMethod.POST)
    public String guardarAsignacion(Asignacion_proyectoActi asignacion, RedirectAttributes flash) {
        try {
            if (asignacion.getId_asignacionproyecto() != null) {
                // Busca la asignación existente
                Asignacion_proyectoActi asignacionExistente = asignar_p.findOne(asignacion.getId_asignacionproyecto());
                if (asignacionExistente == null) {
                    flash.addFlashAttribute("error", "La Asignación con ese ID no existe");
                    return "redirect:/listarAsignaciones";
                }
                // Actualiza solo los campos necesarios
                asignacionExistente.setId_proyecto(asignacion.getId_proyecto());
                asignacionExistente.setId_tipoActividades(asignacion.getId_tipoActividades());
                asignacionExistente.setEstado(asignacion.getEstado());
                asignacionExistente.setMeta_real(asignacion.getMeta_real());
                asignacionExistente.setMeta_deseada(asignacion.getMeta_deseada());

                // Guarda la entidad actualizada
                asignar_p.save(asignacionExistente);
                flash.addFlashAttribute("success", "Asignación actualizada exitosamente");
            } else {
                // Guarda una nueva asignación
                asignar_p.save(asignacion);
                flash.addFlashAttribute("success", "Asignación guardada exitosamente");
            }
            return "redirect:/listarAsignaciones";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la Asignación: " + e.getMessage());
            return "redirect:/listarAsignaciones";
        }
    }


    @RequestMapping(value = "/asignacion/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
        	asignar_p.delete(id); 
            flash.addFlashAttribute("success", "Asignación eliminada correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la Asignación");
        }
        return "redirect:/listarAsignaciones";
    }
}



	


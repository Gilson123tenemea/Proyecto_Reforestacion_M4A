package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Tipo_Actividades;
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
    public String listarAsignaciones(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        model.addAttribute("titulo", "Listado de Asignaciones de Proyecto");
        List<Asignacion_proyectoActi> asignaciones = asignar_p.findByAdministradorId(idAdministrador);
        model.addAttribute("asignaciones", asignaciones); 
        
        
        return "listarAsignaciones";
    }
    
    
    @RequestMapping(value = "/asignacion", method = RequestMethod.GET)
    public String crear(Map<String, Object> model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        Asignacion_proyectoActi asignacion = new Asignacion_proyectoActi();
        model.put("asignacion", asignacion);
        model.put("titulo", "Formulario de Nueva Asignación");
        List<Proyecto> proyectos = proyectoService.findByAdministradorId(idAdministrador);
        model.put("proyectos", proyectos); 
        List<Tipo_Actividades> actividades = actividadService.findByAdministradorId(idAdministrador);
        model.put("actividades", actividades);

        return "asignacion";
    }

    
    @RequestMapping(value = "/asignacion/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, @SessionAttribute("idAdministrador") Long idAdministrador) {
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

        List<Proyecto> proyectos = proyectoService.findByAdministradorId(idAdministrador);
        model.put("proyectos", proyectos);

        List<Tipo_Actividades> actividades = actividadService.findByAdministradorId(idAdministrador);
        model.put("actividades", actividades);

        return "asignacion";
    }
    
    

   
    @RequestMapping(value = "/asignacion", method = RequestMethod.POST)
    public String guardarAsignacion(Asignacion_proyectoActi asignacion, RedirectAttributes flash) {
        try {
            // Guarda nueva asignación o actualiza existente
            if (asignacion.getId_asignacionproyecto() != null) {
                // Busca la asignación existente
                Asignacion_proyectoActi asignacionExistente = asignar_p.findOne(asignacion.getId_asignacionproyecto());
                if (asignacionExistente == null) {
                    flash.addFlashAttribute("error", "La Asignación con ese ID no existe");
                    return "redirect:/listarAsignaciones";
                }

                // Actualiza los campos necesarios
                asignacionExistente.setId_proyecto(asignacion.getId_proyecto());
                asignacionExistente.setId_tipoActividades(asignacion.getId_tipoActividades());
                asignacionExistente.setEstado(asignacion.getEstado());
                asignacionExistente.setMeta_deseada(asignacion.getMeta_deseada());
                asignar_p.save(asignacionExistente);
                flash.addFlashAttribute("success", "Asignación actualizada exitosamente");
            } else {
                // Guarda nueva asignación
                asignar_p.save(asignacion);
                flash.addFlashAttribute("success", "Asignación guardada exitosamente");
            }

            // Contar las actividades asignadas al proyecto
            long totalActividades = asignar_p.countByProyectoId(asignacion.getId_proyecto());

            // Calcular el porcentaje por actividad
            double porcentajePorActividad = totalActividades > 0 ? 100.0 / totalActividades : 0;

            // Obtener todas las asignaciones del proyecto y actualizar su porcentaje
            List<Asignacion_proyectoActi> asignacionesProyecto = asignar_p.findByProyectoId(asignacion.getId_proyecto());
            for (Asignacion_proyectoActi a : asignacionesProyecto) {
                a.setPorcentajeActividad(porcentajePorActividad);
                asignar_p.save(a); // Guarda cada actividad con el nuevo porcentaje
            }

            // Imprimir en la consola
            System.out.println("Total de Actividades asignadas al Proyecto ID " + asignacion.getId_proyecto() + ": " + totalActividades);
            System.out.println("Porcentaje asignado a cada actividad: " + porcentajePorActividad + "%");

            return "redirect:/listarAsignaciones";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la Asignación: " + e.getMessage());
            return "redirect:/listarAsignaciones";
        }
    }
    
    @RequestMapping(value = "/proyecto/{id}/count", method = RequestMethod.GET)
    public String contarAsignaciones(@PathVariable("id") Long idProyecto, Model model) {
        long totalActividades = asignar_p.countByProyectoId(idProyecto);
        model.addAttribute("totalActividades", totalActividades);
        model.addAttribute("idProyecto", idProyecto);
        
        return "resultadoConteo"; // Nombre de la vista para mostrar el resultado
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



	


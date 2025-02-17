package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

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
    public String guardarAsignacion(@Valid Asignacion_proyectoActi asignacion, 
                                     BindingResult result, 
                                     RedirectAttributes flash, 
                                     Model model, 
                                     @SessionAttribute("idAdministrador") Long idAdministrador) {
        try {
            // Si hay errores de validación
            if (result.hasErrors()) {
                model.addAttribute("titulo", "Formulario de Nueva Asignación");
                model.addAttribute("asignacion", asignacion);
                model.addAttribute("proyectos", proyectoService.findByAdministradorId(idAdministrador));
                model.addAttribute("actividades", actividadService.findByAdministradorId(idAdministrador));

                // Extraer los mensajes de error y agregarlos al modelo
                StringBuilder errores = new StringBuilder();
                result.getAllErrors().forEach(error -> errores.append(error.getDefaultMessage()).append("<br>"));
                model.addAttribute("error", errores.toString());

                return "asignacion"; // Volver a cargar la vista con los errores
            }

            // Verificar si ya existe una asignación para el mismo proyecto y actividad
            List<Asignacion_proyectoActi> asignacionesExistentes = asignar_p.findByProyectoId(asignacion.getId_proyecto());
            for (Asignacion_proyectoActi a : asignacionesExistentes) {
                if (a.getId_tipoActividades().equals(asignacion.getId_tipoActividades())) {
                    model.addAttribute("error", "La actividad ya está asignada a este proyecto");
                    model.addAttribute("titulo", "Formulario de Nueva Asignación");
                    model.addAttribute("asignacion", asignacion);
                    model.addAttribute("proyectos", proyectoService.findByAdministradorId(idAdministrador));
                    model.addAttribute("actividades", actividadService.findByAdministradorId(idAdministrador));
                    return "asignacion"; // Recargar la vista sin redirigir
                }
            }

            // Guarda nueva asignación o actualiza existente
            if (asignacion.getId_asignacionproyecto() != null) {
                Asignacion_proyectoActi asignacionExistente = asignar_p.findOne(asignacion.getId_asignacionproyecto());
                if (asignacionExistente == null) {
                    model.addAttribute("error", "La Asignación con ese ID no existe");
                    return "asignacion"; // Recargar la vista sin redirigir
                }

                asignacionExistente.setId_proyecto(asignacion.getId_proyecto());
                asignacionExistente.setId_tipoActividades(asignacion.getId_tipoActividades());
                asignacionExistente.setEstado(asignacion.getEstado());
                asignacionExistente.setMeta_deseada(asignacion.getMeta_deseada());
                asignar_p.save(asignacionExistente);
                model.addAttribute("success", "Asignación actualizada exitosamente");
            } else {
                asignar_p.save(asignacion);
                model.addAttribute("success", "Asignación guardada exitosamente");
            }

            // Recalcular el porcentaje después de guardar
            recalcularPorcentaje(asignacion.getId_proyecto());

            return "redirect:/listarAsignaciones"; // Redirección al listado de asignaciones
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la Asignación: " + e.getMessage());
            model.addAttribute("titulo", "Formulario de Nueva Asignación");
            model.addAttribute("asignacion", asignacion);
            model.addAttribute("proyectos", proyectoService.findByAdministradorId(idAdministrador));
            model.addAttribute("actividades", actividadService.findByAdministradorId(idAdministrador));
            return "asignacion"; // Recargar la vista sin redirigir
        }
    }

    private void recalcularPorcentaje(Long idProyecto) {
        // Contar las actividades asignadas al proyecto
        long totalActividades = asignar_p.countByProyectoId(idProyecto);

        // Calcular el porcentaje por actividad
        double porcentajePorActividad = totalActividades > 0 ? 100.0 / totalActividades : 0;

        // Obtener todas las asignaciones del proyecto y actualizar su porcentaje
        List<Asignacion_proyectoActi> asignacionesProyecto = asignar_p.findByProyectoId(idProyecto);
        for (Asignacion_proyectoActi a : asignacionesProyecto) {
            a.setPorcentajeActividad(porcentajePorActividad);
            asignar_p.save(a);
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
            Long idProyecto = asignar_p.findOne(id).getId_proyecto(); // Obtener el ID del proyecto
            asignar_p.delete(id); 
            recalcularPorcentaje(idProyecto); // Recalcular el porcentaje después de eliminar
            flash.addFlashAttribute("success", "Asignación eliminada correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la Asignación");
        }
        return "redirect:/listarAsignaciones";
    }
}
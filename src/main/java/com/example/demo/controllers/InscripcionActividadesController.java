package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inscripcion_actividades;
import com.example.demo.service.IInscripcion_actividadesService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.ITipo_ActividadesService;
import com.example.demo.service.IVoluntariosService;


@Controller
public class InscripcionActividadesController {

    @Autowired
    private ITipo_ActividadesService actividadService;

    @Autowired
    private IInscripcion_actividadesService inscripcionActividadesService;

    @Autowired
    private IVoluntariosService voluntarioService;
    
  

    // Listar Inscripciones de Actividades
    @RequestMapping(value = "/listarinscripciones", method = RequestMethod.GET)
    public String listarInscripciones(Model model) {
        model.addAttribute("titulo", "Listado de Inscripciones a Actividades");
        model.addAttribute("inscripciones", inscripcionActividadesService.findAll());
        return "listarinscripciones";
    }

    // Crear una nueva Inscripción
    @RequestMapping(value = "/inscripcion", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Inscripcion_actividades inscripcion = new Inscripcion_actividades();
        model.put("inscripcion", inscripcion);
        model.put("titulo", "Formulario de Nueva Inscripción");
        model.put("actividades", actividadService.listaractividades()); // Lista de actividades
        model.put("voluntarios", voluntarioService.listarvoluntarios()); // Lista de voluntarios
        return "inscripcion";
    }

    // Editar Inscripción
    @RequestMapping(value = "/inscripcion/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Inscripcion_actividades inscripcion = null;
        if (id > 0) {
            inscripcion = inscripcionActividadesService.findOne(id);
            if (inscripcion == null) {
                flash.addFlashAttribute("error", "La Inscripción no existe en la base de datos");
                return "redirect:/listarinscripciones";
            }
        } else {
            flash.addFlashAttribute("error", "El Id de la Inscripción no puede ser 0");
            return "redirect:/listarinscripciones";
        }
        model.put("inscripcion", inscripcion);
        model.put("titulo", "Editar Inscripción");
        model.put("actividades", actividadService.listaractividades());
        model.put("voluntarios", voluntarioService.listarvoluntarios());
        return "inscripcion";
    }

    // Guardar Inscripción (Crear o Actualizar)
    @RequestMapping(value = "/inscripcion", method = RequestMethod.POST)
    public String guardarInscripcion(Inscripcion_actividades inscripcion, RedirectAttributes flash) {
        try {
            if (inscripcion.getId_inscripcionactividades() != null) {
                // Si el ID existe, es una actualización
                Inscripcion_actividades inscripcionExistente = inscripcionActividadesService.findOne(inscripcion.getId_inscripcionactividades());
                if (inscripcionExistente == null) {
                    flash.addFlashAttribute("error", "La Inscripción con ese ID no existe");
                    return "redirect:/listarinscripciones";
                }
                // Actualizar los campos necesarios (si es necesario)
                inscripcionExistente.setId_voluntario(inscripcion.getId_voluntario());
                inscripcionExistente.setId_tipoActividades(inscripcion.getId_tipoActividades());
                inscripcionExistente.setCreateAs(inscripcion.getCreateAs());

                inscripcionActividadesService.save(inscripcionExistente);
                flash.addFlashAttribute("success", "Inscripción actualizada exitosamente");
            } else {
                // Si el ID no existe, es una creación
                inscripcionActividadesService.save(inscripcion);
                flash.addFlashAttribute("success", "Inscripción guardada exitosamente");
            }
            return "redirect:/listarinscripciones";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la Inscripción: " + e.getMessage());
            return "redirect:/listarinscripciones";
        }
    }

    // Eliminar Inscripción
    @RequestMapping(value = "/inscripcion/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            inscripcionActividadesService.delete(id);
            flash.addFlashAttribute("success", "Inscripción eliminada correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la Inscripción");
        }
        return "redirect:/listarinscripciones";
    }
}

package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.service.ITipo_ActividadesService;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("idAdministrador")
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
    public String listartipo(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        model.addAttribute("titulo", "Listado de Actividades");
        List<Tipo_Actividades> actividades = tipoActividadesService.findByAdministradorId(idAdministrador);
        model.addAttribute("tactividad", actividades);
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
    public String guardar(
            @Valid @ModelAttribute Tipo_Actividades tactividad,
            BindingResult result,
            @SessionAttribute("idAdministrador") Long idAdministrador,
            RedirectAttributes flash,
            Model model) {

        tactividad.setId_administrador(idAdministrador);
        
        // Validar que el nombre de la actividad no esté vacío
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Tipo Actividad");
            model.addAttribute("tactividad", tactividad);
            return "formtipo"; // Volver a cargar la vista con errores
        }
        
        // Validar que no exista otra actividad con el mismo nombre para el mismo administrador
        List<Tipo_Actividades> actividadesExistentes = tipoActividadesService.findByAdministradorId(idAdministrador);
        for (Tipo_Actividades actividadExistente : actividadesExistentes) {
            if (!actividadExistente.getId_tipoActividades().equals(tactividad.getId_tipoActividades()) 
                && actividadExistente.getNombre_act().equalsIgnoreCase(tactividad.getNombre_act())) {
                model.addAttribute("error", "Ya existe una actividad con ese nombre para este administrador.");
                model.addAttribute("titulo", "Formulario de Tipo Actividad");
                model.addAttribute("tactividad", tactividad);
                return "formtipo"; // Volver a la vista con el mensaje de error
            }
        }

        // Guardar el tipo de actividad
        tipoActividadesService.save(tactividad);
        
        String mensajeFls = (tactividad.getId_tipoActividades() != null) 
            ? "El registro del tipo de actividades se ha editado con éxito" 
            : "El registro de tipo de actividad se ha creado con éxito";

        flash.addFlashAttribute("success", mensajeFls);
        
        return "redirect:/asignacion";
    }

    
    @RequestMapping(value = "/eliminartipo/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            // Verificar si hay asignaciones o registros asociados
            long asignacionesCount = tipoActividadesService.countAsignacionesByTipoActividadId(id);
            long registrosCount = tipoActividadesService.countRegistrosByTipoActividadId(id);

            if (asignacionesCount > 0 || registrosCount > 0) {
                flash.addFlashAttribute("error", "No se puede eliminar la actividad porque esta relaciondo con otras entidades.");
                return "redirect:/listartipo"; // Redirige si no se puede eliminar
            }

            if (id > 0) {
                tipoActividadesService.delete(id);
                flash.addFlashAttribute("success", "La actividad ha sido eliminada correctamente.");
            }
            return "redirect:/listartipo";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la actividad: " + e.getMessage());
            return "redirect:/listartipo";
        }
    }

}



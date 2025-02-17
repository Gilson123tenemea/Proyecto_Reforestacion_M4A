
package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Area;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.IProyectoServices; // Suponiendo que tienes una entidad Proyecto

@Controller
public class AreaController {

    @Autowired
    private IAreaServices areaService;

    @Autowired
    private IProyectoServices proyectoService; 

    @GetMapping("/area")
    public String crearArea(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        model.addAttribute("area", new Area());
        List<Proyecto> proyectos = proyectoService.findByAdministradorId(idAdministrador); 
        model.addAttribute("proyectos", proyectos); 
        return "area"; 
    }


    @PostMapping("/guardararea")
    public String guardarArea(@ModelAttribute Area area, Model model) {
        try {
    
            if (area.getId_proyecto() == null) {
                throw new Exception("Debe seleccionar un Proyecto.");
            }


            areaService.save(area);
            model.addAttribute("mensaje", "Área guardada exitosamente");
            return "redirect:/parcelas";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Área: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping("/listarareas")
    public String listarAreas(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        model.addAttribute("titulo", "Lista de Áreas");
        List<Area> areas = areaService.findByProyectoIdAdministrador(idAdministrador);
        model.addAttribute("areas", areas);
        return "listarareas";
    }

  
    @GetMapping("/area/eliminar/{id}")
    public String eliminarArea(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            long count = areaService.countParcelasByAreaId(id);
            if (count > 0) {
                attributes.addFlashAttribute("error", "No se puede eliminar el área porque tiene parcelas asociadas.");
                return "redirect:/listarareas"; // Redirige si no se puede eliminar
            }
            areaService.delete(id);
            attributes.addFlashAttribute("mensaje", "Área eliminada correctamente");
        } catch (IllegalStateException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar el Área");
        }
        return "redirect:/listarareas";
    }

 
    @GetMapping("/area/editar/{id}")
    public String editarArea(@PathVariable("id") Long id, Model model, RedirectAttributes attributes, @SessionAttribute("idAdministrador") Long idAdministrador) {
        try {
            Area area = areaService.findOne(id);
            if (area == null) {
                attributes.addFlashAttribute("error", "El Área no existe");
                return "redirect:/listarareas";
            }
            model.addAttribute("area", area);
            List<Proyecto> proyectos = proyectoService.findByAdministradorId(idAdministrador);
            model.addAttribute("proyectos", proyectos); 
            model.addAttribute("titulo", "Editar Área");
            return "area"; 
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar el Área: " + e.getMessage());
            return "redirect:/listarareas";
        }
    }
}

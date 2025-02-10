package com.example.demo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.text.SimpleDateFormat; 
import java.util.Date; 

import com.example.demo.entity.Area;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Intervencion_Suelo;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.IEquiposService;
import com.example.demo.service.IIntervencionSueloServices;
import com.example.demo.service.IProyectoServices;

@Controller
public class IntervencionSueloController {

    @Autowired
    private IIntervencionSueloServices intervencionservice;

    @Autowired
    private IAreaServices areaService;

    @Autowired
    private IProyectoServices proyectoService;

    @Autowired
    private IEquiposService equiposService;

    @RequestMapping(value = "/Listaintervencion", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de intervenciones");
        model.addAttribute("Intervenciones", intervencionservice.findAll());
        return "Listaintervencion";
    }

    @RequestMapping("/formularioIntervenciones")
    public String crear(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        Intervencion_Suelo intervencion = new Intervencion_Suelo();
        model.addAttribute("intervencion", intervencion);
        model.addAttribute("titulo", "Formulario de Intervenciones");
        List<Area> areas = areaService.findByProyectoIdAdministrador(idAdministrador);
        List<Proyecto> proyectos = proyectoService.findAll();
        List<Equipos> equipos = equiposService.findAll();

        model.addAttribute("areas", areas);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("equipos", equipos);

        return "formularioIntervenciones"; 
    }

    @RequestMapping(value = "/formularioIntervenciones", method = RequestMethod.POST)
    public String guardar(@ModelAttribute Intervencion_Suelo inter, Model model) {
        try {
            // Establece la fecha y hora actuales
            Date now = new Date();
            inter.setFecha_asignacion(now);
            
            // Formato de la hora actual
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            inter.setHora_asignacion(timeFormat.format(now));

            // Imprimir para depuración
            System.out.println("Intervención a guardar: " + inter);
            // Guarda la intervención, el método save se encargará de crear o actualizar
            intervencionservice.save(inter);
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la intervención: " + e.getMessage());
            return "formularioIntervenciones"; 
        }
        return "redirect:/Listaintervencion"; 
    }
    
    @RequestMapping(value = "/formularioIntervenciones/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        Intervencion_Suelo intervencion = intervencionservice.findOne(id);
        if (intervencion == null) {
            return "redirect:/Listaintervencion";
        }

        List<Area> areas = areaService.findByProyectoIdAdministrador(idAdministrador);
        List<Proyecto> proyectos = proyectoService.findAll();
        List<Equipos> equipos = equiposService.findAll();

        model.addAttribute("intervencion", intervencion);
        model.addAttribute("titulo", "Editar Intervenciones");
        model.addAttribute("areas", areas);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("equipos", equipos);

        return "formularioIntervenciones";
    }

    @RequestMapping(value = "/eliminarintervenciones/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            intervencionservice.delete(id);
        }
        return "redirect:/Listaintervencion"; // Cambié esto a la ruta correcta
    }

    @GetMapping("/proyectosPorArea/{idArea}")
    @ResponseBody
    public List<Proyecto> getProyectosPorArea(@PathVariable Long idArea) {
        return proyectoService.findByAreaId(idArea);
    }

    @GetMapping("/equiposPorProyecto/{idProyecto}")
    @ResponseBody
    public List<Equipos> getEquiposPorProyecto(@PathVariable Long idProyecto) {
        return equiposService.findEquiposPorProyectoYActividad(idProyecto);
    }
}

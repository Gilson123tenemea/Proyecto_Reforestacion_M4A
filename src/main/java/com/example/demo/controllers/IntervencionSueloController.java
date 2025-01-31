package com.example.demo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Area;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Intervencion_Suelo;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IAreaServices;
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

    @RequestMapping(value = "/listarIntervenciones", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de intervenciones");
        model.addAttribute("Intervenciones", intervencionservice.findAll());
        return "Listaintervencion";
    }

    @RequestMapping("/formularioIntervenciones")
    public String crear(Model model) {
        Intervencion_Suelo intervencion = new Intervencion_Suelo();
        model.addAttribute("intervencion", intervencion);
        model.addAttribute("titulo", "Formulario de Intervenciones");

        // Cargar áreas y proyectos
        List<Area> areas = areaService.findAll();
        List<Proyecto> proyectos = proyectoService.findAll();

        model.addAttribute("areas", areas);
        model.addAttribute("proyectos", proyectos);

        return "formularioIntervenciones"; 
    }    
    
    @RequestMapping(value = "/formularioIntervenciones", method = RequestMethod.POST)
    public String guardar(Intervencion_Suelo inter, Model model) {
        intervencionservice.save(inter);
        return "redirect:/listarIntervenciones"; 
    }

    @RequestMapping(value = "/formularioIntervenciones/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {
        Intervencion_Suelo intervencion = null;
        if (id > 0) {
            intervencion = intervencionservice.findOne(id);
        } else {
            return "redirect:/listarIntervenciones";
        }
        model.addAttribute("intervencion", intervencion);
        model.addAttribute("titulo", "Editar Intervenciones");
        return "formularioIntervenciones";
    }

    @RequestMapping(value = "/eliminarintervenciones/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            intervencionservice.delete(id);
        }
        return "redirect:/listarIntervenciones";
    }
    
    @GetMapping("/proyectosPorArea/{idArea}")
    @ResponseBody
    public List<Proyecto> getProyectosPorArea(@PathVariable Long idArea) {
        return proyectoService.findByAreaId(idArea);
    }
    
  
    
}
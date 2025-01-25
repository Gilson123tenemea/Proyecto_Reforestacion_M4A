package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Intervencion_Suelo;
import com.example.demo.service.IIntervencionSueloServices;

@Controller
public class IntervencionSueloController {


	
    @Autowired
    private IIntervencionSueloServices intervencionservice;

    @RequestMapping(value="/listarIntervenciones", method=RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de intervenciones");
        model.addAttribute("Intervenciones", intervencionservice.findAll());
        return "Listaintervencion";
    }

    @RequestMapping("/formularioIntervenciones")
    public String crear(Map<String, Object> model) {
        Intervencion_Suelo intervencion = new Intervencion_Suelo();
        model.put("intervencion", intervencion);
        model.put("titulo", "Patrocinio");
        return "formularioIntervenciones"; 
    }

    @RequestMapping(value="/formularioIntervenciones", method=RequestMethod.POST)
    public String guardar(Intervencion_Suelo inter, Model model) {
        intervencionservice.save(inter);
        return "redirect:/listarIntervenciones"; 
    }

    @RequestMapping(value="/formularioIntervenciones/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
        Intervencion_Suelo intervencion = null;
        if (id > 0) {
        	intervencion = intervencionservice.findOne(id);
        } else {
            return "redirect:/Listaintervencion";
        }
        model.put("intervencion", intervencion);
        model.put("titulo", "Editar Intervenciones");
        return "formularioIntervenciones";
    }

    @RequestMapping(value="/eliminarintervenciones/{id}")
    public String eliminar(@PathVariable(value="id") Long id) {
        if (id > 0) {
            intervencionservice.delete(id);
        }
        return "redirect:/listarIntervenciones";
    }
	
}

package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Patrocinio;
import com.example.demo.service.IPatrocinioService;

@Controller
public class PatrocinioController {

	
    @Autowired
    private IPatrocinioService patrocinioservice;

    @RequestMapping(value="/listarPatrocinios", method=RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("patrocinios", patrocinioservice.findAll());
        return "ListaPatrocinio";
        
        
    }

    @RequestMapping("/formularioPatrocinio")
    public String crear(Map<String, Object> model) {
        Patrocinio patrocinio = new Patrocinio();
        model.put("patrocinio", patrocinio);
        model.put("titulo", "Patrocinio");
        return "formularioPatrocinio"; 
    }

    @RequestMapping(value="/formularioPatrocinio", method=RequestMethod.POST)
    public String guardar(@ModelAttribute Patrocinio cliente,Model model) {
        
        model.addAttribute("titulo", "Patrocinio");
            
        patrocinioservice.save(cliente);
        return "redirect:/listarPatrocinios"; 
    }

    @RequestMapping(value="/formularioPatrocinio/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
        Patrocinio patrocinio = null;
        if (id > 0) {
        	patrocinio = patrocinioservice.findOne(id);
        } else {
            return "redirect:/listarPatrocinios";
        }
        model.put("patrocinio", patrocinio);
        model.put("titulo", "Editar patrocinio");
        return "formularioPatrocinio";
    }

    @RequestMapping(value="/eliminarPatrocinio/{id}")
    public String eliminar(@PathVariable(value="id") Long id) {
        if (id > 0) {
            patrocinioservice.delete(id);
        }
        return "redirect:/listarPatrocinios";
    }
	
	
	
}

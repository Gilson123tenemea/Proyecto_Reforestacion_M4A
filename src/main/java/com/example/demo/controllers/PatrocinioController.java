package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IPatrocinioService;
import com.example.demo.service.IProyectoServices;

@Controller
@SessionAttributes("idPatrocinador")
public class PatrocinioController {

	@Autowired
	private IProyectoServices proyectoService;
    @Autowired
    private IPatrocinioService patrocinioservice;

    @RequestMapping(value="/listarPatrocinios", method=RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Patrocinio");
        model.addAttribute("patrocinios", patrocinioservice.findAll());
        return "ListaPatrocinio";
        
        
    }

    @RequestMapping("/patrocinarproyecto")
    public String crear(Map<String, Object> model) {
        Patrocinio patrocinio = new Patrocinio();
        model.put("patrocinio", patrocinio);
        model.put("titulo", "Patrocinio");
        return "patrocinarproyecto"; 
    }

    @RequestMapping(value="/formularioPatrocinio", method=RequestMethod.POST)
    public String guardar(@ModelAttribute Patrocinio patrocinio, Model model) {
        Long idPatrocinador = (Long) model.asMap().get("idPatrocinador");

        if (idPatrocinador != null) {
            patrocinio.setId_patrocinador(idPatrocinador); 
        } else {
            System.out.println("ID del patrocinador es null"); 
        }
        System.out.println("Beneficios: " + patrocinio.getBeneficios());
        System.out.println("Detalle de Donación: " + patrocinio.getDetalledpnado());
        patrocinioservice.save(patrocinio);
        return "redirect:/verproyectospatrocinador"; 
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
    
    @RequestMapping("/patrocinarproyecto/{id}")
    public String mostrarDetallesProyecto(@PathVariable("id") Long id, Model model) {
        Proyecto proyecto = proyectoService.findOne(id);
        if (proyecto != null) {
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("titulo", "Patrocinio del Proyecto: " + proyecto.getNombre());
        } else {
            model.addAttribute("error", "Proyecto no encontrado");
            return "error"; 
        }
        return "patrocinarproyecto"; 
    }
    
	
}
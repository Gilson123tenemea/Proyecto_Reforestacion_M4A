package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IPatrocinioService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.IUsuarioServices;

@Controller
@SessionAttributes("idPatrocinador")
public class PatrocinioController {

	@Autowired
	private IProyectoServices proyectoService;
    @Autowired
    private IPatrocinioService patrocinioservice;
    
    @Autowired
    private IUsuarioServices usuarioservice;
    
    @GetMapping("/verproyectospatrocinador")
    public String listarProyectosYPatrocinios(Model model) {
        // Recuperar el idPatrocinador de la sesión
        Long idPatrocinador = (Long) model.asMap().get("idPatrocinador");

        // Obtener todos los proyectos y patrocinios
        List<Proyecto> proyectos = proyectoService.findAll();
        List<Patrocinio> allPatrocinios = patrocinioservice.findAll();

        // Filtrar los patrocinios según el idPatrocinador
        List<Patrocinio> filteredPatrocinios = allPatrocinios.stream()
            .filter(patrocinio -> patrocinio.getId_patrocinador().equals(idPatrocinador))
            .collect(Collectors.toList());

        model.addAttribute("titulo", "Proyectos y Patrocinios");
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("patrocinios", filteredPatrocinios); // Pasar solo los patrocinios filtrados
        return "verproyectospatrocinador"; 
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
    
    @GetMapping("/proyectospatrocinados/{id}")
    public String detallePatrocinio(@PathVariable("id") Long id, Model model) {
        Patrocinio patrocinio = patrocinioservice.findOne(id);
        if (patrocinio != null) {
            model.addAttribute("patrocinio", patrocinio);
            Proyecto proyecto = proyectoService.findOne(patrocinio.getId_proyecto());
            model.addAttribute("proyecto", proyecto);

            if (patrocinio.getId_patrocinador() != null) {
                Usuarios patrocinador = usuarioservice.findOne(patrocinio.getId_patrocinador());
                model.addAttribute("patrocinador", patrocinador);
                System.out.println("Patrocinador: " + patrocinador); // Log para verificar
            }

            model.addAttribute("titulo", "Detalles del Patrocinio");
        } else {
            model.addAttribute("error", "Patrocinio no encontrado");
            return "error"; 
        }
        return "proyectospatrocinados"; 
    }
}
package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IProyectoServices;

@Controller
public class ProyectosControllers {

    @Autowired
    private IProyectoServices proyectoService;
    
    @Autowired
    private IParroquiaService parroquiaService;
    
    @Autowired
    private ICantonService cantonService; 
    
    @Autowired
    private IProvinciaService provinciaService; 
    
    
    @GetMapping("/proyectos")
    public String crear(Model model) {
        model.addAttribute("proyecto", new Proyecto()); // Formulario vacío para un nuevo proyecto
        model.addAttribute("parroquia", parroquiaService.findAll());
        model.addAttribute("provincias", provinciaService.findAll()); // Cargar provincias en el modelo
        return "proyectos"; // Vista para el formulario del proyecto
    }

    // Configuración de formato de fecha
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Listar proyectos
    @GetMapping("/listarProyectos")
    public String listarProyectos(Model model) {
        model.addAttribute("titulo", "Lista de Proyectos");
        model.addAttribute("proyectos", proyectoService.findAll());
        return "listarProyectos"; // Vista para mostrar la lista de proyectos
    }

    @PostMapping("/guardarProyecto")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto, Model model) {
        try {
            if (proyecto.getId_parroquia() == null) {
                throw new Exception("Debe seleccionar una Parroquia.");
            }
            proyectoService.save(proyecto);
            model.addAttribute("mensaje", "Proyecto guardado exitosamente");
            return "redirect:/listarProyectos";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el proyecto: " + e.getMessage());
            return "error";
        }
    }
    
 // Método para eliminar proyecto
    @PostMapping("/proyectos/eliminar/{id}")
    public String eliminarProyecto(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            proyectoService.delete(id);
            attributes.addFlashAttribute("mensaje", "Proyecto eliminado correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar el proyecto");
        }
        return "redirect:/listarProyectos";
    }
    
    @GetMapping("/proyectos/editar/{id}")
    public String editarProyecto(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Proyecto proyecto = proyectoService.findOne(id);
            if (proyecto == null) {
                attributes.addFlashAttribute("error", "El proyecto no existe");
                return "redirect:/listarProyectos";
            }
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("parroquia", parroquiaService.findAll());
            model.addAttribute("titulo", "Editar Proyecto");
            return "proyectos"; 
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar la proyecto: " + e.getMessage());
            return "redirect:/listarProyectos";
        }
    }
    
    
    
    @GetMapping("/cantones/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvincia(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }

    @GetMapping("/parroquias/{idCanton}")
    @ResponseBody
    public List<Parroquia> getParroquiasByCanton(@PathVariable Long idCanton) {
        return parroquiaService.findByCanton(idCanton);
    }
}

package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;

import jakarta.validation.Valid;


@Controller
public class ParroquiaControllers {

    @Autowired
    IParroquiaService parroquiaService;
    @Autowired
    ICantonService cantonService;
    
    @Autowired
    private IProvinciaService provinciaService; 

    @GetMapping("/parroquia")
    public String crearParroquiaForm(Model model) {
        model.addAttribute("parroquia", new Parroquia());
        model.addAttribute("provincias", provinciaService.findAll());
        return "parroquia";
    }

    @PostMapping("/guardarParroquia")
    public String guardarParroquia(@Valid @ModelAttribute("parroquia") Parroquia parroquia, 
                                    BindingResult bindingResult, 
                                    Model model) {
        try {
            // Si hay errores de validación, regresamos al formulario con los errores
            if (bindingResult.hasErrors()) {
                model.addAttribute("cantones", cantonService.findAll());
                model.addAttribute("provincias", provinciaService.findAll());
                return "parroquia";  // Volver al formulario con los errores de validación
            }

            // Verificar si el nombre de la parroquia ya existe (ignorando mayúsculas y minúsculas)
            String nombreParroquia = parroquia.getNombreParroquia().trim().toLowerCase();
            List<Parroquia> parroquiasExistentes = parroquiaService.findAll();

            for (Parroquia parroquiaExistente : parroquiasExistentes) {
                if (parroquiaExistente.getNombreParroquia().trim().toLowerCase().equals(nombreParroquia) 
                    && !parroquiaExistente.getId_parroquia().equals(parroquia.getId_parroquia())) {
                    model.addAttribute("error", "Ya existe una parroquia con el mismo nombre.");
                    model.addAttribute("cantones", cantonService.findAll());
                    model.addAttribute("provincias", provinciaService.findAll());
                    model.addAttribute("parroquia", parroquia);
                    return "parroquia";  // Volver al formulario con el mensaje de error
                }
            }

            // Verificar si el objeto parroquia tiene un cantón asociado
            if (parroquia.getId_canton() == null) {
                model.addAttribute("error", "Debe seleccionar un Cantón.");
                model.addAttribute("cantones", cantonService.findAll());
                model.addAttribute("provincias", provinciaService.findAll());
                model.addAttribute("parroquia", parroquia);
                return "parroquia";  // Volver al formulario con el mensaje de error
            }

            // Guardar o actualizar la parroquia
            if (parroquia.getId_parroquia() != null) {
                Parroquia parroquiaExistente = parroquiaService.findOne(parroquia.getId_parroquia());
                if (parroquiaExistente == null) {
                    model.addAttribute("error", "La parroquia con ese ID no existe.");
                    return "parroquia";
                }
                
                // Mantener las relaciones existentes con usuarios y proyectos
                List<Usuarios> usuariosExistentes = parroquiaExistente.getUsuarios(); // Obtener usuarios existentes
                List<Proyecto> proyectosExistentes = parroquiaExistente.getProyecto(); // Obtener proyectos existentes
                
                // Actualizar la parroquia
                parroquiaExistente.setNombreParroquia(parroquia.getNombreParroquia());
                parroquiaExistente.setId_canton(parroquia.getId_canton());
                parroquiaExistente.setUsuarios(usuariosExistentes); // Mantener usuarios existentes
                parroquiaExistente.setProyecto(proyectosExistentes); // Mantener proyectos existentes
                
                parroquiaService.save(parroquiaExistente);  // Actualizar la parroquia
                model.addAttribute("success", "Parroquia actualizada exitosamente");
            } else {
                parroquiaService.save(parroquia);  // Crear nueva parroquia si no tiene un ID
                model.addAttribute("success", "Parroquia guardada exitosamente");
            }

            return "redirect:/listarparroquias";  // Redirigir a la lista de parroquias
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la parroquia: " + e.getMessage());
            model.addAttribute("cantones", cantonService.findAll());
            model.addAttribute("provincias", provinciaService.findAll());
            model.addAttribute("parroquia", parroquia);
            return "parroquia";  // Volver al formulario con mensaje de error
        }
    }


    @GetMapping("/listarparroquias")
    public String listarParroquias(Model model) {
        List<Parroquia> parroquias = parroquiaService.findAll();
        model.addAttribute("parroquias", parroquias);
        return "listarparroquia";  // Nombre de la vista donde se mostrará la lista
    }

    @GetMapping("/parroquia/eliminar/{id}")
    public String eliminarParroquia(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            // Verificar si hay usuarios o proyectos asociados
            long usuariosCount = parroquiaService.countUsuariosByParroquiaId(id);
            long proyectosCount = parroquiaService.countProyectosByParroquiaId(id);
            
            if (usuariosCount > 0 || proyectosCount > 0) {
                attributes.addFlashAttribute("error", "No se puede eliminar la parroquia porque tiene usuarios o proyectos asociados.");
                return "redirect:/listarparroquias"; // Redirige si no se puede eliminar
            }

            parroquiaService.delete(id);
            attributes.addFlashAttribute("mensaje", "Parroquia eliminada correctamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al eliminar la parroquia: " + e.getMessage());
        }
        return "redirect:/listarparroquias";
    }

    @GetMapping("/parroquia/editar/{id}")
    public String editarParroquia(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Parroquia parroquia = parroquiaService.findOne(id);
            if (parroquia == null) {
                attributes.addFlashAttribute("error", "La parroquia no existe");
                return "redirect:/listarparroquias";
            }
            
            model.addAttribute("parroquia", parroquia);
            
            // Cargar las provincias
            model.addAttribute("provincias", provinciaService.findAll());
            
            // Cargar el cantón de la parroquia
            Canton canton = cantonService.findOne(parroquia.getId_canton());
            if (canton != null) {
                model.addAttribute("cantonSeleccionado", canton);
                model.addAttribute("cantones", cantonService.findByProvincia(canton.getId_provincia()));
                model.addAttribute("provinciaSeleccionada", canton.getId_provincia());
            } else {
                model.addAttribute("cantones", List.of());
            }
            
            model.addAttribute("titulo", "Editar Parroquia");
            return "parroquia"; // Redirige al formulario de edición
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar la parroquia: " + e.getMessage());
            return "redirect:/listarparroquias";
        }
    }
    

    @GetMapping("/cantones/proyectos/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvinciaProyectos(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }
}


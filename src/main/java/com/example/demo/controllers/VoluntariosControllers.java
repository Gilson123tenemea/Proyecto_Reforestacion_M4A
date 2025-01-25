package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.example.demo.entity.Administrador;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.IUsuarioServices;
import com.example.demo.service.IVoluntariosService;

@Controller
public class VoluntariosControllers {

    
    @Autowired
    private IVoluntariosService voluntariosServices;
    
    @Autowired
    private IUsuarioServices usuarioServices;

    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    
 // Guardar voluntario y usuario
    @PostMapping("/guardarvolun")
    public String guardarVoluntarioYUsuario(@ModelAttribute Voluntarios voluntario, @ModelAttribute Usuarios usuario, Model model) {
        try {
            usuarioServices.save(usuario);
            voluntario.setId_usuarios(usuario.getId_usuarios());
            voluntariosServices.save(voluntario);

            model.addAttribute("mensaje", "Voluntario y Usuario guardados exitosamente");
            return "redirect:/listarVoluntarios"; 
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Voluntario y Usuario: " + e.getMessage());
            return "error";
        }
    }

    // Crear nuevo voluntario
    @RequestMapping("/voluntarios")
    public String crear(Map<String, Object> model) {
        model.put("voluntario", new Voluntarios()); 
        return "voluntarios"; 
    }
    
    @GetMapping("/listarVoluntarios")
    public String listarVoluntarios(Model model) {
        model.addAttribute("titulo", "Lista de Voluntarios");

        List<Voluntarios> voluntarios = voluntariosServices.findAll();
        List<Usuarios> usuarios = usuarioServices.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            if (!usuario.getVoluntarios().isEmpty()) {
                Voluntarios voluntario = usuario.getVoluntarios().get(0); 
                Map<String, Object> datosCombinados = new HashMap<>();
                datosCombinados.put("voluntarios", voluntario);
                datosCombinados.put("usuario", usuario);
                combinados.add(datosCombinados);
            }
        }

        model.addAttribute("combinados", combinados);
        return "listarVoluntarios";
    }
    
    @PostMapping("/deletevoluntario/{id}")
    public String deleteVoluntarios(@PathVariable Long id, Model model) {
        try {
            Voluntarios voluntarios = voluntariosServices.findOne(id);

            if (voluntarios == null) {
                model.addAttribute("mensaje", "Voluntario no encontrado con ID: " + id);
                return "error";
            }

            voluntariosServices.delete(id);
            model.addAttribute("mensaje", "Voluntario eliminado exitosamente");
            return "redirect:/listarVoluntarios";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el voluntario: " + e.getMessage());
            return "error";
        }
    }

}
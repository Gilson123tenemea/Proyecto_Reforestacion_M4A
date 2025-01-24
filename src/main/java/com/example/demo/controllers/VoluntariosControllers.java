package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    // Listar voluntarios
    @GetMapping("/listarVoluntarios")
    public String listarVoluntarios(Model model) {
        model.addAttribute("titulo", "Lista de Voluntarios");
        model.addAttribute("voluntarios", voluntariosServices.findAll());
        return "listarVoluntarios";
    }


}
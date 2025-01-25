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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    
    @RequestMapping(value = "/voluntarios", method = RequestMethod.GET)
    public String listarYEditar(
            @RequestParam(value = "id", required = false) Long id,
            Map<String, Object> model,
            RedirectAttributes flash) {
        Voluntarios voluntario = new Voluntarios();
        Usuarios usuario = new Usuarios();

        if (id != null && id > 0) {
            voluntario = voluntariosServices.findOne(id);
            if (voluntario != null) {
                usuario = usuarioServices.findOne(voluntario.getId_usuarios());
            } else {
                flash.addFlashAttribute("info", "El voluntario no existe en la base de datos");
                return "redirect:/voluntarios";
            }
        }

        model.put("voluntarios", voluntario); 
        model.put("usuario", usuario);
        model.put("titulo", "Editar o Crear Voluntario");
        return "voluntarios";
    }

    @PostMapping("/guardarvolun")
    public String guardarVoluntarioYUsuario(
            @ModelAttribute("voluntarios") Voluntarios voluntarios,
            @ModelAttribute("usuario") Usuarios usuario,
            Model model) {
        try {
            if (usuario.getId_usuarios() != null) {
                Usuarios usuarioExistente = usuarioServices.findOne(usuario.getId_usuarios());
                if (usuarioExistente != null) {
                    usuarioExistente.setCedula(usuario.getCedula());
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setApellido(usuario.getApellido());
                    usuarioExistente.setCorreo(usuario.getCorreo());
                    
                    if (usuario.getFecha_nacimiento() != null) {
                        usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                    }
                    
                    usuarioExistente.setGenero(usuario.getGenero());
                    usuarioExistente.setCelular(usuario.getCelular());
                    usuarioExistente.setContraseña(usuario.getContraseña());
                    usuario = usuarioExistente;
                }
            }

            usuarioServices.save(usuario);
            voluntarios.setId_usuarios(usuario.getId_usuarios());
            voluntariosServices.save(voluntarios);

            return "redirect:/listarVoluntarios";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crearVoluntario")
    public String crearVoluntario(Map<String, Object> model) {
    	model.put("voluntarios", new Voluntarios());
        model.put("usuarios", usuarioServices.findAll());
        return "voluntarios"; 
    }
    
    @GetMapping("/listarVoluntarios")
    public String listarVoluntarios(Model model) {
        model.addAttribute("titulo", "Lista de Voluntarios");

        List<Voluntarios> voluntarios = voluntariosServices.findAll();
        List<Usuarios> usuarios = usuarioServices.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            if (usuario.getVoluntarios() != null && !usuario.getVoluntarios().isEmpty()) {
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
            System.out.println("Eliminando voluntario con ID: " + id);  // Debugging log
            Voluntarios voluntarios = voluntariosServices.findOne(id);

            if (voluntarios == null) {
                model.addAttribute("mensaje", "Voluntario no encontrado con ID: " + id);
                return "error";
            }

            voluntariosServices.eliminarVoluntarioYUsuario(id);
            model.addAttribute("mensaje", "Voluntario y Usuario eliminados exitosamente");
            return "redirect:/listarVoluntarios";  // Asegúrate de que esta URL sea correcta
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el voluntario: " + e.getMessage());
            return "error";
        }
    }




}
package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IUsuarioServices;

@Controller

public class PatrocinadorController {
	
	
	@Autowired
	private IPatrocinadorServices patrocinadorservice;
	@Autowired
	private IUsuarioServices usuarioservice;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

	@GetMapping("/listar")
	 public String listarVoluntarios(Model model) {
        model.addAttribute("titulo", "Lista de Patrocinadores");

        List<Patrocinador> patrocinadores = patrocinadorservice.findAll();
        List<Usuarios> usuarios = usuarioservice.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            if (!usuario.getPatrocinador().isEmpty()) {
            	Patrocinador patrocinador = usuario.getPatrocinador().get(0); 
                Map<String, Object> datosCombinados = new HashMap<>();
                datosCombinados.put("patrocinadores", patrocinador);
                datosCombinados.put("usuario", usuario);
                combinados.add(datosCombinados);
            }
        }

        model.addAttribute("combinados", combinados);
        return "Lista";
    }

    // Método para mostrar el formulario
    @GetMapping("/formulario")
    public String crear(Map<String, Object> model) {
        model.put("patrocinador", new Patrocinador()); 
        return "formularioPatrocinador"; 
    }

    // Método para guardar el patrocinador
    @PostMapping("/guardarPatrocinador")
    public String guardarPatrocinadorYUsuario(@ModelAttribute Patrocinador patrocinador, @ModelAttribute Usuarios usuario, Model model) {
        try {
            usuarioservice.save(usuario);
            patrocinador.setId_usuarios(usuario.getId_usuarios());
            patrocinadorservice.save(patrocinador);

            model.addAttribute("mensaje", "Patrocinador guardado exitosamente");
            return "redirect:/listar"; 
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar el Voluntario y Usuario: " + e.getMessage());
            return "error";
        }
    }	
	
    @RequestMapping(value = "/patrocinadores", method = RequestMethod.GET)
    public String listarYEditar(
            @RequestParam(value = "id", required = false) Long id,
            Map<String, Object> model,
            RedirectAttributes flash) {
        Patrocinador patrocinador = new Patrocinador();
        Usuarios usuario = new Usuarios();

        if (id != null && id > 0) {
        	patrocinador = patrocinadorservice.findOne(id);
            if (patrocinador != null) {
                usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
            } else {
                flash.addFlashAttribute("info", "El patrocinador no existe en la base de datos");
                return "redirect:/formularioPatrocinador";
            }
        }

        model.put("patrocinadores", patrocinador); 
        model.put("usuario", usuario);
        model.put("titulo", "Editar o Crear Patrocinadores");
        return "formularioPatrocinador";
    }


  
    @RequestMapping(value="/formulario/{id}")
    public String guardarVoluntarioYUsuario(
            @ModelAttribute("patrocinador") Patrocinador patrocinador,
            @ModelAttribute("usuario") Usuarios usuario,
            Model model) {
        try {
            if (usuario.getId_usuarios() != null) {
                Usuarios usuarioExistente = usuarioservice.findOne(usuario.getId_usuarios());
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

            usuarioservice.save(usuario);
            patrocinador.setId_usuarios(usuario.getId_usuarios());
            patrocinadorservice.save(patrocinador);

            return "redirect:/listar";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al guardar: " + e.getMessage());
            return "error";
        }
    }
	
    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        patrocinadorservice.delete(id); 
        // Llama al servicio para eliminar el patrocinador
        return "redirect:/listar"; // Redirige a la lista de patrocinadores
    }
	
	
	
	

	
	
}
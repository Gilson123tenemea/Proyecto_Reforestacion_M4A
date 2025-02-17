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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IUsuarioServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("idPatrocinador")
public class PatrocinadorController {
    
    @Autowired
    private IPatrocinadorServices patrocinadorservice;
    
    @Autowired
    private IUsuarioServices usuarioservice;
    @Autowired
    private IParroquiaService parroquiaService;
    @Autowired
    private ICantonService cantonService; 
    @Autowired
    private IProvinciaService provinciaService; 
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/listar")
    public String listarPatrocinadores(Model model) {
        model.addAttribute("titulo", "Lista de Patrocinadores");

        List<Patrocinador> patrocinadores = patrocinadorservice.findAll();
        model.addAttribute("patrocinadores", patrocinadores);
        return "Lista";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    
    // Inicio Patrocinador
    @GetMapping("/iniciopatrocinador")
    public String iniciopatrocinador(@SessionAttribute("idPatrocinador") Long idPatrocinador, Model model) {
        model.addAttribute("titulo", "Inicio Patrocinador");

        Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);
        if (patrocinador != null) {
            Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
            if (usuario != null) {
                model.addAttribute("nombrePatrocinador", usuario.getNombre());
                model.addAttribute("apellidoPatrocinador", usuario.getApellido());
            }
        }

        return "iniciopatrocinador"; 
    }

    @GetMapping("/formularioPatrocinador")
    public String crearPatrocinador(Model model) {
        model.addAttribute("patrocinador", new Patrocinador());
        model.addAttribute("provincias", provinciaService.findAll());
        return "formularioPatrocinador"; 
    }

    @GetMapping("/editarPatrocinador")
    public String editarPatrocinador(@SessionAttribute Long idPatrocinador, Map<String, Object> model) {
        Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);
        Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
        
        model.put("patrocinadores", patrocinador);
        model.put("usuario", usuario);
        model.put("provincias", provinciaService.findAll());

        return "editarPatrocinador";
    }
    
    @PostMapping("/guardarPatrocinador")
    public String guardarPatrocinadorYUsuario(@ModelAttribute Patrocinador patrocinador, @ModelAttribute Usuarios usuario, Model model) {
        usuarioservice.save(usuario); // Guarda el usuario
        patrocinador.setId_usuarios(usuario.getId_usuarios()); // Asocia el usuario al patrocinador
        patrocinadorservice.save(patrocinador); // Guarda el patrocinador
        return "redirect:/login"; 
    }

    @RequestMapping(value="/formulario/{id}")
    public String guardarPatrocinadorDesdeFormulario(@ModelAttribute("patrocinador") Patrocinador patrocinador,
            @ModelAttribute("usuario") Usuarios usuario, Model model) {
        if (usuario.getId_usuarios() != null) {
            Usuarios usuarioExistente = usuarioservice.findOne(usuario.getId_usuarios());
            if (usuarioExistente != null) {
                usuarioExistente.setCedula(usuario.getCedula());
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setApellido(usuario.getApellido());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setId_parroquia(usuario.getId_parroquia());
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
        return "redirect:/login";
    }
    
    @PostMapping("/ActualizarPatrocinador")
    public String actualizarPatrocinador(@RequestParam(value = "id_usuarios", required = false) Long idUsuario,
            @RequestParam(value = "id_patrocinador", required = false) Long idPatrocinador,
            @ModelAttribute("usuario") Usuarios usuario, 
            @ModelAttribute("patrocinador") Patrocinador patrocinador) {
        Usuarios usuarioExistente = usuarioservice.findOne(idUsuario);
        if (usuarioExistente != null) {
            usuarioExistente.setId_usuarios(idUsuario);
            if (usuario.getCedula() != null) {
                usuarioExistente.setCedula(usuario.getCedula());
            }
            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                usuarioExistente.setNombre(usuario.getNombre());
            }
            if (usuario.getApellido() != null && !usuario.getApellido().isEmpty()) {
                usuarioExistente.setApellido(usuario.getApellido());
            }
            if (usuario.getCorreo() != null && !usuario.getCorreo().isEmpty()) {
                usuarioExistente.setCorreo(usuario.getCorreo());
            }
            if (usuario.getFecha_nacimiento() != null) {
                usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
            }
            if (usuario.getId_parroquia() != null) {
                usuarioExistente.setId_parroquia(usuario.getId_parroquia());
            }
            if (usuario.getCelular() != null && !usuario.getCelular().isEmpty()) {
                usuarioExistente.setCelular(usuario.getCelular());
            }
            if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                usuarioExistente.setContraseña(usuario.getContraseña());
            }
            usuarioservice.save(usuarioExistente);
        }

        if (idPatrocinador != null) {
            Patrocinador patrocinadorExistente = patrocinadorservice.findOne(idPatrocinador);
            if (patrocinadorExistente != null) {
                patrocinadorExistente.setId_patrocinador(idPatrocinador);
                if (patrocinador.getNombreEmpresa() != null && !patrocinador.getNombreEmpresa().isEmpty()) {
                    patrocinadorExistente.setNombreEmpresa(patrocinador.getNombreEmpresa());
                }
                if (patrocinador.getRuc() != null && !patrocinador.getRuc().isEmpty()) {
                    patrocinadorExistente.setRuc(patrocinador.getRuc());
                }
                patrocinadorservice.save(patrocinadorExistente);
            }
        } else {
            patrocinadorservice.save(patrocinador);
        }

        return "redirect:/verproyectospatrocinador";
    }
    
    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        patrocinadorservice.delete(id); 
        return "redirect:/listar"; 
    }
    
    @GetMapping("/patrocinador/cantones/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvinciaPatrocinador(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }

    @GetMapping("/patrocinador/parroquias/{idCanton}")
    @ResponseBody
    public List<Parroquia> getParroquiasByCanton(@PathVariable Long idCanton) {
        return parroquiaService.findByCanton(idCanton);
    }
}
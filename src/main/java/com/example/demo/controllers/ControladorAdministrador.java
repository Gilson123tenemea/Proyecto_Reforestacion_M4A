package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Administrador;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Provincia;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IUsuarioServices;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("idSuperAdministrador")
public class ControladorAdministrador {
    
    @Autowired
    private IAdministradorServices administradorServices;
    
    @Autowired
    private IUsuarioServices usuarioServices;
    
    @Autowired
    private IProvinciaService provinciaService;

    @Autowired
    private ICantonService cantonService;

    @Autowired
    private IParroquiaService parroquiaService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/listarAdministradores")
    public String listarAdministradores(Model model) {
        model.addAttribute("titulo", "Lista de Administradores");

        List<Administrador> administradores = administradorServices.findAll();
        List<Usuarios> usuarios = usuarioServices.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();

        for (Usuarios usuario : usuarios) {
            if (!usuario.getAdministrador().isEmpty()) {
                Administrador administrador = usuario.getAdministrador().get(0); 
                Map<String, Object> datosCombinados = new HashMap<>();
                datosCombinados.put("administrador", administrador);
                datosCombinados.put("usuario", usuario);
                combinados.add(datosCombinados);
            }
        }

        model.addAttribute("combinados", combinados);
        return "listarAdministradores";
    }

    @GetMapping("/administrador")
    public String listarYEditar(@RequestParam(value = "id", required = false) Long id, Map<String, Object> model, RedirectAttributes flash) {
        Administrador administrador = new Administrador();
        Usuarios usuario = new Usuarios();
        
        if (id != null && id > 0) {
            administrador = administradorServices.findOne(id);
            if (administrador != null) {
                usuario = usuarioServices.findOne(administrador.getId_usuarios());
            } else {
                flash.addFlashAttribute("info", "El administrador no existe en la base de datos");
                return "redirect:/listarAdministradores";
            }
        }
        
        model.put("administrador", administrador);
        model.put("usuario", usuario);
        model.put("provincias", provinciaService.findAll());
        model.put("titulo", "Editar o Crear Administrador");
        
        return "administrador";
    }

    @PostMapping("/guardar")
    public String guardarAdministradorYUsuario(
            @Valid @ModelAttribute("administrador") Administrador administrador,
            @Valid @ModelAttribute("usuario") Usuarios usuario,
            BindingResult result,
            Model model) {

        // Si hay errores en la validación
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar o Crear Administrador");
            model.addAttribute("provincias", provinciaService.findAll());

            // Extrae los mensajes de error y los agrega al modelo
            StringBuilder errores = new StringBuilder();
            result.getAllErrors().forEach(error -> errores.append(error.getDefaultMessage()).append("<br>"));

            model.addAttribute("error", errores.toString());
            return "administrador";
        }

        try {
            // Verificar si la cédula ya está registrada en otro usuario
            String cedula = usuario.getCedula().trim();
            List<Usuarios> usuariosExistentes = usuarioServices.findAll();

            for (Usuarios usuarioExistente : usuariosExistentes) {
                if (usuarioExistente.getCedula().trim().equals(cedula) && 
                    (usuario.getId_usuarios() == null || !usuarioExistente.getId_usuarios().equals(usuario.getId_usuarios()))) {
                    model.addAttribute("error", "La cédula ya está registrada en otro usuario.");
                    model.addAttribute("titulo", "Editar o Crear Administrador");
                    model.addAttribute("provincias", provinciaService.findAll());
                    return "administrador";
                }
            }

         // Verificar que el usuario sea mayor de 18 años
            if (usuario.getFecha_nacimiento() != null) {
                Date fechaNacimientoDate = usuario.getFecha_nacimiento();
                LocalDate fechaNacimiento = fechaNacimientoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                int edad = Period.between(fechaNacimiento, fechaActual).getYears();

                if (edad < 18) {
                    model.addAttribute("error", "El usuario debe ser mayor de 18 años.");
                    model.addAttribute("titulo", "Editar o Crear Administrador");
                    model.addAttribute("provincias", provinciaService.findAll());
                    return "administrador";
                }
            }

            // Convertir el nombre y apellido a mayúsculas antes del guardado
            if (usuario.getNombre() != null) {
                usuario.setNombre(usuario.getNombre().toUpperCase());
            }
            if (usuario.getApellido() != null) {
                usuario.setApellido(usuario.getApellido().toUpperCase());
            }

            // Si el usuario ya existe, actualizarlo
            if (usuario.getId_usuarios() != null) {
                Usuarios usuarioExistente = usuarioServices.findOne(usuario.getId_usuarios());
                if (usuarioExistente != null) {
                    usuarioExistente.setCedula(usuario.getCedula());
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setApellido(usuario.getApellido());
                    usuarioExistente.setCorreo(usuario.getCorreo());
                    usuarioExistente.setId_parroquia(usuario.getId_parroquia());
                    usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                    usuarioExistente.setGenero(usuario.getGenero());
                    usuarioExistente.setCelular(usuario.getCelular());
                    usuarioExistente.setContraseña(usuario.getContraseña());
                    usuario = usuarioExistente;
                }
            }

            usuarioServices.save(usuario);
            administrador.setId_usuarios(usuario.getId_usuarios());
            administradorServices.save(administrador);

            model.addAttribute("success", "Administrador y usuario guardados correctamente");
            return "redirect:/listarAdministradores";

        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "administrador";
        }
    }


    @GetMapping("/inicioadmin")
    public String iniciosuperadmin(Model model) {
        model.addAttribute("titulo", "Inicio SuperAdmin");
        return "inicioadmin";
    }
    
    @GetMapping("/verlistasproyectos")
    public String verlistasproyectos(Model model) {
        model.addAttribute("titulo", "lista proyectos ");
        return "verlistasproyectos";
    }
    
    @GetMapping("/verlistasactividades")
    public String verlistasactividades(Model model) {
        model.addAttribute("titulo", "lista actividades ");
        return "verlistasactividades";
    }


    @PostMapping("/delete/{id}")
    public String deleteAdministrador(@PathVariable Long id, Model model) {
        try {
            Administrador administrador = administradorServices.findOne(id);

            if (administrador == null) {
                model.addAttribute("mensaje", "Administrador no encontrado con ID: " + id);
                return "error";
            }

            administradorServices.delete(id);
            model.addAttribute("mensaje", "Administrador eliminado exitosamente");
            return "redirect:/listarAdministradores";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el administrador: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/admin/cantones/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvincia(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }

    @GetMapping("/admin/parroquias/{idCanton}")
    @ResponseBody
    public List<Parroquia> getParroquiasByCanton(@PathVariable Long idCanton) {
        return parroquiaService.findByCanton(idCanton);
    }
}
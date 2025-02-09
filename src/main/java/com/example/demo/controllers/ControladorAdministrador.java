package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.put("provincias", provinciaService.findAll()); // Cargar provincias siempre
        if (usuario.getId_parroquia() != null) {
            Canton canton = cantonService.findOne(usuario.getId_parroquia());
            model.put("cantones", cantonService.findByProvincia(canton.getId_provincia())); // Cargar cantones si ya tiene parroquia
            model.put("parroquias", parroquiaService.findByCanton(canton.getId_canton())); // Cargar parroquias si ya tiene canton
        }
        model.put("titulo", "Editar o Crear Administrador");
        
        return "administrador"; // Cambia a la vista del formulario
    }

    @PostMapping("/guardar")
    public String guardarAdministradorYUsuario(
            @ModelAttribute("administrador") Administrador administrador,
            @ModelAttribute("usuario") Usuarios usuario,
            Model model) {
        try {
            // Validación de cédula no vacía
            if (usuario.getCedula() == null || usuario.getCedula().isEmpty()) {
                model.addAttribute("error", "La cédula no puede estar vacía.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";
            }

            // Validación de correo no vacío
            if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
                model.addAttribute("error", "El correo no puede estar vacío.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";
            }

            // Verificar si ya existe una cédula igual (ignorando mayúsculas y minúsculas)
            String cedula = usuario.getCedula().trim();
            List<Usuarios> usuariosExistentes = usuarioServices.findAll();

            for (Usuarios usuarioExistente : usuariosExistentes) {
                if (usuarioExistente.getCedula().trim().equals(cedula)) {
                    model.addAttribute("error", "La cédula ya está registrada en el sistema.");
                    model.addAttribute("provincias", provinciaService.findAll());  // Cargar provincias
                    if (usuario.getId_parroquia() != null) {
                        Canton canton = cantonService.findOne(usuario.getId_parroquia());
                        model.addAttribute("cantones", cantonService.findByProvincia(canton.getId_provincia()));  // Cargar cantones si ya tiene parroquia
                        model.addAttribute("parroquias", parroquiaService.findByCanton(canton.getId_canton()));  // Cargar parroquias si ya tiene canton
                    }
                    return "administrador";  // Retornar al formulario con el mensaje de error
                }
            }

            // Validación de cédula duplicada dentro de la transacción
            try {
                usuarioServices.save(usuario);  // Esto lanza excepción si se encuentra duplicada
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("error", "La cédula ya está registrada en el sistema.");
                model.addAttribute("provincias", provinciaService.findAll());
                if (usuario.getId_parroquia() != null) {
                    Canton canton = cantonService.findOne(usuario.getId_parroquia());
                    model.addAttribute("cantones", cantonService.findByProvincia(canton.getId_provincia()));
                    model.addAttribute("parroquias", parroquiaService.findByCanton(canton.getId_canton()));
                }
                return "administrador";
            }

            // Validación de nombre no vacío
            if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
                model.addAttribute("error", "El nombre no puede estar vacío.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";
            }

            // Validación de celular: debe tener 10 dígitos
            if (usuario.getCelular() == null || !usuario.getCelular().matches("\\d{10}")) {
                model.addAttribute("error", "El celular debe contener 10 dígitos.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";
            }

            // Validación de contraseña: al menos 8 caracteres, con mayúscula, minúscula y número
            if (usuario.getContraseña() == null || usuario.getContraseña().length() < 8 || 
                !usuario.getContraseña().matches(".*[A-Z].*") || 
                !usuario.getContraseña().matches(".*[a-z].*") || 
                !usuario.getContraseña().matches(".*\\d.*")) {
                model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula y un número.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";
            }

            // Validación de edad mayor de 18 años
            if (!esMayorDeEdad(usuario.getFecha_nacimiento())) {
                model.addAttribute("error", "El Administrador debe ser mayor de 18 años.");
                model.addAttribute("provincias", provinciaService.findAll());
                return "administrador";  // Retornar al formulario con el mensaje de error
            }

            // Actualiza el usuario existente si tiene ID
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

            // Guarda el administrador y el usuario
            administrador.setId_usuarios(usuario.getId_usuarios());
            administradorServices.save(administrador);

            model.addAttribute("success", "Administrador y usuario guardados exitosamente.");
            return "redirect:/listarAdministradores";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "error";
        }
    }


    // Método para verificar si la fecha de nacimiento es mayor de 18 años
    private boolean esMayorDeEdad(Date fechaNacimiento) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaNacimiento);
        
        // Obtener la fecha de hoy
        Calendar hoy = Calendar.getInstance();
        
        // Calcular la diferencia de años
        int edad = hoy.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

        // Si no ha cumplido años este año, restamos uno
        if (hoy.get(Calendar.MONTH) < calendar.get(Calendar.MONTH) || 
            (hoy.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && hoy.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH))) {
            edad--;
        }

        return edad >= 18;  // Verifica si tiene 18 o más años
    }




    @GetMapping("/inicioadmin")
    public String iniciosuperadmin(Model model) {
        model.addAttribute("titulo", "Inicio SuperAdmin");
        return "inicioadmin";
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

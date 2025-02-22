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
import org.springframework.validation.FieldError;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Administrador;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Patrocinador;
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
        boolean esEdicion = false; 

        if (id != null && id > 0) {
            esEdicion = true; 
            administrador = administradorServices.findOne(id);
            if (administrador != null) {
                usuario = usuarioServices.findOne(administrador.getId_usuarios());
                // Cargar la parroquia relacionada con el usuario
                if (usuario.getId_parroquia() != null) {
                    Parroquia parroquia = parroquiaService.findOne(usuario.getId_parroquia());
                    if (parroquia != null) {
                        Canton canton = cantonService.findOne(parroquia.getId_canton());
                        if (canton != null) {
                            Long idProvincia = canton.getId_provincia();
                            model.put("provinciaSeleccionada", idProvincia);
                            model.put("cantonSeleccionado", canton);
                            model.put("parroquiaSeleccionada", parroquia);
                            model.put("cantones", cantonService.findByProvincia(idProvincia)); // Carga los cantones
                            model.put("parroquias", parroquiaService.findByCanton(canton.getId_canton()));
                            model.put("parroquiaSeleccionada", parroquia.getId_parroquia());

                        }
                    }
                }
            } else {
                flash.addFlashAttribute("info", "El administrador no existe en la base de datos");
                return "redirect:/listarAdministradores";
            }
        }

        model.put("administrador", administrador);
        model.put("usuario", usuario);
        model.put("provincias", provinciaService.findAll());
        model.put("titulo", "Editar o Crear Administrador");
        model.put("esEdicion", esEdicion); 

        return "administrador";
        
    }
    
    public boolean esContrasenaValida(String contrasena) {
        // Expresión regular para validar la contraseña
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$";

        // Verificar que la contraseña no sea nula y cumpla con la expresión regular
        return contrasena != null && contrasena.matches(regex);
    }
    
    public boolean esCedulaValida(String cedula) {
        // Verificar que tenga exactamente 10 dígitos numéricos
        if (cedula == null || !cedula.matches("\\d{10}")) {
            return false;
        }

        // Extraer los componentes de la cédula
        int provincia = Integer.parseInt(cedula.substring(0, 2)); // Dos primeros dígitos
        int tercerDigito = Character.getNumericValue(cedula.charAt(2)); // Tercer dígito

        // Verificar que la provincia esté entre 01 y 24
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        // Verificar que el tercer dígito sea válido (0-6 para personas naturales)
        if (tercerDigito < 0 || tercerDigito > 6) {
            return false;
        }

        // Aplicar el algoritmo de verificación de cédula (módulo 10)
        int suma = 0;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2}; // Coeficientes alternados

        for (int i = 0; i < 9; i++) {
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            suma += (valor > 9) ? valor - 9 : valor;
        }

        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int residuo = suma % 10;
        int digitoCalculado = (residuo == 0) ? 0 : 10 - residuo;

        // Comparar el dígito calculado con el último dígito de la cédula
        return digitoCalculado == digitoVerificador;
    }

    @PostMapping("/guardar")
    public String guardarAdministradorYUsuario(
            @Valid @ModelAttribute("administrador") Administrador administrador,
            @Valid @ModelAttribute("usuario") Usuarios usuario,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar o Crear Administrador");
            model.addAttribute("provincias", provinciaService.findAll());
            model.addAttribute("usuario", usuario);
            model.addAttribute("administrador", administrador);
            model.addAttribute("fecha_nacimiento", usuario.getFecha_nacimiento());
            model.addAttribute("contraseña", usuario.getContraseña());

            // Agregar errores específicos al modelo
            for (FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            return "administrador";
        }

        try {
            // Validar cédula ecuatoriana
            String cedula = usuario.getCedula().trim();
            if (!esCedulaValida(cedula)) {
                model.addAttribute("cedulaError", "La cédula ingresada no es válida, tiene que ser una cédula ecuatoriana.");
                return manejarErrores(model, usuario, administrador, null);
            }

            // Validar contraseña segura
            if (!esContrasenaValida(usuario.getContraseña().trim())) {
                model.addAttribute("contraseñaError", "La contraseña debe tener entre 8 y 16 caracteres, incluir mayúsculas, minúsculas, números y al menos un carácter especial.");
                return manejarErrores(model, usuario, administrador, null);
            }

            // Validar nombre
            if (!esNombreValido(usuario.getNombre())) {
                model.addAttribute("nombreError", "El nombre debe ser válido (ejemplo: 'Steven Alexander').");
                return manejarErrores(model, usuario, administrador, null);
            }

            // Validar apellido
            if (!esApellidoValido(usuario.getApellido())) {
                model.addAttribute("apellidoError", "El apellido debe ser válido (ejemplo: 'Carpio Chillogallo').");
                return manejarErrores(model, usuario, administrador, null);
            }

            // Verificar si la cédula ya está registrada en otro usuario (solo en modo creación)
            if (usuario.getId_usuarios() == null) { // Modo creación
                List<Usuarios> usuariosExistentes = usuarioServices.findAll();
                for (Usuarios usuarioExistente : usuariosExistentes) {
                    if (usuarioExistente.getCedula().trim().equals(cedula)) {
                        model.addAttribute("cedulaError", "La cédula ya está registrada en otro usuario.");
                        return manejarErrores(model, usuario, administrador, null);
                    }
                }
            }

            // Verificar que el usuario sea mayor de 18 años
            if (usuario.getFecha_nacimiento() != null) {
                Date fechaNacimientoDate = usuario.getFecha_nacimiento();
                LocalDate fechaNacimiento = fechaNacimientoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                int edad = Period.between(fechaNacimiento, fechaActual).getYears();

                if (edad < 18) {
                    model.addAttribute("fecha_nacimientoError", "El usuario debe ser mayor de 18 años.");
                    return manejarErrores(model, usuario, administrador, null);
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
            return manejarErrores(model, usuario, administrador, null);
        }
    }


    
 // Método para validar el nombre
    private boolean esNombreValido(String nombre) {
        return nombre != null && nombre.trim().length() > 1 && nombre.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");
    }
    
 // Método para validar el apellido
    private boolean esApellidoValido(String apellido) {
        return apellido != null && apellido.trim().length() > 1 && apellido.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");
    }
    
    
 // Método auxiliar para manejar errores y mantener los valores
    private String manejarErrores(Model model, Usuarios usuario, Administrador administrador, BindingResult result) {
        model.addAttribute("titulo", "Editar o Crear Administrador");
        model.addAttribute("provincias", provinciaService.findAll());
        model.addAttribute("usuario", usuario);
        model.addAttribute("administrador", administrador);

        if (result != null) {
            // Extraer los mensajes de error y agregarlos al modelo
            StringBuilder errores = new StringBuilder();
            result.getAllErrors().forEach(error -> errores.append(error.getDefaultMessage()).append("<br>"));
            model.addAttribute("error", errores.toString());
        }

        return "administrador"; // Volver al formulario
    }



    @GetMapping("/inicioadmin")
    public String iniciosuperadmin(@SessionAttribute("idAdministrador") Long idAdministrador, Model model) {
        model.addAttribute("titulo", "Inicio SuperAdmin");
        
        Administrador admin= administradorServices.findOne(idAdministrador);
        if (admin !=null) {
        	 Usuarios usuario = usuarioServices.findOne(admin.getId_usuarios());
			if (usuario !=null) {
				model.addAttribute("nombreAdmin", usuario.getNombre());
				model.addAttribute("apellidoAdmin", usuario.getApellido());
			}
		}
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
            // Verificar si el administrador existe
            Administrador administrador = administradorServices.findOne(id);
            
            if (administrador == null) {
                model.addAttribute("mensaje", "Administrador no encontrado con ID: " + id);
                return "error";
            }

            // Verificar si hay proyectos, equipos o tipos de actividades asociados
            long proyectosCount = administradorServices.countProyectosByAdministradorId(id);
            long equiposCount = administradorServices.countEquiposByAdministradorId(id);
            long tipoActividadesCount = administradorServices.countTipoActividadesByAdministradorId(id);
            
            if (proyectosCount > 0 || equiposCount > 0 || tipoActividadesCount > 0) {
                model.addAttribute("mensaje", "No se puede eliminar el administrador porque tiene proyectos, equipos o tipos de actividades asociados.");
                return "error"; // Mostrar un mensaje de error
            }

            // Eliminar el administrador
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
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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;

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
        model.addAttribute("titulo", "Lista de Patrocinadores ");

        List<Patrocinador> patrocinador = patrocinadorservice.findAll();
        List<Usuarios> usuarios = usuarioservice.findAll();

        List<Map<String, Object>> combinados = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            if (usuario.getPatrocinador() != null && !usuario.getPatrocinador().isEmpty()) {
                Patrocinador patrocinadores  = usuario.getPatrocinador().get(0);
                Map<String, Object> datosCombinados = new HashMap<>();
                datosCombinados.put("patrocinadores", patrocinadores);
                datosCombinados.put("usuario", usuario);
                combinados.add(datosCombinados);
            }
        }

        model.addAttribute("combinados", combinados);
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

    @GetMapping("/iniciopatrocinador")
    public String iniciopatrocinador(@SessionAttribute("idPatrocinador") Long idPatrocinador, Model model) {
        model.addAttribute("titulo", "Inicio Patrocinador");

        Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);
        if (patrocinador != null) {
            Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());
            if (usuario != null) {
                model.addAttribute("nombrePatrocinador", usuario.getNombre());
                model.addAttribute("apellidoPatrocinador", usuario.getApellido());
                model.addAttribute("id_patrocinador", idPatrocinador); 
            }
        }

        return "iniciopatrocinador";
    }


    @GetMapping("/editarPatrocinador/{idPatrocinador}")
    public String editarPatrocinador(@PathVariable Long idPatrocinador, Model model) {
        Patrocinador patrocinador = patrocinadorservice.findOne(idPatrocinador);
        Usuarios usuario = usuarioservice.findOne(patrocinador.getId_usuarios());

        model.addAttribute("patrocinador", patrocinador);
        model.addAttribute("usuario", usuario);
        model.addAttribute("provincias", provinciaService.findAll());

        return "editarPatrocinador"; // Nombre de la vista
    }
    
    @GetMapping("/formularioPatrocinador")
    public String crearPatrocinador(Model model) {
        model.addAttribute("patrocinador", new Patrocinador());
        model.addAttribute("usuario", new Usuarios()); // Asegúrate de inicializar el objeto usuario
        model.addAttribute("provincias", provinciaService.findAll());
        return "formularioPatrocinador";
    }

    @PostMapping("/guardarPatrocinador")
    public String guardarPatrocinadorYUsuario(
            @Valid @ModelAttribute("patrocinador") Patrocinador patrocinador,
            @Valid @ModelAttribute("usuario") Usuarios usuario,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Comprobar si hay errores de validación antes de continuar
        if (result.hasErrors()) {
            return manejarErrores(model, usuario, patrocinador, result);
        }

        try {
            
            // Validar cédula ecuatoriana
            String cedula = usuario.getCedula().trim();
            if (!esCedulaValida(cedula)) {
                model.addAttribute("errorCedula", "La cédula ingresada no es válida, tiene que ser una cédula ecuatoriana.");
                return manejarErrores(model, usuario, patrocinador, null);
            }

            // Validar nombre
            if (!esNombreValido(usuario.getNombre())) {
                model.addAttribute("errorNombre", "El nombre debe ser válido (ejemplo: 'Steven Alexander').");
                return manejarErrores(model, usuario, patrocinador, null);
            }
            
            // Validar nombre de la empresa
            if (!validarNombreEmpresa(patrocinador.getNombreEmpresa())) {
                model.addAttribute("errorNombreEmpresa", "El nombre de la empresa debe ser válido (no puede estar vacío y solo puede contener letras, números y espacios).");
                return manejarErrores(model, usuario, patrocinador, null);
            }

            // Validar apellido
            if (!esApellidoValido(usuario.getApellido())) {
                model.addAttribute("errorApellido", "El apellido debe ser válido (ejemplo: 'Carpio Chillogallo').");
                return manejarErrores(model, usuario, patrocinador, null);
            }

            // Validar RUC
            if (!validarRuc(patrocinador.getRuc())) {
                model.addAttribute("errorRuc", "El RUC debe contener exactamente 13 dígitos.");
                return manejarErrores(model, usuario, patrocinador, null);
            }

            // Verificar si la cédula ya está registrada en otro usuario
            List<Usuarios> usuariosExistentes = usuarioservice.findAll();
            for (Usuarios usuarioExistente : usuariosExistentes) {
                if (usuarioExistente.getCedula().trim().equals(cedula) && 
                    (usuario.getId_usuarios() == null || !usuarioExistente.getId_usuarios().equals(usuario.getId_usuarios()))) {
                    model.addAttribute("errorCedula", "La cédula ya está registrada en otro usuario.");
                    return manejarErrores(model, usuario, patrocinador, null);
                }
            }

            // Verificar que el usuario sea mayor de 18 años
            if (usuario.getFecha_nacimiento() != null) {
                Date fechaNacimientoDate = usuario.getFecha_nacimiento();
                LocalDate fechaNacimiento = fechaNacimientoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                int edad = Period.between(fechaNacimiento, fechaActual).getYears();

                if (edad < 18) {
                    model.addAttribute("errorFechaNacimiento", "El usuario debe ser mayor de 18 años.");
                    return manejarErrores(model, usuario, patrocinador, null);
                }
            }

            // Convertir el nombre y apellido a mayúsculas antes del guardado
            if (usuario.getNombre() != null) {
                usuario.setNombre(usuario.getNombre().toUpperCase());
            }
            if (usuario.getApellido() != null) {
                usuario.setApellido(usuario.getApellido().toUpperCase());
            }
         // Validar celular
            if (!validarCelular(usuario.getCelular())) {
                model.addAttribute("errorCelular", "El celular debe contener exactamente 10 dígitos.");
                return manejarErrores(model, usuario, patrocinador, null);
            }

            // Guardar usuario
            usuarioservice.save(usuario);
            patrocinador.setId_usuarios(usuario.getId_usuarios()); // Asociar usuario al patrocinador
            patrocinadorservice.save(patrocinador); // Guardar patrocinador

            // Agregar mensaje de éxito a RedirectAttributes
            redirectAttributes.addFlashAttribute("success", "Patrocinador guardado exitosamente.");
            return "redirect:/login"; // Redirigir al login

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al guardar el patrocinador: " + e.getMessage());
            return manejarErrores(model, usuario, patrocinador, null);
        }
    }
    
 // Método para validar el celular
    private boolean validarCelular(String celular) {
        return celular != null && celular.matches("\\d{10}");
    }

    // Método auxiliar para manejar errores y mantener los valores
    private String manejarErrores(Model model, Usuarios usuario, Patrocinador patrocinador, BindingResult result) {
        model.addAttribute("titulo", "Editar o Crear Patrocinador");
        model.addAttribute("provincias", provinciaService.findAll());
        model.addAttribute("usuario", usuario);
        model.addAttribute("patrocinador", patrocinador);

        if (result != null) {
            // Extraer los mensajes de error y agregarlos al modelo
            result.getFieldErrors().forEach(error -> {
                model.addAttribute("error" + capitalizeFirstLetter(error.getField()), error.getDefaultMessage());
            });
        }

        return "formularioPatrocinador"; // Volver al formulario
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
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

    // Método para validar el RUC
    private boolean validarRuc(String ruc) {
        return ruc != null && ruc.matches("^\\d{13}$");
    }
    
    // Método para validar el nombre
    private boolean esNombreValido(String nombre) {
        return nombre != null && nombre.trim().length() > 1 && nombre.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");
    }

    // Método para validar el apellido
    private boolean esApellidoValido(String apellido) {
        return apellido != null && apellido.trim().length() > 1 && apellido.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");
    }
   
    private boolean validarNombreEmpresa(String nombree) {
        return nombree != null && nombree.trim().length() > 1 && nombree.trim().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+");
    }

    @PostMapping("/ActualizarPatrocinador")
    public String actualizarPatrocinador(
            @RequestParam(value = "id_usuarios", required = false) Long idUsuario,
            @RequestParam(value = "id_patrocinador", required = false) Long idPatrocinador,
            @Valid @ModelAttribute("usuario") Usuarios usuario,
            BindingResult resultUsuario,
            @Valid @ModelAttribute("patrocinador") Patrocinador patrocinador,
            BindingResult resultPatrocinador,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Validar si hay errores
        if (resultUsuario.hasErrors() || resultPatrocinador.hasErrors()) {
            // Manejo de errores
            StringBuilder errores = new StringBuilder();
            if (resultUsuario.hasErrors()) {
                resultUsuario.getFieldErrors().forEach(error -> {
                    errores.append(error.getDefaultMessage()).append("<br>");
                });
            }

            if (resultPatrocinador.hasErrors()) {
                resultPatrocinador.getFieldErrors().forEach(error -> {
                    errores.append(error.getDefaultMessage()).append("<br>");
                });
            }

            model.addAttribute("error", errores.toString());
            model.addAttribute("patrocinador", patrocinador);
            model.addAttribute("usuario", usuario);
            model.addAttribute("provincias", provinciaService.findAll());

            return "editarPatrocinador"; // Volver al formulario de edición
        }

        try {
            // Obtener el usuario existente
            Usuarios usuarioExistente = usuarioservice.findOne(idUsuario);
            if (usuarioExistente != null) {
                // No actualizar la cédula
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setApellido(usuario.getApellido());
                usuarioExistente.setCorreo(usuario.getCorreo());
                usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                usuarioExistente.setId_parroquia(usuario.getId_parroquia());
                usuarioExistente.setCelular(usuario.getCelular());
                usuarioExistente.setContraseña(usuario.getContraseña());
                usuarioservice.save(usuarioExistente);
            }

            // Obtener el patrocinador existente
            Patrocinador patrocinadorExistente = patrocinadorservice.findOne(idPatrocinador);
            if (patrocinadorExistente != null) {
                patrocinadorExistente.setNombreEmpresa(patrocinador.getNombreEmpresa());
                patrocinadorExistente.setRuc(patrocinador.getRuc());
                patrocinadorservice.save(patrocinadorExistente);
            }

            redirectAttributes.addFlashAttribute("success", "Patrocinador actualizado exitosamente.");
            return "redirect:/verproyectospatrocinador"; // Redirigir a la vista de proyectos
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al actualizar el patrocinador: " + e.getMessage());
            model.addAttribute("patrocinador", patrocinador);
            model.addAttribute("usuario", usuario);
            model.addAttribute("provincias", provinciaService.findAll());

            return "editarPatrocinador"; // Volver al formulario de edición
        }
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

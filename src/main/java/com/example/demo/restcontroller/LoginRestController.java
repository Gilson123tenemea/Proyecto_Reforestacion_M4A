package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.IVoluntariosService;
import com.example.demo.service.UsuariosServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    @Autowired
    private UsuariosServiceImpl usuarioService;
    @Autowired
    private IVoluntariosService voluntariosService;


    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String cedula, @RequestParam String contraseña) {
        Map<String, Object> response = new HashMap<>();

        // Intentar autenticar al usuario
        String rol = usuarioService.authenticate(cedula, contraseña);
        if (rol == null) {
            response.put("error", "Credenciales inválidas. Intente nuevamente.");
            return response; 
        }

        // Buscar usuario en la base de datos
        Usuarios usuario = usuarioService.findAll().stream()
            .filter(u -> u.getCedula().equals(cedula) && u.getContraseña().equals(contraseña))
            .findFirst()
            .orElse(null);

        // Validar si el usuario existe
        if (usuario == null) {
            response.put("error", "Usuario no encontrado.");
            return response;
        }

        // Almacenar atributos de sesión según el rol del usuario
        Map<String, Object> userData = new HashMap<>();
        if (!usuario.getPatrocinador().isEmpty()) {
            userData.put("idPatrocinador", usuario.getPatrocinador().get(0).getId_patrocinador());
        }
        if (!usuario.getVoluntarios().isEmpty()) {
            userData.put("idVoluntario", usuario.getVoluntarios().get(0).getId_voluntario());
        }
        if (!usuario.getAdministrador().isEmpty()) {
            userData.put("idAdministrador", usuario.getAdministrador().get(0).getId_administrador());
        }
        if (!usuario.getSuper_administrador().isEmpty()) {
            userData.put("idSuperAdministrador", usuario.getSuper_administrador().get(0).getId_super_administrador());
        }

        response.put("mensaje", "¡Bienvenido, " + usuario.getNombre() + "!");
        response.put("userData", userData);

        // Redirigir según el rol del usuario
        switch (rol) {
            case "superadmin":
                response.put("redirect", "/iniciosuperadmin");
                break;
            case "administrador":
                response.put("redirect", "/inicioadmin");
                break;
            case "voluntario":
                response.put("redirect", "/proyectosvoluntario");
                break;
            case "patrocinador":
                response.put("redirect", "/iniciopatrocinador");
                break;
            default:
                response.put("error", "Rol no reconocido.");
        }

        return response;
    }

    @GetMapping({"/", "/Inicio"})
    public Map<String, String> inicio() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Bienvenido al inicio de la aplicación");
        return response;
    }
}
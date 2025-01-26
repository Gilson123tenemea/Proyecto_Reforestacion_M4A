package com.example.demo.restcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Administrador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.IUsuarioServices;

@RestController
@RequestMapping("/api/administradores")

public class AdminstradorRestController {
	@Autowired
    private IAdministradorServices administradorServices;

    @Autowired
    private IUsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores() {
        List<Administrador> administradores = administradorServices.findAll();
        return new ResponseEntity<>(administradores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerAdministrador(@PathVariable Long id) {
        Administrador administrador = administradorServices.findOne(id);
        if (administrador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(administrador, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarAdministradorYUsuario(@RequestBody Map<String, Object> input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Map<String, Object> usuarioInput = (Map<String, Object>) input.get("usuario");
            Usuarios usuario = new Usuarios();

            usuario.setCedula((String) usuarioInput.get("cedula"));
            usuario.setNombre((String) usuarioInput.get("nombre"));
            usuario.setApellido((String) usuarioInput.get("apellido"));
            usuario.setCorreo((String) usuarioInput.get("correo"));

            String fechaNacimientoStr = (String) usuarioInput.get("fecha_nacimiento");
            if (fechaNacimientoStr != null) {
                usuario.setFecha_nacimiento(dateFormat.parse(fechaNacimientoStr));
            }

            usuario.setGenero((String) usuarioInput.get("genero"));
            usuario.setCelular((String) usuarioInput.get("celular"));
            usuario.setContraseña((String) usuarioInput.get("contraseña"));
            usuarioServices.save(usuario);
            Administrador administrador = new Administrador();
            administrador.setId_usuarios(usuario.getId_usuarios());
            if (input.containsKey("actividades_gestionadas")) {
                administrador.setActividades_gestionadas((Integer) input.get("actividades_gestionadas"));
            }
            
            if (input.containsKey("id_super_administrador")) {
                administrador.setId_super_administrador(((Number) input.get("id_super_administrador")).longValue());
            }

            administradorServices.save(administrador);

            return new ResponseEntity<>("Administrador y Usuario guardados exitosamente", HttpStatus.CREATED);
        } catch (ParseException e) {
            return new ResponseEntity<>("Error al convertir la fecha: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/modificar/{id}")
    public Map<String, Object> modificarAdministradorYUsuario(@PathVariable Long id, @RequestBody Map<String, Object> input) {
        Map<String, Object> response = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Administrador administradorExistente = administradorServices.findOne(id);
            if (administradorExistente == null) {
                response.put("error", "Administrador no encontrado");
                return response;
            }

            Map<String, Object> usuarioInput = (Map<String, Object>) input.get("usuario");
            Usuarios usuario = usuarioServices.findOne(administradorExistente.getId_usuarios());
            if (usuario != null) {
                usuario.setCedula((String) usuarioInput.get("cedula"));
                usuario.setNombre((String) usuarioInput.get("nombre"));
                usuario.setApellido((String) usuarioInput.get("apellido"));
                usuario.setCorreo((String) usuarioInput.get("correo"));

                String fechaNacimientoStr = (String) usuarioInput.get("fecha_nacimiento");
                if (fechaNacimientoStr != null) {
                    Date fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
                    usuario.setFecha_nacimiento(fechaNacimiento);
                }

                usuario.setGenero((String) usuarioInput.get("genero"));
                usuario.setCelular((String) usuarioInput.get("celular"));
                usuario.setContraseña((String) usuarioInput.get("contraseña"));

                usuarioServices.save(usuario);
            } else {
                response.put("error", "Usuario no encontrado");
                return response;
            }
            if (input.containsKey("actividades_gestionadas")) {
                administradorExistente.setActividades_gestionadas((Integer) input.get("actividades_gestionadas"));
            }
            
            if (input.containsKey("id_super_administrador")) {
                administradorExistente.setId_super_administrador(((Number) input.get("id_super_administrador")).longValue());
            }

            administradorServices.save(administradorExistente);

            response.put("message", "Administrador y Usuario modificados correctamente");
        } catch (ParseException e) {
            response.put("error", "Error al convertir la fecha: " + e.getMessage());
        } catch (Exception e) {
            response.put("error", "Error al modificar: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAdministrador(@PathVariable Long id) {
        try {
            Administrador administrador = administradorServices.findOne(id);
            if (administrador == null) {
                return new ResponseEntity<>("Administrador no encontrado", HttpStatus.NOT_FOUND);
            }
            administradorServices.delete(id);
            return new ResponseEntity<>("Administrador eliminado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.demo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Listar Administradores
    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores() {
        List<Administrador> administradores = administradorServices.findAll();
        return new ResponseEntity<>(administradores, HttpStatus.OK);
    }

    // Obtener un Administrador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerAdministrador(@PathVariable Long id) {
        Administrador administrador = administradorServices.findOne(id);
        if (administrador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(administrador, HttpStatus.OK);
    }

    // Crear o actualizar un Administrador y Usuario
    @PostMapping
    public ResponseEntity<String> guardarAdministradorYUsuario(@RequestBody Administrador administrador) {
        try {
            // Guardar o actualizar el usuario
            Usuarios usuario = usuarioServices.findOne(administrador.getId_usuarios());
            if (usuario == null) {
                usuario = new Usuarios(); // Crea un nuevo usuario si no existe
            }
            // Aquí se asume que el 'usuario' se recibe como parte del 'administrador'
            usuario.setId_usuarios(administrador.getId_usuarios()); // Añadir más campos según sea necesario
            usuarioServices.save(usuario);

            // Asignar el ID de usuario al administrador
            administrador.setId_usuarios(usuario.getId_usuarios());
            administradorServices.save(administrador);

            return new ResponseEntity<>("Administrador y Usuario guardados exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un Administrador
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

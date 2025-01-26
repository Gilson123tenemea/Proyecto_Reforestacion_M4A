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

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IUsuarioServices;



@RestController
@RequestMapping("/api/patrocinador")
public class PatrocinadorRestController {

	@Autowired
    private IPatrocinadorServices patrocinadorservice;

    @Autowired
    private IUsuarioServices usuarioServices;

    // Listar Administradores
    @GetMapping
    public ResponseEntity<List<Patrocinador>> listarPatrocinadores() {
        List<Patrocinador> patrocinadores = patrocinadorservice.findAll();
        return new ResponseEntity<>(patrocinadores, HttpStatus.OK);
    }

    // Obtener un Administrador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Patrocinador> obtenerPatrocinador(@PathVariable Long id) {
    	Patrocinador patrocina = patrocinadorservice.findOne(id);
        if (patrocina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patrocina, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> guardarPatrocinadorYUsuario(@RequestBody Patrocinador patrocinador) {
        try {
            Usuarios usuario = usuarioServices.findOne(patrocinador.getId_usuarios());
            if (usuario == null) {
                usuario = new Usuarios(); // Crea un nuevo usuario si no existe
            }
            usuario.setId_usuarios(patrocinador.getId_usuarios()); // Añadir más campos según sea necesario
            usuarioServices.save(usuario);

            patrocinador.setId_usuarios(usuario.getId_usuarios());
            patrocinadorservice.save(patrocinador);

            return new ResponseEntity<>("Patrocinador creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un Administrador
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPatrocinador(@PathVariable Long id) {
        try {
        	Patrocinador patrocinador = patrocinadorservice.findOne(id);
            if (patrocinador == null) {
                return new ResponseEntity<>("Patrocinador no encontrado", HttpStatus.NOT_FOUND);
            }
            patrocinadorservice.delete(id);
            return new ResponseEntity<>("Patrocinador eliminado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

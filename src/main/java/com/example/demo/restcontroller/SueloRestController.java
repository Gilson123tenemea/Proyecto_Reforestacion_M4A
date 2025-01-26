package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Suelo;
import com.example.demo.service.ISueloService;

import java.util.List;

@RestController
@RequestMapping("/api/suelo")
public class SueloRestController {

    @Autowired
    private ISueloService sueloService;

    // Obtener un Suelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Suelo suelo = sueloService.findOne(id);

        if (suelo == null) {
            return ResponseEntity.badRequest().body("El suelo no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(suelo);
    }

    // Listar todos los Suelos
    @GetMapping
    public ResponseEntity<List<Suelo>> listar() {
        List<Suelo> suelos = sueloService.findAll();
        return ResponseEntity.ok(suelos);
    }

    // Crear un nuevo Suelo
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Suelo suelo) {
        try {
            if (suelo.getId_tiposuelo() == null) {
                throw new Exception("Debe seleccionar un Tipo de suelo.");
            }
            sueloService.save(suelo);
            return ResponseEntity.ok("El registro del suelo se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el suelo: " + e.getMessage());
        }
    }

    // Editar un Suelo
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Suelo suelo) {
        Suelo existente = sueloService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id del suelo no existe en la base de datos.");
        }

        suelo.setId_suelo(id); // Asignar el ID para la actualización
        sueloService.save(suelo);

        return ResponseEntity.ok("El registro del suelo se ha editado con éxito.");
    }

    // Eliminar un Suelo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            sueloService.delete(id);
            return ResponseEntity.ok("El suelo ha sido eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar el suelo porque está siendo referenciado por otras entidades."
            );
        }
    }
}
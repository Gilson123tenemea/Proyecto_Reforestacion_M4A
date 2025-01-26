package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Parroquia;
import com.example.demo.service.IParroquiaService;

import java.util.List;

@RestController
@RequestMapping("/api/parroquias")
public class ParroquiaRestControllers {

    @Autowired
    private IParroquiaService parroquiaService;

    // Obtener una Parroquia por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Parroquia parroquia = parroquiaService.findOne(id);

        if (parroquia == null) {
            return ResponseEntity.badRequest().body("La parroquia no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(parroquia);
    }

    // Listar todas las Parroquias
    @GetMapping
    public ResponseEntity<List<Parroquia>> listar() {
        List<Parroquia> parroquias = parroquiaService.findAll();
        return ResponseEntity.ok(parroquias);
    }

    // Crear una nueva Parroquia
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Parroquia parroquia) {
        try {
            if (parroquia.getId_canton() == null) {
                throw new Exception("Debe seleccionar un Cantón.");
            }
            parroquiaService.save(parroquia);
            return ResponseEntity.ok("El registro de la parroquia se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la parroquia: " + e.getMessage());
        }
    }

    // Editar una Parroquia
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Parroquia parroquia) {
        Parroquia existente = parroquiaService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id de la parroquia no existe en la base de datos.");
        }

        parroquia.setId_parroquia(id); // Asignar el ID para la actualización
        parroquiaService.save(parroquia);

        return ResponseEntity.ok("El registro de la parroquia se ha editado con éxito.");
    }

    // Eliminar una Parroquia
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            parroquiaService.delete(id);
            return ResponseEntity.ok("La parroquia ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar la parroquia porque está siendo referenciada por otras entidades."
            );
        }
    }
}
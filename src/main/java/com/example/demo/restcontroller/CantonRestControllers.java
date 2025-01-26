package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Canton;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IProvinciaService;

import java.util.List;

@RestController
@RequestMapping("/api/cantones")
public class CantonRestControllers {

    @Autowired
    private ICantonService cantonService;

    @Autowired
    private IProvinciaService provinciaService;

    // Obtener un Canton por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Canton canton = cantonService.findOne(id);

        if (canton == null) {
            return ResponseEntity.badRequest().body("El cantón no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(canton);
    }

    // Listar todos los Cantones
    @GetMapping
    public ResponseEntity<List<Canton>> listar() {
        List<Canton> cantones = cantonService.findAll();
        return ResponseEntity.ok(cantones);
    }

    // Crear un nuevo Canton
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Canton canton) {
        try {
            if (canton.getId_provincia() == null) {
                throw new Exception("Debe seleccionar una provincia.");
            }
            cantonService.save(canton);
            return ResponseEntity.ok("El registro del cantón se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el cantón: " + e.getMessage());
        }
    }

    // Editar un Canton
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Canton canton) {
        Canton existente = cantonService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id del cantón no existe en la base de datos.");
        }

        canton.setId_canton(id); // Asignar el ID para la actualización
        cantonService.save(canton);

        return ResponseEntity.ok("El registro del cantón se ha editado con éxito.");
    }

    // Eliminar un Canton
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            cantonService.delete(id);
            return ResponseEntity.ok("El cantón ha sido eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar el cantón porque está siendo referenciado por otras entidades."
            );
        }
    }
}
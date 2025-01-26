package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.service.IAsignacion_proyectoActiService;


import java.util.List;

@RestController
@RequestMapping("/api/asignacion")
public class AsignarProyectosRestController {

    @Autowired
    private IAsignacion_proyectoActiService asignarService;

  

    // Listar todas las asignaciones
    @GetMapping
    public ResponseEntity<List<Asignacion_proyectoActi>> listar() {
        List<Asignacion_proyectoActi> asignaciones = asignarService.findAll();
        return ResponseEntity.ok(asignaciones);
    }

    // Obtener una asignación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Asignacion_proyectoActi asignacion = asignarService.findOne(id);

        if (asignacion == null) {
            return ResponseEntity.badRequest().body("La asignación no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(asignacion);
    }

    // Crear una nueva asignación
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Asignacion_proyectoActi asignacion) {
        try {
            asignarService.save(asignacion);
            return ResponseEntity.ok("La asignación se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la asignación: " + e.getMessage());
        }
    }

    // Editar una asignación
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Asignacion_proyectoActi asignacion) {
        Asignacion_proyectoActi existente = asignarService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id de la asignación no existe en la base de datos.");
        }

        asignacion.setId_asignacionproyecto(id); // Asignar el ID para la actualización
        asignarService.save(asignacion);

        return ResponseEntity.ok("La asignación se ha editado con éxito.");
    }

    // Eliminar una asignación
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            asignarService.delete(id);
            return ResponseEntity.ok("La asignación ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se puede eliminar la asignación: " + e.getMessage());
        }
    }
}
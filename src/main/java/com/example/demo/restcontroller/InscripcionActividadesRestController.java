package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Inscripcion_actividades;
import com.example.demo.service.IInscripcion_actividadesService;
import com.example.demo.service.ITipo_ActividadesService;
import com.example.demo.service.IVoluntariosService;

import java.util.List;

@RestController
@RequestMapping("/api/inscripcion_actividades")
public class InscripcionActividadesRestController {

    @Autowired
    private IInscripcion_actividadesService inscripcionActividadesService;

    
    @GetMapping
    public ResponseEntity<List<Inscripcion_actividades>> listar() {
        List<Inscripcion_actividades> inscripciones = inscripcionActividadesService.findAll();
        return ResponseEntity.ok(inscripciones);
    }

    // Obtener una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Inscripcion_actividades inscripcion = inscripcionActividadesService.findOne(id);

        if (inscripcion == null) {
            return ResponseEntity.badRequest().body("La inscripción no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(inscripcion);
    }

    // Crear una nueva inscripción
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Inscripcion_actividades inscripcion) {
        try {
            inscripcionActividadesService.save(inscripcion);
            return ResponseEntity.ok("La inscripción se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la inscripción: " + e.getMessage());
        }
    }

    // Editar una inscripción
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Inscripcion_actividades inscripcion) {
        Inscripcion_actividades existente = inscripcionActividadesService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id de la inscripción no existe en la base de datos.");
        }

        inscripcion.setId_inscripcionactividades(id); // Asignar el ID para la actualización
        inscripcionActividadesService.save(inscripcion);

        return ResponseEntity.ok("La inscripción se ha editado con éxito.");
    }

    // Eliminar una inscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            inscripcionActividadesService.delete(id);
            return ResponseEntity.ok("La inscripción ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se puede eliminar la inscripción: " + e.getMessage());
        }
    }
}
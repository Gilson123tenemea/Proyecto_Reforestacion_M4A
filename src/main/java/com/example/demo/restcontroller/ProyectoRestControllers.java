package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IProyectoServices;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoRestControllers {

    @Autowired
    private IProyectoServices proyectoService;

    // Obtener un Proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.findOne(id);

        if (proyecto == null) {
            return ResponseEntity.badRequest().body("El proyecto no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(proyecto);
    }

    // Listar todos los Proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> listar() {
        List<Proyecto> proyectos = proyectoService.findAll();
        return ResponseEntity.ok(proyectos);
    }

    // Crear un nuevo Proyecto
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Proyecto proyecto) {
        try {
            if (proyecto.getId_parroquia() == null) {
                throw new Exception("Debe seleccionar una Parroquia.");
            }
            proyectoService.save(proyecto);
            return ResponseEntity.ok("El registro del proyecto se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el proyecto: " + e.getMessage());
        }
    }

    // Editar un Proyecto
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        Proyecto existente = proyectoService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id del proyecto no existe en la base de datos.");
        }

        proyecto.setId_proyecto(id); // Asignar el ID para la actualización
        proyectoService.save(proyecto);

        return ResponseEntity.ok("El registro del proyecto se ha editado con éxito.");
    }

    // Eliminar un Proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            proyectoService.delete(id);
            return ResponseEntity.ok("El proyecto ha sido eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar el proyecto porque está siendo referenciado por otras entidades."
            );
        }
    }
}
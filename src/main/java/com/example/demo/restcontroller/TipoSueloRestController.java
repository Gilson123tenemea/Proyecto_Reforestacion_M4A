package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.ITipo_SueloService;

import java.util.List;

@RestController
@RequestMapping("/api/tipo_suelo")
public class TipoSueloRestController {

    @Autowired
    private ITipo_SueloService tipoSueloService;

    // Obtener un Tipo de Suelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Tipo_Suelo tipoSuelo = tipoSueloService.findOne(id);

        if (tipoSuelo == null) {
            return ResponseEntity.badRequest().body("El tipo de suelo no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(tipoSuelo);
    }

    // Listar todos los Tipos de Suelo
    @GetMapping
    public ResponseEntity<List<Tipo_Suelo>> listar() {
        List<Tipo_Suelo> suelos = tipoSueloService.findAll();
        return ResponseEntity.ok(suelos);
    }

    // Crear un nuevo Tipo de Suelo
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Tipo_Suelo tipoSuelo) {
        tipoSueloService.save(tipoSuelo);
        return ResponseEntity.ok("El registro del tipo de suelo se ha creado con éxito.");
    }

    // Editar un Tipo de Suelo
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Tipo_Suelo tipoSuelo) {
        Tipo_Suelo existente = tipoSueloService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id del tipo de suelo no existe en la base de datos.");
        }

        tipoSuelo.setId_tiposuelo(id); // Asignar el ID para la actualización
        tipoSueloService.save(tipoSuelo);

        return ResponseEntity.ok("El registro del tipo de suelo se ha editado con éxito.");
    }

    // Eliminar un Tipo de Suelo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tipoSueloService.delete(id);
            return ResponseEntity.ok("El tipo de suelo ha sido eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar el tipo de suelo porque está siendo referenciado por otras entidades."
            );
        }
    }
}
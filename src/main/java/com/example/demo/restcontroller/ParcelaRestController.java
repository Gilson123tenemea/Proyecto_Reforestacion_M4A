package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Parcelas;
import com.example.demo.service.IParcelaService;

import java.util.List;

@RestController
@RequestMapping("/api/parcela")
public class ParcelaRestController {

    @Autowired
    private IParcelaService parcelaService;

    // Obtener una Parcela por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Parcelas parcela = parcelaService.findOne(id);

        if (parcela == null) {
            return ResponseEntity.badRequest().body("La parcela no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(parcela);
    }

    // Listar todas las Parcelas
    @GetMapping
    public ResponseEntity<List<Parcelas>> listar() {
        List<Parcelas> parcelas = parcelaService.findAll();
        return ResponseEntity.ok(parcelas);
    }

    // Crear una nueva Parcela
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Parcelas parcela) {
        try {
            parcelaService.save(parcela);
            return ResponseEntity.ok("El registro de la parcela se ha creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la parcela: " + e.getMessage());
        }
    }

    // Editar una Parcela
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Parcelas parcela) {
        Parcelas existente = parcelaService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id de la parcela no existe en la base de datos.");
        }

        parcela.setId_parcelas(id); // Asignar el ID para la actualización
        parcelaService.save(parcela);

        return ResponseEntity.ok("El registro de la parcela se ha editado con éxito.");
    }

    // Eliminar una Parcela
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            parcelaService.delete(id);
            return ResponseEntity.ok("La parcela ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar la parcela porque está siendo referenciada por otras entidades."
            );
        }
    }
}
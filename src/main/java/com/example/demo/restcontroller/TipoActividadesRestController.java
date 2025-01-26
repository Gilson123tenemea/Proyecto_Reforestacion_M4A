package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.service.ITipo_ActividadesService;

import java.util.List;

@RestController
@RequestMapping("/api/tipo_actividades")
public class TipoActividadesRestController {

    @Autowired
    private ITipo_ActividadesService tipoActividadesService;

   
    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Tipo_Actividades tactividad = tipoActividadesService.findOne(id);

        if (tactividad == null) {
            return ResponseEntity.badRequest().body("La actividad no se encuentra en la base de datos.");
        }

        return ResponseEntity.ok(tactividad);
    }

    
    @GetMapping
    public ResponseEntity<List<Tipo_Actividades>> listar() {
        List<Tipo_Actividades> actividades = tipoActividadesService.findAll();
        return ResponseEntity.ok(actividades);
    }

   
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Tipo_Actividades tactividad) {
        tipoActividadesService.save(tactividad);
        return ResponseEntity.ok("El registro de tipo de actividad se ha creado con éxito.");
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Tipo_Actividades tactividad) {
        Tipo_Actividades existente = tipoActividadesService.findOne(id);

        if (existente == null) {
            return ResponseEntity.badRequest().body("El Id del tipo de actividad no existe en la base de datos.");
        }

        tactividad.setId_tipoActividades(id); 
        tipoActividadesService.save(tactividad);

        return ResponseEntity.ok("El registro del tipo de actividades se ha editado con éxito.");
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tipoActividadesService.delete(id);
            return ResponseEntity.ok("La actividad ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                "No se puede eliminar la actividad porque está siendo referenciada por otras entidades."
            );
        }
    }
}


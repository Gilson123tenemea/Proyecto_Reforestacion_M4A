package com.example.demo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Intervencion_Suelo;
import com.example.demo.entity.Patrocinio;
import com.example.demo.service.IIntervencionSueloServices;
import com.example.demo.service.IPatrocinioService;


@RestController
@RequestMapping("/api/intervencion")
public class IntervencionSueloRestController {


	  @Autowired
	    private IIntervencionSueloServices IntervencionService;

	    @GetMapping("/{id}")
	    public ResponseEntity<?> ver(@PathVariable Long id) {
	        Intervencion_Suelo intervencion = IntervencionService.findOne(id);

	        if (intervencion == null) {
	            return ResponseEntity.badRequest().body("Intervencion no se encuentra en la base de datos.");
	        }

	        return ResponseEntity.ok(intervencion);
	    }

	    @GetMapping
	    public ResponseEntity<List<Intervencion_Suelo>> listar() {
	        List<Intervencion_Suelo> intervenciones = IntervencionService.findAll();
	        return ResponseEntity.ok(intervenciones);
	    }

	    @PostMapping
	    public ResponseEntity<?> crear(@RequestBody Intervencion_Suelo intervencion) {
	        try {
	            
	        	IntervencionService.save(intervencion);
	            return ResponseEntity.ok("Intervencion creada con éxito.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error al crear la intervencion: " + e.getMessage());
	        }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Intervencion_Suelo intervencion) {
	        Intervencion_Suelo existente = IntervencionService.findOne(id);

	        if (existente == null) {
	            return ResponseEntity.badRequest().body("Intervencion no existe en la base de datos.");
	        }

	        intervencion.setId_intervencion_suelo(id); // Asignar el ID para la actualización
	        IntervencionService.save(intervencion);

	        return ResponseEntity.ok("La intervencion se ha editado con éxito.");
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> eliminar(@PathVariable Long id) {
	        try {
	            IntervencionService.delete(id);
	            return ResponseEntity.ok("La intervencion ha sido eliminado correctamente.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(
	                "No se puede eliminar la intervencion porque está siendo referenciado por otras entidades."
	            );
	        }
	    }
}

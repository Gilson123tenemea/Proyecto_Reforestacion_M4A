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

import com.example.demo.entity.Patrocinio;
import com.example.demo.service.IPatrocinioService;

@RestController
@RequestMapping("/api/patrocinio")
public class PatrocinioRestController {
	
	  @Autowired
	    private IPatrocinioService patrociniosService;

	    // Obtener un Patrocinio por ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> ver(@PathVariable Long id) {
	        Patrocinio patrocinio = patrociniosService.findOne(id);

	        if (patrocinio == null) {
	            return ResponseEntity.badRequest().body("El patrocinio no se encuentra en la base de datos.");
	        }

	        return ResponseEntity.ok(patrocinio);
	    }

	    // Listar todos los Patrocinios
	    @GetMapping
	    public ResponseEntity<List<Patrocinio>> listar() {
	        List<Patrocinio> patrocinios = patrociniosService.findAll();
	        return ResponseEntity.ok(patrocinios);
	    }

	    // Crear un nuevo Patrocinio
	    @PostMapping
	    public ResponseEntity<?> crear(@RequestBody Patrocinio patrocinio) {
	        try {
	            
	            patrociniosService.save(patrocinio);
	            return ResponseEntity.ok("El patrocinio se ha creado con éxito.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error al crear el patrocinio: " + e.getMessage());
	        }
	    }

	    // Editar un Patrocinio
	    @PutMapping("/{id}")
	    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Patrocinio patrocinio) {
	        Patrocinio existente = patrociniosService.findOne(id);

	        if (existente == null) {
	            return ResponseEntity.badRequest().body("El Id del patrocinio no existe en la base de datos.");
	        }

	        patrocinio.setId_patrocina(id); // Asignar el ID para la actualización
	        patrociniosService.save(patrocinio);

	        return ResponseEntity.ok("El patrocinio se ha editado con éxito.");
	    }

	    // Eliminar un Patrocinio
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> eliminar(@PathVariable Long id) {
	        try {
	            patrociniosService.delete(id);
	            return ResponseEntity.ok("El patrocinio ha sido eliminado correctamente.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(
	                "No se puede eliminar el patrocinio porque está siendo referenciado por otras entidades."
	            );
	        }
	    }

}

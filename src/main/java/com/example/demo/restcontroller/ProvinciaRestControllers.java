package com.example.demo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Provincia;
import com.example.demo.service.IProvinciaService;

@RestController
@RequestMapping("/api/provincias")
@CrossOrigin(origins = "http://localhost:8092")  // Permitir el origen de tu cliente

public class ProvinciaRestControllers {
	
    @Autowired
    private IProvinciaService provinciaService;

    // Listar Provincias
    @GetMapping
    public ResponseEntity<List<Provincia>> listarProvincias() {
        List<Provincia> provincias = provinciaService.findAll();
        return new ResponseEntity<>(provincias, HttpStatus.OK);
    }

    // Obtener una Provincia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Provincia> obtenerProvincia(@PathVariable Long id) {
        Provincia provincia = provinciaService.findOne(id);
        if (provincia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provincia, HttpStatus.OK);
    }

    // Crear o actualizar una Provincia
    @PostMapping
    public ResponseEntity<String> guardarProvincia(@RequestBody Provincia provincia) {
        try {
            // Guardar o actualizar la provincia
            provinciaService.save(provincia);
            return new ResponseEntity<>("Provincia guardada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar una Provincia
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProvincia(@PathVariable Long id) {
        try {
            Provincia provincia = provinciaService.findOne(id);
            if (provincia == null) {
                return new ResponseEntity<>("Provincia no encontrada", HttpStatus.NOT_FOUND);
            }
            provinciaService.delete(id);
            return new ResponseEntity<>("Provincia eliminada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // Editar una Provincia
    @PutMapping("/{id}")
    public ResponseEntity<String> editarProvincia(@PathVariable Long id, @RequestBody Provincia provincia) {
        try {
            // Verificar si la provincia existe
            Provincia provinciaExistente = provinciaService.findOne(id);
            if (provinciaExistente == null) {
                return new ResponseEntity<>("Provincia no encontrada", HttpStatus.NOT_FOUND);
            }

            // Asignar los nuevos valores a la provincia existente
            provinciaExistente.setNombreProvincia(provincia.getNombreProvincia());  // O cualquier otro campo que desees actualizar
            provinciaExistente.setCanton(provincia.getCanton());  // Actualizar canton o cualquier otro campo

            // Guardar los cambios
            provinciaService.save(provinciaExistente);

            return new ResponseEntity<>("Provincia actualizada exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al editar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


package com.example.demo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Tipo_Suelo;
import com.example.demo.service.ITipo_SueloService;

@RestController
@RequestMapping("/api/tiposuelos")
public class TipoSueloRestController {
    
    @Autowired
    private ITipo_SueloService tipoSueloService;

    // Listar Tipos de Suelo
    @GetMapping
    public ResponseEntity<List<Tipo_Suelo>> listarTiposSuelo() {
        List<Tipo_Suelo> tiposSuelo = tipoSueloService.findAll();
        return new ResponseEntity<>(tiposSuelo, HttpStatus.OK);
    }

    // Obtener un Tipo de Suelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tipo_Suelo> obtenerTipoSuelo(@PathVariable Long id) {
        Tipo_Suelo tipoSuelo = tipoSueloService.findOne(id);
        if (tipoSuelo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tipoSuelo, HttpStatus.OK);
    }

    // Crear o actualizar un Tipo de Suelo
    @PostMapping
    public ResponseEntity<String> guardarTipoSuelo(@RequestBody Tipo_Suelo tipoSuelo) {
        try {
            tipoSueloService.save(tipoSuelo);
            return new ResponseEntity<>("Tipo de Suelo guardado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un Tipo de Suelo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipoSuelo(@PathVariable Long id) {
        try {
            Tipo_Suelo tipoSuelo = tipoSueloService.findOne(id);
            if (tipoSuelo == null) {
                return new ResponseEntity<>("Tipo de Suelo no encontrado", HttpStatus.NOT_FOUND);
            }
            tipoSueloService.delete(id);
            return new ResponseEntity<>("Tipo de Suelo eliminado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
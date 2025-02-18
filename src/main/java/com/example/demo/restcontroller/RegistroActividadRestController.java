package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.service.RegistroActividadRealizadaService;
import com.example.demo.dao.IRegistroActividadRealizadaDao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registro_actividad")
public class RegistroActividadRestController {

    @Autowired
    private RegistroActividadRealizadaService registroActividadService;

    @Autowired
    private IRegistroActividadRealizadaDao iregistroActividadRealizadaDao;

    @GetMapping("/voluntario/{voluntarioId}")
    public ResponseEntity<List<Object[]>> listarActividadesPorVoluntario(@PathVariable Long voluntarioId) {
        List<Object[]> actividades = iregistroActividadRealizadaDao.findActividadesRealizadas(voluntarioId);
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verActividad(@PathVariable Long id) {
        Optional<RegistroActividadRealiza> actividad = registroActividadService.findOne(id);
        if (actividad == null) {
            return ResponseEntity.badRequest().body("La actividad no se encuentra en la base de datos.");
        }
        return ResponseEntity.ok(actividad);
    }

    @PostMapping
    public ResponseEntity<?> crearActividad(
            @RequestParam(value ="id_tipo_actividad") Long idActividad,
            @RequestParam(value = "id_voluntario") Long idVoluntario,
            @RequestParam("cantidad_realizada") Integer cantidadRealizada,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "validacion_admin_tareaRealizada", defaultValue = "false") boolean validacionAdmin,
            @RequestParam(value = "validacion_voluntario_tareaRealizada", defaultValue = "false") boolean validacionVoluntario,
            @RequestParam(value = "foto", required = false) MultipartFile file) {

        try {
            RegistroActividadRealiza registroActividad = new RegistroActividadRealiza();
            registroActividad.setId_voluntario(idVoluntario);
            registroActividad.setId_tipoActividades(idActividad);
            registroActividad.setCantidad_realizada(cantidadRealizada);
            registroActividad.setDescripcion(descripcion);
            registroActividad.setValidacion_admin_tareaRealizada(validacionAdmin);
            registroActividad.setValidacion_voluntario_tareaRealizada(validacionVoluntario);

            if (file != null && !file.isEmpty()) {
                registroActividad.setFoto(file.getBytes());
            }

            registroActividadService.save(registroActividad);
            return ResponseEntity.ok("Actividad guardada con éxito.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir la imagen.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocurrió un error inesperado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarActividad(
            @PathVariable Long id,
            @RequestParam("cantidad_realizada") Integer cantidadRealizada,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        Optional<RegistroActividadRealiza> actividadOpt = registroActividadService.findOne(id);
        if (!actividadOpt.isPresent()) {
            return ResponseEntity.badRequest().body("El Id de la actividad no existe en la base de datos.");
        }

        RegistroActividadRealiza actividad = actividadOpt.get();
        actividad.setCantidad_realizada(cantidadRealizada);
        actividad.setDescripcion(descripcion);

        if (foto != null && !foto.isEmpty()) {
            try {
                actividad.setFoto(foto.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error al procesar la imagen.");
            }
        }

        registroActividadService.save(actividad);
        return ResponseEntity.ok("Actividad actualizada correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarActividad(@PathVariable Long id) {
        try {
            registroActividadService.deleteById(id);
            return ResponseEntity.ok("La actividad ha sido eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se puede eliminar la actividad porque está siendo referenciada por otras entidades.");
        }
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<RegistroActividadRealiza> registroOpt = iregistroActividadRealizadaDao.findOne(id);
        if (registroOpt.isPresent() && registroOpt.get().getFoto() != null) {
            return ResponseEntity.ok(registroOpt.get().getFoto());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/todas")
    public ResponseEntity<List<RegistroActividadRealiza>> listarTodasLasActividades() {
        List<RegistroActividadRealiza> actividades = registroActividadService.findAll();
        return ResponseEntity.ok(actividades);
    }

}
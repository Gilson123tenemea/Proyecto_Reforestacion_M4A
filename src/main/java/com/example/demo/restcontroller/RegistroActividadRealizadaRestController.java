package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.service.RegistroActividadRealizadaService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/registro-actividad")
public class RegistroActividadRealizadaRestController {

	@Autowired
	private RegistroActividadRealizadaService registroActividadService;

	// Guardar una nueva actividad
	@PostMapping("/guardar")
	public ResponseEntity<String> guardarActividad(@RequestParam Long id_tipo_actividad,
			@RequestParam Long idVoluntario, @RequestParam Integer cantidad_realizada, @RequestParam String descripcion,
			@RequestParam(value = "validacion_admin_tareaRealizada", defaultValue = "false") boolean validacionAdmin,
			@RequestParam(value = "validacion_voluntario_tareaRealizada", defaultValue = "false") boolean validacionVoluntario,
			@RequestParam(value = "foto", required = false) MultipartFile file) {

		try {
			RegistroActividadRealiza actividad = new RegistroActividadRealiza();
			actividad.setId_voluntario(idVoluntario);
			actividad.setId_tipoActividades(id_tipo_actividad);
			actividad.setCantidad_realizada(cantidad_realizada);
			actividad.setDescripcion(descripcion);
			actividad.setValidacion_admin_tareaRealizada(validacionAdmin);
			actividad.setValidacion_voluntario_tareaRealizada(validacionVoluntario);

			if (file != null && !file.isEmpty()) {
				actividad.setFoto(file.getBytes());
			}

			registroActividadService.save(actividad);
			return ResponseEntity.status(HttpStatus.CREATED).body("Actividad guardada con éxito");

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
		}
	}

	// Obtener una actividad específica
	@GetMapping("/detalle/{id}")
	public ResponseEntity<RegistroActividadRealiza> obtenerActividad(@PathVariable Long id) {
		Optional<RegistroActividadRealiza> actividad = registroActividadService.findOne(id);
		return actividad.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	/////////////////////////////////////////////////// SIRVEN
	/////////////////////////////////////////////////// ///////////////////////////////////////////////////
	// Actualizar actividad
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizarActividad(@PathVariable Long id, @RequestParam Integer cantidad_realizada,
			@RequestParam String descripcion, @RequestParam(value = "foto", required = false) MultipartFile file) {

		Optional<RegistroActividadRealiza> actividadOpt = registroActividadService.findOne(id);
		if (!actividadOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actividad no encontrada");
		}

		RegistroActividadRealiza actividad = actividadOpt.get();
		actividad.setCantidad_realizada(cantidad_realizada);
		actividad.setDescripcion(descripcion);

		try {
			if (file != null && !file.isEmpty()) {
				actividad.setFoto(file.getBytes());
			}
			registroActividadService.save(actividad);
			return ResponseEntity.ok("Actividad actualizada correctamente");

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
		}
	}

	// Obtener imagen de una actividad
	@GetMapping("/imagen/{id}")
	public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
		Optional<RegistroActividadRealiza> actividadOpt = registroActividadService.findOne(id);
		if (actividadOpt.isPresent() && actividadOpt.get().getFoto() != null) {
			return ResponseEntity.ok().body(actividadOpt.get().getFoto());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// Obtener actividades realizadas por voluntario
	@GetMapping("/{voluntarioId}")
	public ResponseEntity<List<Object[]>> obtenerActividadesPorVoluntario(@PathVariable Long voluntarioId) {
		List<Object[]> actividades = registroActividadService.findActividadesRealizadas2(voluntarioId);
		if (actividades.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(actividades);
	}
}

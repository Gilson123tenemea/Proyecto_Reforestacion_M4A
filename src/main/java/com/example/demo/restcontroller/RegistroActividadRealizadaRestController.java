package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ActividadARealizarDTO;
import com.example.demo.entity.Area;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.service.IVoluntariosService;
import com.example.demo.service.RegistroActividadRealizadaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registro-actividad")
public class RegistroActividadRealizadaRestController {

	@Autowired
	private RegistroActividadRealizadaService registroActividadService;
	
	@Autowired
	private IVoluntariosService voluntariosService;

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
	////////////////////////////////////////////////////////////////////////////////////////////////////
	

	@GetMapping("/crear/{id_actividad}/{id_voluntario}")
	public ResponseEntity<?> mostrarActividad(
	        @PathVariable("id_actividad") Long idactividad,
	        @PathVariable("id_voluntario") Long voluntarioId) {

	    System.out.println("📢 Método mostrarActividad iniciado");
	    System.out.println("📢 Id Actividad ======================" + idactividad);
	    System.out.println("📌 ID del voluntario recibido: " + voluntarioId);

	    // Obtener los resultados de la actividad
	    List<Object[]> resultados = registroActividadService.findActividadesRealizadas2(voluntarioId);
	    System.out.println("🔍 Resultados obtenidos: " + resultados.size());

	    if (resultados.isEmpty()) {
	        return ResponseEntity.status(404).body("⚠️ No se encontraron actividades realizadas para el voluntario con ID: " + voluntarioId);
	    }

	    // Usar un Map para almacenar los datos dinámicamente
	    Map<String, Object> responseData = new HashMap<>();
	    Object[] datos = resultados.get(0);
	    
	    responseData.put("voluntarioNombre", datos[4].toString());  // Voluntario Nombre
	    responseData.put("actividadNombre", datos[0].toString());   // Actividad Nombre
	    responseData.put("actividadDuracion",  datos[1]);  // Actividad Duración
	    responseData.put("proyectoNombre", datos[2].toString());    // Proyecto Nombre
	    responseData.put("equipoNombre", datos[3].toString()); 
	    responseData.put("Id_tipoProyecto", datos[6].toString()); 
	    // Equipo Nombre

	    // Si quieres almacenar más información, solo añádela al Map

	    return ResponseEntity.ok(responseData);  // Retorna los datos en formato JSON
	}
	
	/*
	 @PostMapping("/guardarActividad")
	    public ResponseEntity<String> guardarActividad(@RequestBody ActividadARealizarDTO actividadDTO) {
	        try {
	            // Procesamos los datos recibidos del cliente (móvil)
	            RegistroActividadRealiza registroActividad = new RegistroActividadRealiza();
	            registroActividad.setId_voluntario(actividadDTO.getIdVoluntario());
	            registroActividad.setId_tipoActividades(actividadDTO.getIdTipoActividad());
	            registroActividad.setCantidad_realizada(actividadDTO.getCantidadRealizada());
	            registroActividad.setDescripcion(actividadDTO.getDescripcion());
	            registroActividad.setValidacion_admin_tareaRealizada(actividadDTO.isValidacionAdmin());
	            registroActividad.setValidacion_voluntario_tareaRealizada(actividadDTO.isValidacionVoluntario());

	            // Si hay foto en Base64, la decodificamos
	            if (actividadDTO.getFoto() != null && !actividadDTO.getFoto().isEmpty()) {
	                byte[] fotoBytes = Base64.getDecoder().decode(actividadDTO.getFoto());
	                registroActividad.setFoto(fotoBytes); // Guardamos la foto como bytes
	            }

	            // Guardamos la actividad en la base de datos
	            registroActividadService.save(registroActividad);

	            // Retornamos un mensaje de éxito
	            return ResponseEntity.ok("Actividad guardada exitosamente");

	        } catch (Exception e) {
	            e.printStackTrace();
	            // Si ocurre algún error, devolvemos un mensaje de error
	            return ResponseEntity.status(500).body("Error al guardar la actividad");
	        }
	    }
	*/
}

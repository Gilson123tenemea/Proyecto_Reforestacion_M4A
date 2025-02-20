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
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IUsuarioServices;
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

	@Autowired
	private IUsuarioServices usuarioService;

	@PostMapping("/guardarActividad/{id_actividad}/{id_voluntario}")
	public ResponseEntity<String> guardarActividad(@PathVariable("id_actividad") Long idActividad,
			@PathVariable("id_voluntario") Long idVoluntario, @RequestBody ActividadARealizarDTO actividadDTO) {
		try {
			// Crear un nuevo registro de actividad
			RegistroActividadRealiza registroActividad = new RegistroActividadRealiza();
			registroActividad.setId_voluntario(idVoluntario);
			registroActividad.setId_tipoActividades(idActividad);
			registroActividad.setCantidad_realizada(actividadDTO.getCantidadRealizada());
			registroActividad.setDescripcion(actividadDTO.getDescripcion());
			registroActividad.setValidacion_admin_tareaRealizada(actividadDTO.isValidacionAdmin());
			registroActividad.setValidacion_voluntario_tareaRealizada(actividadDTO.isValidacionVoluntario());

			// Procesar la foto si existe
			if (actividadDTO.getFoto() != null && !actividadDTO.getFoto().isEmpty()) {
				byte[] fotoBytes = Base64.getDecoder().decode(actividadDTO.getFoto());
				registroActividad.setFoto(fotoBytes);
			}

			// Guardar el registro
			registroActividadService.save(registroActividad);
			return ResponseEntity.status(HttpStatus.CREATED).body("Actividad guardada con éxito");

		} catch (Exception e) {
			e.printStackTrace(); // Si ocurre algún error, lo logueamos
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la actividad");
		}
	}

	@GetMapping("/crear/{id_actividad}/{id_voluntario}")
	public ResponseEntity<?> mostrarActividad(@PathVariable("id_actividad") Long idactividad,
			@PathVariable("id_voluntario") Long voluntarioId) {
		System.out.println("📢 Método mostrarActividad iniciado");
		System.out.println("📢 Id Actividad ======================" + idactividad);
		System.out.println("📌 ID del voluntario recibido: " + voluntarioId);

		// Obtener los resultados de la actividad
		List<Object[]> resultados = registroActividadService.findActividadesRealizadas2(voluntarioId);
		System.out.println("🔍 Resultados obtenidos: " + resultados.size());

		if (resultados.isEmpty()) {
			return ResponseEntity.status(404)
					.body("⚠️ No se encontraron actividades realizadas para el voluntario con ID: " + voluntarioId);
		}

		// Usar un Map para almacenar los datos dinámicamente
		Map<String, Object> responseData = new HashMap<>();
		Object[] datos = resultados.get(0);

		responseData.put("voluntarioNombre", datos[4].toString()); // Voluntario Nombre
		responseData.put("actividadNombre", datos[0].toString()); // Actividad Nomguardar
		responseData.put("actividadDuracion", datos[1]); // Actividad Duración
		responseData.put("proyectoNombre", datos[2].toString()); // Proyecto Nombre
		responseData.put("equipoNombre", datos[3].toString());
		responseData.put("Id_tipoProyecto", datos[6].toString());
		// Equipo Nombre

		// Si quieres almacenar más información, solo añádela al Map

		return ResponseEntity.ok(responseData); // Retorna los datos en formato JSON
	}

	// Obtener una actividad específica
	@GetMapping("/detalle/{id}")
	public ResponseEntity<RegistroActividadRealiza> obtenerActivizdad(@PathVariable Long id) {
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

	@GetMapping("/Info_Actividad/{id_actividad}/{id_voluntario}")
	public ResponseEntity<?> mostrarActividadInfo(@PathVariable("id_actividad") Long idactividad,
			@PathVariable("id_voluntario") Long voluntarioId) {

		System.out.println("📢 Método mostrarActividad iniciado");
		System.out.println("📢 Id Actividad ======================" + idactividad);
		System.out.println("📌 ID del voluntario recibido: " + voluntarioId);

		// Obtener los resultados de la actividad
		List<Object[]> resultados = registroActividadService.findActividadesRealizadas2(voluntarioId);
		System.out.println("🔍 Resultados obtenidos: " + resultados.size());

		if (resultados.isEmpty()) {
			return ResponseEntity.status(404)
					.body("⚠️ No se encontraron actividades realizadas para el voluntario con ID: " + voluntarioId);
		}

		// Usar un Map para almacenar los datos dinámicamente
		Map<String, Object> responseData = new HashMap<>();
		Object[] datos = resultados.get(0);

		responseData.put("voluntarioNombre", datos[4] != null ? datos[4].toString() : "");
		responseData.put("actividadNombre", datos[0] != null ? datos[0].toString() : "");
		responseData.put("actividadDuracion", datos[1] != null ? datos[1] : "");
		responseData.put("proyectoNombre", datos[2] != null ? datos[2].toString() : "");
		responseData.put("equipoNombre", datos[3] != null ? datos[3].toString() : "");
		responseData.put("Id_tipoProyecto", datos[6] != null ? datos[6].toString() : "");

		responseData.put("fecha_inicio", datos[7] != null ? datos[7].toString() : "");
		responseData.put("nombreEmpresa", datos[8] != null ? datos[8].toString() : "");
		responseData.put("tipo_patrocinio", datos[9] != null ? datos[9].toString() : "");
		responseData.put("detalledpnado", datos[10] != null ? datos[10].toString() : "");

		// Validar si datos[11] es null antes de hacer la consulta
		String nombre = "";
		if (datos[11] != null) {
			Usuarios usuario = usuarioService.findOne((Long) datos[11]);
			if (usuario != null) {
				nombre = usuario.getNombre() + " " + usuario.getApellido();
			}
		}

		responseData.put("NombrePatrocinador", nombre);

		return ResponseEntity.ok(responseData);
	}


}

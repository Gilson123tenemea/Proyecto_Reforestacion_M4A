package com.example.demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ProyectoDTO;
import com.example.demo.entity.Area;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.IParcelaService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.serviceMovil.IProyectoServiceMovil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProyectoRestControllers {
	@Autowired
	private IProyectoServiceMovil proyectoServiceMovil;

	@Autowired
	private IProyectoServices proyectoService;
	@Autowired
    private IAreaServices areaService;
	
	@Autowired
    private IParcelaService parcelaService;
	
	// Obtener un Proyecto por ID
	@GetMapping("/proyectos/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Proyecto proyecto = proyectoService.findOne(id);

		if (proyecto == null) {
			return ResponseEntity.badRequest().body("El proyecto no se encuentra en la base de datos.");
		}

		return ResponseEntity.ok(proyecto);
	}
	
	// Obtener proyectos con paginación y datos reducidos
	 @GetMapping("/movil/proyectos")
	    public ResponseEntity<List<ProyectoDTO>> listarProyectosMovil(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {

	        List<ProyectoDTO> proyectos = proyectoServiceMovil.findAllPaged(page, size)
	                .stream()
	                .map(proyecto -> new ProyectoDTO(
	                        proyecto.getId_proyecto(),
	                        proyecto.getNombre(),
	                        proyecto.getFecha_inicio(),
	                        proyecto.getFecha_fin(),
	                        proyecto.getVoluntariosmax()
	                ))
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(proyectos);
	    }

	// Listar todos los Proyectos
	@GetMapping("/proyectos")
	public ResponseEntity<List<Proyecto>> listar() {
		List<Proyecto> proyectos = proyectoService.findAll();
		return ResponseEntity.ok(proyectos);
	}

	// Crear un nuevo Proyecto
	@PostMapping("proyectos")
	public ResponseEntity<?> crear(@RequestBody Proyecto proyecto) {
		try {
			if (proyecto.getId_parroquia() == null) {
				throw new Exception("Debe seleccionar una Parroquia.");
			}
			proyectoService.save(proyecto);
			return ResponseEntity.ok("El registro del proyecto se ha creado con éxito.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error al guardar el proyecto: " + e.getMessage());
		}
	}

	// Editar un Proyecto
	@PutMapping("/proyectos/{id}")
	public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Proyecto proyecto) {
		Proyecto existente = proyectoService.findOne(id);

		if (existente == null) {
			return ResponseEntity.badRequest().body("El Id del proyecto no existe en la base de datos.");
		}

		proyecto.setId_proyecto(id); // Asignar el ID para la actualización
		proyectoService.save(proyecto);

		return ResponseEntity.ok("El registro del proyecto se ha editado con éxito.");
	}

	// Eliminar un Proyecto
	@DeleteMapping("/proyectos/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		try {
			proyectoService.delete(id);
			return ResponseEntity.ok("El proyecto ha sido eliminado correctamente.");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body("No se puede eliminar el proyecto porque está siendo referenciado por otras entidades.");
		}
	}
	// asociar------------------------------
	
		@PostMapping("/proyectos/asociar")
		public ResponseEntity<String> asociarProyecto(@RequestParam Long idProyecto, 
		                                              @RequestParam Long idArea, 
		                                              @RequestParam Long idParcela) {
		    try {
		        Proyecto proyecto = proyectoService.findOne(idProyecto);
		        if (proyecto == null) {
		            return ResponseEntity.badRequest().body("El proyecto no existe.");
		        }

		        Area area = areaService.findOne(idArea);
		        if (area == null || !area.getId_proyecto().equals(idProyecto)) {
		            return ResponseEntity.badRequest().body("El área no existe o no pertenece al proyecto.");
		        }

		        Parcelas parcela = parcelaService.findOne(idParcela);
		        if (parcela == null || !parcela.getId_area().equals(idArea)) {
		            return ResponseEntity.badRequest().body("La parcela no existe o no pertenece al área.");
		        }

		        return ResponseEntity.ok("Asociación correcta: Proyecto " + idProyecto + 
		                                 " -> Área " + idArea + " -> Parcela " + idParcela);
		    } catch (Exception e) {
		        return ResponseEntity.badRequest().body("Error al asociar: " + e.getMessage());
		    }
		}

		// obtener proyecto y parcelas
		
		@GetMapping("/proyectos/{idProyecto}/parcelas/{idParcela}")
		public ResponseEntity<?> obtenerParcelaConProyecto(@PathVariable Long idProyecto, @PathVariable Long idParcela) {
		    try {
		        // Buscar el proyecto por su ID
		        Proyecto proyecto = proyectoService.findOne(idProyecto);
		        if (proyecto == null) {
		            return ResponseEntity.badRequest().body("El proyecto no se encuentra en la base de datos.");
		        }

		        // Buscar la parcela por su ID
		        Parcelas parcela = parcelaService.findOne(idParcela);
		        if (parcela == null) {
		            return ResponseEntity.badRequest().body("La parcela no se encuentra en la base de datos.");
		        }

		        // Verificar si la parcela pertenece al proyecto
		        Area area = areaService.findOne(parcela.getId_area());
		        if (area == null || !area.getId_proyecto().equals(idProyecto)) {
		            return ResponseEntity.badRequest().body("La parcela no pertenece al proyecto.");
		        }

		        // Crear la respuesta que contiene la información del proyecto y la parcela
		        Map<String, Object> respuesta = new HashMap<>();
		        respuesta.put("proyecto", proyecto);
		        respuesta.put("parcela", parcela);
		        respuesta.put("monitoreo", parcela.getMonitoreo());  // Detalles de monitoreo relacionados
		        respuesta.put("intervencion_suelo", parcela.getEquipos());  // Detalles de intervenciones relacionadas

		        // Retornar la respuesta con todos los detalles
		        return ResponseEntity.ok(respuesta);
		    } catch (Exception e) {
		        return ResponseEntity.badRequest().body("Error al obtener la parcela y proyecto: " + e.getMessage());
		    }
		}
}
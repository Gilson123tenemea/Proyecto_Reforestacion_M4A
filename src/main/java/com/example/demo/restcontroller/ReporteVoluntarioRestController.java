package com.example.demo.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.IProyectoServices;

@RestController
@RequestMapping("/api")
public class ReporteVoluntarioRestController {
	
	@Autowired
    IProyectoServices proyectoService;

	@GetMapping("/MostrarTodosestados")
	public Map<String, Object> MostrarEstadoProyecto() {
	    Map<String, Object> response = new HashMap<>();

	    List<Proyecto> proyectosActivos = proyectoService.findactivos();
	    List<Proyecto> proyectosInactivos = proyectoService.findInactivos();
	    List<Proyecto> proyectosFinalizados = proyectoService.findFinalizados();

	    List<Map<String, Object>> nombresProyectosActivos = proyectosActivos.stream()
	        .map(proyecto -> {
	            Map<String, Object> proyectoData = new HashMap<>();
	            proyectoData.put("nombre", proyecto.getNombre());
	            proyectoData.put("porcentaje", proyecto.getPorcentaje());
	            return proyectoData;
	        })
	        .collect(Collectors.toList());

	    List<Map<String, Object>> nombresProyectosInactivos = proyectosInactivos.stream()
	        .map(proyecto -> {
	            Map<String, Object> proyectoData = new HashMap<>();
	            proyectoData.put("nombre", proyecto.getNombre());
	            proyectoData.put("porcentaje", proyecto.getPorcentaje());
	            return proyectoData;
	        })
	        .collect(Collectors.toList());

	    List<Map<String, Object>> nombresProyectosFinalizados = proyectosFinalizados.stream()
	        .map(proyecto -> {
	            Map<String, Object> proyectoData = new HashMap<>();
	            proyectoData.put("nombre", proyecto.getNombre());
	            proyectoData.put("porcentaje", proyecto.getPorcentaje());
	            return proyectoData;
	        })
	        .collect(Collectors.toList());

	    response.put("nombresProyectosActivos", nombresProyectosActivos);
	    response.put("nombresProyectosInactivos", nombresProyectosInactivos);
	    response.put("nombresProyectosFinalizados", nombresProyectosFinalizados);

	    return response;
	}
}

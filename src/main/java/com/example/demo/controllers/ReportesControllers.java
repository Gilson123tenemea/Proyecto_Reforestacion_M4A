package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.IProyectoServices;

@Controller
public class ReportesControllers {
	
	@Autowired
	IProyectoServices proyectoService;
	
	@GetMapping("/MostrarUbicacion")
	public String MostrarMapa() {
		return "ReporteUbicaciones";
		
	}
	
	@GetMapping("/MostrarTodosestados")
	public String MostrarEstadoProyecto(Model model) {
		
		   model.addAttribute("nombresProyectosActivos", proyectoService.findactivos());
	        model.addAttribute("nombresProyectosInactivos", proyectoService.findInactivos());
	        model.addAttribute("nombresProyectosFinalizados", proyectoService.findFinalizados());
		
		return "ReporteProyectoestados";
		
	}
	

	@GetMapping("/MostrarVoluntarios")
	public String MostrarVouluntarios() {
		return "ReporteVoluntarios";
		
	}

}

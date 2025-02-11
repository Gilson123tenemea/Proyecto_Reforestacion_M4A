package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.IUsuarioServices;

@Controller
@SessionAttributes("idAdministrador")
public class ReportesControllers {
	
	@Autowired
	IProyectoServices proyectoService;
	@Autowired
	IUsuarioServices usuarioservice;
	
	@Autowired
    private EquiposServiceImpl equiposimpl;

	
    public Long id_administrador=null;

	
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
	

	  @GetMapping("/MostrarProyectos")
	    public String MostrarProyectosActivos(Model model) {
		  
	        Long idAdministrador = (Long) model.asMap().get("idAdministrador");
	        id_administrador=idAdministrador;
	        model.addAttribute("proyectos", equiposimpl.findAllProyectos(id_administrador));
	        
	      
	        return "ReporteVoluntarios";
	    }
	
	
	@PostMapping("/MostrarVoluntarios")
	public String MostrarVouluntarios(@RequestParam("idProyecto") Long id,Model model) {
		
		model.addAttribute("Integrantes",usuarioservice.NumeroUsuariosProvincia(id));
		
        model.addAttribute("proyectos", equiposimpl.findAllProyectos(id_administrador));

		return "ReporteVoluntarios";
		
	}

}

package com.example.demo.controllers;

import java.io.Console;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Especie;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IEquiposService;

@Controller
public class EquiposControllers {

	@Autowired
	private IEquiposService equiposService;
	
	@Autowired
	private EquiposServiceImpl equiposimpl;
	
	@GetMapping("/Combobox")
	public String MostrarProyectosActivos(Model model) {
		model.addAttribute("proyectos", equiposimpl.findAllProyectos());
		return "equipos";
	}
	
	
	@PostMapping("/Enviar")
	public String CargarTabla(@RequestParam("idProyecto") Long id, Model model) {
		
		model.addAttribute("proyectos", equiposimpl.findAllProyectos());
	    model.addAttribute("voluntarios", equiposimpl.listarVoluntariosPorProyecto(id));


		System.out.print(id);
	    // Aquí puedes usar el ID del proyecto para cargar los datos relacionados
	    
	    // Retorna la vista con los datos actualizados
	    return "equipos";
	}


	


	
}

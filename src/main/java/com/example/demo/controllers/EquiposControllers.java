package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IAsignacionEquiposService;
import com.example.demo.service.IEquiposService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SessionAttributes("idAdministrador")
public class EquiposControllers {
	
	   @Autowired
	    private IEquiposService equiposService;

	   @Autowired
	    private IAsignacionEquiposService AsignarService;
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
	        return "equipos";
	    }

	    @PostMapping("/guardarEquipos")
	    public String guardarEquipos(@RequestParam("nombreEquipo") String nombreEquipo, 
	                                 @RequestParam("equipos") String equiposJson, Asignar_equipos Asignarequipo) {

	        // Usamos ObjectMapper para convertir el JSON a un List
	    	// Long idAdministrador = (Long) model.asMap().get("idAdministrador");
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<Map<String, String>> equipos = null;
	        try {
	            equipos = objectMapper.readValue(equiposJson, List.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        Equipos Miequipo = new Equipos();
	        Miequipo.setNombre(nombreEquipo);
	        equiposService.save(Miequipo);

	        Date date = new Date();
	        for (Map<String, String> equipo : equipos) {
	            // Crear una nueva instancia de Asignar_equipos en cada iteración del bucle
	            Asignar_equipos nuevaAsignacion = new Asignar_equipos();
	            
	            nuevaAsignacion.setId_equipos(Miequipo.getId_equipos());
	            nuevaAsignacion.setFecha_asinacionequi(date);

	            String nombre = equipo.get("nombre");
	            String cedula = equipo.get("cedula");

	            List<Voluntarios> idvoluntarios = equiposService.ObtenerVoluntario(cedula);
	            for (Voluntarios volun : idvoluntarios) {
	                System.out.println(volun.getId_voluntario());
	                System.out.println(Miequipo.getId_equipos());

	                nuevaAsignacion.setId_voluntario(volun.getId_voluntario());
	                nuevaAsignacion.setId_equipos(Miequipo.getId_equipos());

	                System.out.println("Cédula: " + cedula + ", Nombre: " + nombre);
	                AsignarService.save(nuevaAsignacion);
	            }
	        }

	        return "equipos";  // Redirigir a la vista o mostrar un mensaje
	    }

	      
	        

	


	
}

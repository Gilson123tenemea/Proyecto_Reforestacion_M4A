package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imgscalr.Scalr.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.EquiposServiceImpl;
import com.example.demo.service.IAsignacionEquiposService;
import com.example.demo.service.IAsignacion_proyectoActiService;
import com.example.demo.service.IEquiposService;
import com.example.demo.service.ITipo_ActividadesService;
import com.example.demo.service.IUsuarioServices;
import com.example.demo.service.IVoluntariosService;
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
	    @Autowired
	    private IVoluntariosService voluntarioService;
	    
	    @Autowired
	    private ITipo_ActividadesService tipoService;
	    
	    @Autowired
	    private IAsignacion_proyectoActiService asignacionactividad;
	    
	    @Autowired
	    private IUsuarioServices usuarioServices;
	    
	    public Long Id_Proyecto=null;
	    public Long Id_actividades=null;
	    public Long id_administrador=null;
	    

	    @GetMapping("/Combobox")
	    public String MostrarProyectosActivos(Model model) {
	        Long idAdministrador = (Long) model.asMap().get("idAdministrador");
	        id_administrador=idAdministrador;
	        model.addAttribute("proyectos", equiposimpl.findAllProyectos(id_administrador));
	        
	      
	        return "equipos";
	    }

	    @PostMapping("/Enviar")
	    public String CargarTabla(@RequestParam("idProyecto") Long id, Model model) {
	        model.addAttribute("voluntarios", equiposimpl.listarVoluntariosPorProyecto(id));
	        model.addAttribute("actividades", equiposimpl.listarActividades(id));
	        Id_Proyecto=id;
	        
	        model.addAttribute("proyectos", equiposimpl.findAllProyectos(id_administrador));


	        return "equipos";
	    }

	    @PostMapping("/guardarEquipos")
	    public String guardarEquipos(@RequestParam("nombreEquipo") String nombreEquipo, 
	    		                     @RequestParam("actividad") Long actividadId,
	                                 @RequestParam("equipos") String equiposJson, Asignar_equipos Asignarequipo,Model model) {

	        // Usamos ObjectMapper para convertir el JSON a un List
	    	
	        ObjectMapper objectMapper = new ObjectMapper();
	        List<Map<String, String>> equipos = null;
	        try {
	            equipos = objectMapper.readValue(equiposJson, List.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        System.out.print("Actividad  "+actividadId);
	        Tipo_Actividades id_Asignacion=tipoService.findOne(actividadId);
	        System.out.print("Asinacion "+id_Asignacion.getId_tipoActividades()+"");
	        
	        Equipos Miequipo = new Equipos();
	        Miequipo.setNombre(nombreEquipo);
	        System.out.println(actividadId);
	        Miequipo.setId_asignacionproyecto(id_Asignacion.getId_tipoActividades());
	        Miequipo.setId_administrador(id_administrador);
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
	        

	        return "redirect:/ListarEquipos";  // Redirigir a la vista o mostrar un mensaje
	    }

	      
	    @GetMapping("/ListarEquipos")
	    public String ListarEquiposPorProyecto(Model model) {
	    	

	    	
	        List<Equipos> equipos = equiposService.findEquiposPorProyectoYActividad(Id_Proyecto); // Trae todos los equipos para un proyecto
	        List<Map<String, Object>> equiposConIntegrantes = new ArrayList<>();
	        String nombre=" ";


	        // Procesar cada equipo
	        for (Equipos equipo : equipos) {
	            Map<String, Object> equipoData = new HashMap<>();
	            equipoData.put("equipo", equipo);
	            

	            List<Map<String, String>> integrantes = new ArrayList<>();
	            for (Asignar_equipos asignacion : equiposService.MostarIntegrantesEquipo(equipo.getId_equipos())) {
	            	
	            	
	                Long id_voluntario = asignacion.getId_voluntario();
	                Voluntarios voluntario = null;
	                Usuarios usuario = null;
	                
	                
	                
	                
	                for(Tipo_Actividades tipo :equiposService.ActividadesEquipo(equipo.getId_equipos())) {
	                	
	                	
	                	
	               
	                

	                if (id_voluntario > 0) {
	                    voluntario = voluntarioService.findOne(id_voluntario);
	                    if (voluntario != null && voluntario.getId_usuarios() > 0) {
	                        usuario = usuarioServices.findOne(voluntario.getId_usuarios());

	                        Map<String, String> integranteData = new HashMap<>();
	                        integranteData.put("cedula", usuario.getCedula());
	                        integranteData.put("actividad",tipo.getNombre_act());
	                        integranteData.put("nombre", usuario.getNombre() + " " + usuario.getApellido());

	                        integrantes.add(integranteData);
	                    }
	                }
	                }
	                
	               
	            }
	            
	           
	            equipoData.put("integrantes", integrantes);
	            equiposConIntegrantes.add(equipoData);
	        }
	        
	        	
	        

	        model.addAttribute("equiposConIntegrantes", equiposConIntegrantes);  // Agregar los equipos con sus integrantes al modelo

	        return "listarEquipos";  // La vista donde se mostrará la tabla
	    }

	


	
}

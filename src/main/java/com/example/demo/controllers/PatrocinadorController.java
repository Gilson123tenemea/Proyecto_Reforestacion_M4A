package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IUsuarioServices;

@Controller

public class PatrocinadorController {
	
	@Autowired
	private IPatrocinadorServices patrocinadorservice;
	@Autowired
	private IUsuarioServices usuarioservice;

	@GetMapping("/listar")
	public String Listar(Model model) {
	    model.addAttribute("titulo", "Listas");

	    // Obtener todos los patrocinadores y usuarios
	    List<Patrocinador> patrocinadores = patrocinadorservice.findAll();
	    List<Usuarios> usuarios = usuarioservice.findAll();

	    // Crear un mapa para almacenar los patrocinadores por ID
	    Map<Long, Patrocinador> patrocinadorMap = patrocinadores.stream()
	            .collect(Collectors.toMap(Patrocinador::getId_patrocinador, patrocinador -> patrocinador));

	    // Crear una lista para almacenar los datos combinados
	    List<Object[]> combinados = new ArrayList<>();

	    // Iterar sobre los usuarios y agregar a la lista combinada
	    for (Usuarios usuario : usuarios) {
	        Patrocinador patrocinador = patrocinadorMap.get(usuario.getId_usuarios());
	        
	        if (patrocinador != null) {
	            combinados.add(new Object[]{patrocinador, usuario});
	        }
	    }

	    // Agregar la lista combinada al modelo
	    model.addAttribute("combinados", combinados);

	    return "lista"; // Aquí va la vista
	}
	

    // Método para mostrar el formulario
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("patrocinador", new Patrocinador()); // Inicializa el objeto
        model.addAttribute("usuario",new Usuarios());
        return "formularioPatrocinador"; // Nombre de la vista
    }

    // Método para guardar el patrocinador
    @PostMapping("/enviar")
    public String guardar(@ModelAttribute("patrocinador") Patrocinador patrocinador,
    		@ModelAttribute("usuario") Usuarios usuario) {
    	usuarioservice.save(usuario); 
        
    	patrocinador.setId_usuarios(usuario.getId_usuarios());
    	patrocinadorservice.save(patrocinador);
        
        return "redirect:/listar"; // Redirige a la lista de patrocinadores
    }
	
	
	

    @PutMapping("/modificar")
    public String Modificar(@ModelAttribute("patrocinador") Patrocinador patrocinador,
    		@ModelAttribute("usuario") Usuarios usuario) {
    	usuarioservice.save(usuario); 
        
    	patrocinador.setId_usuarios(usuario.getId_usuarios());
    	patrocinadorservice.save(patrocinador);
        
        return "redirect:/listar"; // Redirige a la lista de patrocinadores
    }
	
	
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        patrocinadorservice.delete(id); // Llama al servicio para eliminar el patrocinador
        return "redirect:/listar"; // Redirige a la lista de patrocinadores
    }
	

	
}

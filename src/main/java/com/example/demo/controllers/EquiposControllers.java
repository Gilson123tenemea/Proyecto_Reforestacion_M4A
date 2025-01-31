package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		model.addAttribute("titulo", equiposimpl.findAllProyectos());
		return "equipos";
	}
	

	@GetMapping("/MostrarProyectos")
	public String MostrarProyectos(Model model) {

		model.addAttribute("titulo", equiposService.findAll());
		return "/listar";

	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String crear(Map<String, Object> model) {
		Equipos equipos = new Equipos();
		model.put("equipos", equipos);
		model.put("titulo", "Formulario de Equipos");
		return "especie";
	}

	@RequestMapping(value = "/listar/editar/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Equipos equipos = null;

		// Verificar si el ID es válido
		if (id > 0) {
			equipos = equiposService.findOne(id);

			// Cambiar "=" a "==" para la comparación
			if (equipos == null) {
				flash.addFlashAttribute("error", "El ID del Equipo no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Equipo no puede ser 0");
			return "redirect:/listar";
		}

		model.put("equipos", equipos);
		model.put("titulo", "Editar Especie");
		return "especie";
	}

	@RequestMapping(value = "/especie", method = RequestMethod.POST)

	public String guardarEquipos(Equipos equipos, RedirectAttributes flash) {
		try {
			if (equipos.getId_equipos() != null) {
				Equipos equiposExistente = equiposService.findOne(equipos.getId_equipos());
				if (equiposExistente == null) {
					flash.addFlashAttribute("error", "El Equipo con ese ID no existe");
					return "redirect:/listar";
				}

				equiposExistente.setAsistencia(equipos.getAsistencia());
				equiposService.save(equiposExistente);
				flash.addFlashAttribute("success", "Equipo actualizada exitosamente");

			} else {
				equiposService.save(equipos);
				flash.addFlashAttribute("success", "Equipos guardada exitosamente");
			}
			return "redirect:/listar";
		} catch (Exception e) {
			flash.addFlashAttribute("error", "Error al guardar el Equipo: " + e.getMessage());
			return "redirect:/listar";
		}
	}

	@RequestMapping(value = "/equipos/eliminar/{id}", method = RequestMethod.GET)
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		try {
			equiposService.delete(id);
			flash.addFlashAttribute("success", "Equipos eliminada correctamente");
		} catch (IllegalStateException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			flash.addFlashAttribute("error", "Error al eliminar el Equipo");
		}
		return "redirect:/listar";
	}
}

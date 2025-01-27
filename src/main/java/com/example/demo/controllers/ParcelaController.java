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

import com.example.demo.entity.Parcelas;
import com.example.demo.service.IAreaServices;
import com.example.demo.service.IParcelaService;
import com.example.demo.service.IPlantasService;
import com.example.demo.service.ISueloService;

@Controller
public class ParcelaController {

    @Autowired
    private ISueloService sueloservice;

    @Autowired
    private IParcelaService parcelaservice;

    @Autowired
    private IAreaServices areaService;

    @Autowired
    private IPlantasService plantaService;

    // Listar Parcelas
    @RequestMapping(value = "/listarparcelas", method = RequestMethod.GET)
    public String listarParcelas(Model model) {
        model.addAttribute("titulo", "Listado de Parcelas");
        model.addAttribute("parcelas", parcelaservice.findAll());
        return "listarparcelas";
    }

    // Crear una nueva Parcela
    @RequestMapping(value = "/parcelas", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Parcelas parcela = new Parcelas();
        model.put("parcela", parcela);
        model.put("titulo", "Formulario de Nueva Parcela");
        model.put("suelos", sueloservice.listarsuelos()); // Lista de suelos
        model.put("plantas", plantaService.listarPlantas()); // Lista de plantas
        model.put("areas", areaService.listarAreas()); // Lista de áreas
        return "parcelas";
    }

    // Editar Parcela
    @RequestMapping(value = "/parcela/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Parcelas parcela = null;
        if (id > 0) {
            parcela = parcelaservice.findOne(id);
            if (parcela == null) {
                flash.addFlashAttribute("error", "El Id de la Parcela no existe en la base de datos");
                return "redirect:/listarparcelas";
            }
        } else {
            flash.addFlashAttribute("error", "El Id de la Parcela no puede ser 0");
            return "redirect:/listarparcelas";
        }
        model.put("parcela", parcela);
        model.put("titulo", "Editar Parcela");
        model.put("suelos", sueloservice.listarsuelos());
        model.put("plantas", plantaService.listarPlantas());
        model.put("areas", areaService.listarAreas());
        return "parcelas";
    }

    // Guardar Parcela (Crear o Actualizar)
    @RequestMapping(value = "/parcelas", method = RequestMethod.POST)
    public String guardarParcela(Parcelas parcela, RedirectAttributes flash) {
        try {
            if (parcela.getId_parcelas() != null) {
                // Si el ID existe, es una actualización
                Parcelas parcelaExistente = parcelaservice.findOne(parcela.getId_parcelas());
                if (parcelaExistente == null) {
                    flash.addFlashAttribute("error", "La Parcela con ese ID no existe");
                    return "redirect:/listarparcelas";
                }
                // Actualizar los campos necesarios (si es necesario)
                parcelaExistente.setId_suelo(parcela.getId_suelo());
                parcelaExistente.setId_plantas(parcela.getId_plantas());
                parcelaExistente.setId_area(parcela.getId_area());
                parcelaExistente.setLargo(parcela.getLargo());
                parcelaExistente.setAncho(parcela.getAncho());
                parcelaExistente.setX(parcela.getX());
                parcelaExistente.setY(parcela.getY());

                parcelaservice.save(parcelaExistente);
                flash.addFlashAttribute("success", "Parcela actualizada exitosamente");
            } else {
                // Si el ID no existe, es una creación
                parcelaservice.save(parcela);
                flash.addFlashAttribute("success", "Parcela guardada exitosamente");
            }
            return "redirect:/listarparcelas";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al guardar la Parcela: " + e.getMessage());
            return "redirect:/listarparcelas";
        }
    }

    // Eliminar Parcela
    @RequestMapping(value = "/parcela/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            parcelaservice.delete(id);
            flash.addFlashAttribute("success", "Parcela eliminada correctamente");
        } catch (IllegalStateException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar la Parcela");
        }
        return "redirect:/listarparcelas";
    }
    
    @GetMapping("/mapa")
    public String mostrarMapa(Model model) {
        List<Parcelas> parcelas = parcelaservice.findAll();
        model.addAttribute("parcelas", parcelas);
        return "mapa";
    }
    @GetMapping("/mapa/{id}")
    public String mostrarMapaParcela(@PathVariable(value = "id") Long id, Model model) {
        List<Parcelas> parcelas = parcelaservice.findAll();
        Parcelas parcelaSeleccionada = parcelaservice.findOne(id);
        
        if (parcelaSeleccionada == null) {
            return "redirect:/listarparcelas";
        }
        
        model.addAttribute("parcelas", parcelas);
        model.addAttribute("parcelaSeleccionada", parcelaSeleccionada);
        return "mapa";
    }

    
  
}

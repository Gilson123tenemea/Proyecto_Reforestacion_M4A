package com.example.demo.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.service.ICantonService;
import com.example.demo.service.IParroquiaService;
import com.example.demo.service.IProvinciaService;
import com.example.demo.service.IProyectoServices;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Valid;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

@Controller
public class ProyectosControllers {

    @Autowired
    private IProyectoServices proyectoService;
    
    @Autowired
    private IParroquiaService parroquiaService;
    
    @Autowired
    private ICantonService cantonService; 
    
    @Autowired
    private IProvinciaService provinciaService; 
    
    
    @GetMapping("/proyectos")
    public String crear(Model model) {
        model.addAttribute("proyecto", new Proyecto()); 
        model.addAttribute("parroquia", parroquiaService.findAll());
        model.addAttribute("provincias", provinciaService.findAll());
        return "proyectos"; 
    }
    

    @GetMapping("/proyectosestados")
    public String mostrarProyectosActivos(Model model) {
        
    	
        model.addAttribute("nombresProyectosActivos", proyectoService.findActivos());
        model.addAttribute("nombresProyectosInactivos", proyectoService.findInactivos());
        model.addAttribute("nombresProyectosFinalizados", proyectoService.findFinalizados());



        return "ReporteGrafico"; // Nombre de la vista (HTML con Thymeleaf)
    }




    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }


    @GetMapping("/listarProyectos")
    public String listarProyectos(Model model) {
        model.addAttribute("titulo", "Lista de Proyectos");
        model.addAttribute("proyectos", proyectoService.findAll());
        return "listarProyectos"; 
    }
    
    //@GetMapping("/verproyectospatrocinador")
    public String proyectospatrocinador(Model model) {
        model.addAttribute("titulo", "Lista de Proyectos");
        model.addAttribute("proyectos", proyectoService.findAll());
        return "verproyectospatrocinador"; 
    }



    @PostMapping("/guardarProyecto")
    public String guardarProyecto(@Valid @ModelAttribute("proyecto") Proyecto proyecto,
                                  @RequestParam("imagenArchivo") MultipartFile imagenArchivo,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (proyecto.getId_parroquia() == null) {
                throw new Exception("Debe seleccionar una Parroquia.");
            }

            if (!imagenArchivo.isEmpty()) {
                BufferedImage originalImage = ImageIO.read(imagenArchivo.getInputStream());
                BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 300, 300);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "jpg", baos);
                proyecto.setImagen(baos.toByteArray());
            } else {
                proyecto.setImagen(null);
            }

            proyectoService.save(proyecto);
            redirectAttributes.addFlashAttribute("mensaje", "Proyecto guardado exitosamente");
            return "redirect:/listarProyectos";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
            return "redirect:/proyectos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el proyecto: " + e.getMessage());
            return "redirect:/proyectos";
        }
    }    
    
    @GetMapping("/proyecto/imagen/{id}")
    @ResponseBody
    public byte[] obtenerImagenProyecto(@PathVariable("id") Long id) {
        Proyecto proyecto = proyectoService.findOne(id);
        if (proyecto != null && proyecto.getImagen() != null) {
            return proyecto.getImagen(); // Devuelve los bytes de la imagen
        }
        return new byte[0]; // Si no existe la imagen, devuelve un array vacío
    }
    
    @PostMapping("/proyectos/eliminar/{id}")
    public String eliminarProyecto(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        try {
            proyectoService.delete(id);
            flash.addFlashAttribute("success", "Proyecto eliminado correctamente");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar el Proyecto: " + e.getMessage());
        }
        return "redirect:/listarProyectos"; // Asegúrate de tener esta ruta definida
    }
    
    
    @GetMapping("/proyectos/editar/{id}")
    public String editarProyecto(@PathVariable("id") Long id, Model model, RedirectAttributes attributes) {
        try {
            Proyecto proyecto = proyectoService.findOne(id);
            if (proyecto == null) {
                attributes.addFlashAttribute("error", "El proyecto no existe");
                return "redirect:/listarProyectos";
            }

            // Obtener la parroquia relacionada con el proyecto
            Parroquia parroquia = parroquiaService.findOne(proyecto.getId_parroquia());
            if (parroquia != null) {
                model.addAttribute("parroquiaSeleccionada", parroquia);
                // Obtener el cantón relacionado con la parroquia
                Canton canton = cantonService.findOne(parroquia.getId_canton());
                if (canton != null) {
                    model.addAttribute("cantonSeleccionado", canton);
                    // Obtener la provincia relacionada con el cantón
                    Long idProvincia = canton.getId_provincia();
                    model.addAttribute("provinciaSeleccionada", idProvincia);
                }
            }

            model.addAttribute("proyecto", proyecto);
            model.addAttribute("parroquia", parroquiaService.findAll()); // Todas las parroquias
            model.addAttribute("provincias", provinciaService.findAll()); // Todas las provincias
            return "proyectos"; 
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al cargar el proyecto: " + e.getMessage());
            return "redirect:/listarProyectos";
        }
    }
    
    
    @GetMapping("/cantones/{idProvincia}")
    @ResponseBody
    public List<Canton> getCantonesByProvincia(@PathVariable Long idProvincia) {
        return cantonService.findByProvincia(idProvincia);
    }

    @GetMapping("/parroquias/{idCanton}")
    @ResponseBody
    public List<Parroquia> getParroquiasByCanton(@PathVariable Long idCanton) {
        return parroquiaService.findByCanton(idCanton);
    }
}

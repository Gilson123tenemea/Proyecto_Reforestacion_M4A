package com.example.demo.controllers;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.lowagie.text.Image;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Patrocinio;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IPatrocinadorServices;
import com.example.demo.service.IPatrocinioService;
import com.example.demo.service.IProyectoServices;
import com.example.demo.service.IUsuarioServices;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("idPatrocinador")
public class PatrocinioController {

	@Autowired
	private IProyectoServices proyectoService;
    @Autowired
    private IPatrocinioService patrocinioservice;
    
    @Autowired
    private IPatrocinadorServices patrocinadorService;
    
    @Autowired
    private IUsuarioServices usuarioservice;
    
    @GetMapping("/verproyectospatrocinador")
    public String listarProyectosYPatrocinios(Model model) {
    	
        Long idPatrocinador = (Long) model.asMap().get("idPatrocinador");
        List<Proyecto> proyectos = proyectoService.findAll();
        List<Patrocinio> allPatrocinios = patrocinioservice.findAll();
        List<Patrocinio> filteredPatrocinios = allPatrocinios.stream()
            .filter(patrocinio -> patrocinio.getId_patrocinador().equals(idPatrocinador))
            .collect(Collectors.toList());

        model.addAttribute("titulo", "Proyectos y Patrocinios");
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("patrocinios", filteredPatrocinios); 
        return "verproyectospatrocinador"; 
    }

    @RequestMapping("/patrocinarproyecto")
    public String crear(Map<String, Object> model) {
        Patrocinio patrocinio = new Patrocinio();
        model.put("patrocinio", patrocinio);
        model.put("titulo", "Patrocinio");
        return "patrocinarproyecto"; 
    }
    
    @GetMapping("/verpatrocinios")
    public String verPatrocinios(Model model, @SessionAttribute("idAdministrador") Long idAdministrador) {
        // Listar proyectos por el administrador específico
        List<Proyecto> proyectos = proyectoService.findByAdministradorId(idAdministrador);
        proyectos.sort(Comparator.comparing(Proyecto::getNombre)); // Ordenar alfabéticamente por nombre
        model.addAttribute("proyectos", proyectos); // Cargar proyectos del administrador

        return "verpatrocinios";
    }
    
    @GetMapping("/patrocinios/{idProyecto}")
    @ResponseBody
    public List<Map<String, Object>> obtenerPatrociniosPorProyecto(@PathVariable("idProyecto") Long idProyecto) {
        List<Patrocinio> patrocinios = patrocinioservice.findByIdProyecto(idProyecto);
        
        return patrocinios.stream().map(patrocinio -> {
            Map<String, Object> patrocinioMap = new HashMap<>();
            patrocinioMap.put("beneficios", patrocinio.getBeneficios());
            patrocinioMap.put("cantiad_estimada", patrocinio.getCantiad_estimada());
            patrocinioMap.put("detalledpnado", patrocinio.getDetalledpnado());
            patrocinioMap.put("fechainicio", patrocinio.getFechainicio());
            patrocinioMap.put("fechafin", patrocinio.getFechafin());
            patrocinioMap.put("tipo_patrocinio", patrocinio.getTipo_patrocinio());

            // Obtener el patrocinador y su nombre de empresa
            Patrocinador patrocinador = patrocinadorService.findOne(patrocinio.getId_patrocinador());
            patrocinioMap.put("nombreEmpresa", patrocinador != null ? patrocinador.getNombreEmpresa() : "Desconocido");

            return patrocinioMap;
        }).collect(Collectors.toList());
    }    
    
    @GetMapping("/verpatrocinios/pdf")
    public void generarPdf(@RequestParam("idProyecto") Long idProyecto, HttpServletResponse response, @SessionAttribute("idAdministrador") Long idAdministrador) throws Exception {
        // Configurar tipo de respuesta y encabezado
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=patrocinios.pdf");

        // Obtener el proyecto por ID
        Proyecto proyecto = proyectoService.findOne(idProyecto);
        if (proyecto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Proyecto no encontrado");
            return;
        }

        // Obtener patrocinios para el proyecto seleccionado
        List<Patrocinio> patrocinios = patrocinioservice.findByIdProyecto(idProyecto);
        if (patrocinios.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron patrocinios para este proyecto");
            return;
        }

        // Crear documento PDF
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Agregar línea en la parte superior
        PdfContentByte cb = writer.getDirectContent();
        cb.setLineWidth(1); // Grosor de la línea

        // Línea superior
        cb.moveTo(36, document.getPageSize().getHeight() - 35); // Coordenadas de inicio
        cb.lineTo(document.getPageSize().getWidth() - 36, document.getPageSize().getHeight() - 35); // Coordenadas de fin
        cb.stroke(); // Dibujar la línea

        // Agregar logo
        try {
            String logoPath = "src/main/resources/static/imagenes/LogoPrincipalReforest.png"; // Ruta completa
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(140, 120); // Ajusta el tamaño del logo
            logo.setAlignment(Image.ALIGN_CENTER); // Centrar el logo
            document.add(logo); // Agregar el logo al documento
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }

        // Agregar título
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
        Paragraph title = new Paragraph("Reporte de Patrocinios", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Línea debajo del título
        cb.moveTo(36, document.getPageSize().getHeight() - 180); // Coordenadas debajo del título
        cb.lineTo(document.getPageSize().getWidth() - 36, document.getPageSize().getHeight() - 180);
        cb.stroke(); // Dibujar línea

        // Agregar nombre del proyecto
        String nombreProyecto = proyecto.getNombre();
        Font projectFont = new Font(Font.HELVETICA, 16, Font.ITALIC);
        Paragraph projectTitle = new Paragraph("Para el Proyecto: " + nombreProyecto, projectFont);
        projectTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(projectTitle);

        // Línea debajo del nombre del proyecto
        cb.moveTo(36, document.getPageSize().getHeight() - 800); // Coordenadas debajo del nombre del proyecto
        cb.lineTo(document.getPageSize().getWidth() - 36, document.getPageSize().getHeight() - 800);
        cb.stroke(); // Dibujar línea
        document.add(new Paragraph(" ")); // Espacio en blanco

        document.add(new Paragraph(" ")); // Espacio en blanco

        // Crear tabla
        PdfPTable table = new PdfPTable(7); // 7 columnas
        table.setWidthPercentage(100); // Ancho de la tabla

        // Agregar encabezados de la tabla
        String[] headers = {"Nombre de la Empresa", "Tipo de Patrocinio", "Beneficios", "Fecha Inicio", "Fecha Fin", "Cantidad Estimada", "Detalle"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.HELVETICA, 12, Font.BOLD)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidth(1); // Borde
            table.addCell(cell);
        }

        // Agregar datos de patrocinios
        for (Patrocinio patrocinio : patrocinios) {
            Patrocinador patrocinador = patrocinadorService.findOne(patrocinio.getId_patrocinador());
            table.addCell(createCell(patrocinador != null ? patrocinador.getNombreEmpresa() : "Desconocido"));
            table.addCell(createCell(patrocinio.getTipo_patrocinio()));
            table.addCell(createCell(patrocinio.getBeneficios()));
            table.addCell(createCell(patrocinio.getFechainicio().toString())); // Formatear correctamente la fecha
            table.addCell(createCell(patrocinio.getFechafin().toString())); // Formatear correctamente la fecha
            table.addCell(createCell(String.valueOf(patrocinio.getCantiad_estimada())));
            table.addCell(createCell(patrocinio.getDetalledpnado()));
        }

        // Agregar la tabla al documento
        document.add(table);

        // Cerrar el documento
        document.close();
    }

    // Método auxiliar para crear celdas con bordes
    private PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setBorderWidth(1); // Borde de la celda
        cell.setPadding(5); // Espaciado interno
        return cell;
    }    
    
    @RequestMapping(value="/formularioPatrocinio", method=RequestMethod.POST)
    public String guardar(@ModelAttribute Patrocinio patrocinio, Model model) {
        Long idPatrocinador = (Long) model.asMap().get("idPatrocinador");

        if (idPatrocinador != null) {
            patrocinio.setId_patrocinador(idPatrocinador); 
        } else {
            System.out.println("ID del patrocinador es null"); 
        }
        System.out.println("Beneficios: " + patrocinio.getBeneficios());
        System.out.println("Detalle de Donación: " + patrocinio.getDetalledpnado());
        patrocinioservice.save(patrocinio);
        return "redirect:/verproyectospatrocinador"; 
    }
    
    @RequestMapping(value="/formularioPatrocinio/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
        Patrocinio patrocinio = null;
        if (id > 0) {
        	patrocinio = patrocinioservice.findOne(id);
        } else {
            return "redirect:/listarPatrocinios";
        }
        model.put("patrocinio", patrocinio);
        model.put("titulo", "Editar patrocinio");
        return "formularioPatrocinio";
    }

    @RequestMapping(value="/eliminarPatrocinio/{id}")
    public String eliminar(@PathVariable(value="id") Long id) {
        if (id > 0) {
            patrocinioservice.delete(id);
        }
        return "redirect:/listarPatrocinios";
    }
    
    @RequestMapping("/patrocinarproyecto/{id}")
    public String mostrarDetallesProyecto(@PathVariable("id") Long id, Model model) {
        Proyecto proyecto = proyectoService.findOne(id);
        if (proyecto != null) {
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("titulo", "Patrocinio del Proyecto: " + proyecto.getNombre());
        } else {
            model.addAttribute("error", "Proyecto no encontrado");
            return "error"; 
        }
        return "patrocinarproyecto"; 
    }
    
    @GetMapping("/proyectospatrocinados/{id}")
    public String detallePatrocinio(@PathVariable("id") Long id, Model model) {
        Patrocinio patrocinio = patrocinioservice.findOne(id);
        if (patrocinio != null) {
            model.addAttribute("patrocinio", patrocinio);
            Proyecto proyecto = proyectoService.findOne(patrocinio.getId_proyecto());
            model.addAttribute("proyecto", proyecto);

            if (patrocinio.getId_patrocinador() != null) {
                Usuarios patrocinador = usuarioservice.findOne(patrocinio.getId_patrocinador());
                model.addAttribute("patrocinador", patrocinador);
                System.out.println("Patrocinador: " + patrocinador); 
            }

            model.addAttribute("titulo", "Detalles del Patrocinio");
        } else {
            model.addAttribute("error", "Patrocinio no encontrado");
            return "error"; 
        }
        return "proyectospatrocinados"; 
    }
}
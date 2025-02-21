package com.example.demo.restcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.VoluntarioDTO;
import com.example.demo.entity.*;
import com.example.demo.service.*;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioRestController {
	
	    @Autowired
	    private IPatrocinioService patrocinioService;

	    @Autowired
	    private IAreaServices areaService;

	    @Autowired
	    private ISueloService sueloService;

	    @Autowired
	    private IProvinciaService provinciaService;
	    @Autowired
	    private IUsuarioServices usuarioService;
    @Autowired
    private IVoluntariosService voluntariosServices;

    @Autowired
    private IUsuarioServices usuarioServices;

    @Autowired
    private IProyectoServices proyectoService;

    @Autowired
    private IInscripcionServices inscripcionService;
    @Autowired
    private IVoluntariosService voluntariosService;
    @Autowired
    private RegistroActividadRealizadaService registroActividadRealizadaService;

    @Autowired
    private EquiposServiceImpl equipoService;
    
    @GetMapping("/proyectos/{idVoluntario}")
    public ResponseEntity<List<Proyecto>> getProyectosDisponibles(@PathVariable Long idVoluntario) {
        List<Inscripcion> inscripciones = inscripcionService.findAll();
        List<Proyecto> todosLosProyectos = proyectoService.findAll();

        Set<Long> proyectosInscritos = inscripciones.stream()
                .filter(inscripcion -> inscripcion.getId_voluntario().equals(idVoluntario))
                .map(Inscripcion::getId_proyecto)
                .collect(Collectors.toSet());

        List<Proyecto> proyectosDisponibles = todosLosProyectos.stream()
                .filter(proyecto -> !proyectosInscritos.contains(proyecto.getId_proyecto()) && "activo".equals(proyecto.getEstado()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(proyectosDisponibles);
    }

    @GetMapping("/actividades/{idVoluntario}")
    public ResponseEntity<Map<String, Object>> getActividades(@PathVariable Long idVoluntario) {
        List<Object[]> actividades = equipoService.obtenerActividadesPorHacer(idVoluntario);
        List<Object[]> actividadesPorAceptar = registroActividadRealizadaService.findActividadesPorAceptar(idVoluntario);
        List<Object[]> actividadesRealizadas = registroActividadRealizadaService.obtenerActividadesRealizadas(idVoluntario);

        Map<String, Object> response = new HashMap<>();
        response.put("actividades", actividades);
        response.put("actividadesPorAceptar", actividadesPorAceptar);
        response.put("actividadesRealizadas", actividadesRealizadas);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detalle/{actividadId}")
    public ResponseEntity<Object[]> getDetalleActividad(@PathVariable Long actividadId) {
        Optional<Object[]> actividadDetalle = registroActividadRealizadaService.obtenerDetalleActividad(actividadId);
        return actividadDetalle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/guardar")
    public Map<String, Object> guardarVoluntarioYUsuario(@RequestBody Map<String, Object> input) {
        Map<String, Object> response = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Map<String, Object> usuarioInput = (Map<String, Object>) input.get("usuario");
            Map<String, Object> voluntarioInput = (Map<String, Object>) input.get("voluntarios");

            Usuarios usuario = new Usuarios();
            Voluntarios voluntarios = new Voluntarios();

            usuario.setCedula((String) usuarioInput.get("cedula"));
            usuario.setNombre((String) usuarioInput.get("nombre"));
            usuario.setApellido((String) usuarioInput.get("apellido"));
            usuario.setCorreo((String) usuarioInput.get("correo"));

            String fechaNacimientoStr = (String) usuarioInput.get("fecha_nacimiento");
            if (fechaNacimientoStr != null) {
                Date fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
                usuario.setFecha_nacimiento(fechaNacimiento);
            }

            usuario.setGenero((String) usuarioInput.get("genero"));
            usuario.setCelular((String) usuarioInput.get("celular"));
            usuario.setContraseña((String) usuarioInput.get("contraseña"));


            voluntarios.setExperiencia((String) voluntarioInput.get("experiencia"));
            voluntarios.setEstado((Boolean) voluntarioInput.get("estado"));
            voluntarios.setDisponibilidad((Boolean) voluntarioInput.get("disponibilidad"));


            String fechaRegistroStr = (String) voluntarioInput.get("fechaRegistro");
            if (fechaRegistroStr != null) {
                Date fechaRegistro = dateFormat.parse(fechaRegistroStr);
                voluntarios.setFechaRegistro(fechaRegistro);
            }

            if (usuario.getId_usuarios() != null) {
                Usuarios usuarioExistente = usuarioServices.findOne(usuario.getId_usuarios());
                if (usuarioExistente != null) {

                    usuarioExistente.setCedula(usuario.getCedula());
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setApellido(usuario.getApellido());
                    usuarioExistente.setCorreo(usuario.getCorreo());
                    usuarioExistente.setFecha_nacimiento(usuario.getFecha_nacimiento());
                    usuarioExistente.setGenero(usuario.getGenero());
                    usuarioExistente.setCelular(usuario.getCelular());
                    usuarioExistente.setContraseña(usuario.getContraseña());
                    usuario = usuarioExistente;
                }
            }

            usuarioServices.save(usuario);
            voluntarios.setId_usuarios(usuario.getId_usuarios());
            voluntariosServices.save(voluntarios);

            response.put("message", "Voluntario y Usuario guardados correctamente");
        } catch (ParseException e) {
            response.put("error", "Error al convertir la fecha: " + e.getMessage());
        } catch (Exception e) {
            response.put("error", "Error al guardar: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoluntario(@PathVariable Long id) {
        try {
            Voluntarios voluntario = voluntariosServices.findOne(id);
            if (voluntario == null) {
                return ResponseEntity.notFound().build();
            }
            voluntariosServices.delete(id);
            return ResponseEntity.ok("Voluntario eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar: " + e.getMessage());
        }
    }
    // Obtener información de un voluntario
    @GetMapping("/informacion")
    public ResponseEntity<?> obtenerInformacionVoluntario() {
        List<Provincia> provincias = provinciaService.findAll();
        return ResponseEntity.ok(provincias);
    }
    
    @GetMapping("/login/{idVoluntario}")
    public ResponseEntity<?> getVoluntarioById(@PathVariable Long idVoluntario) {
        Voluntarios voluntario = voluntariosService.findOne(idVoluntario);
        
        if (voluntario == null) {
            return ResponseEntity.notFound().build(); // Return 404 if voluntario is not found
        }
        
        // Create a DTO to encapsulate the response data
        VoluntarioDTO voluntarioDTO = new VoluntarioDTO();
        voluntarioDTO.setNombre(voluntario.getUsuario().getCedula());
        voluntarioDTO.setNombre(voluntario.getUsuario().getNombre());
        voluntarioDTO.setApellido(voluntario.getUsuario().getApellido());
        voluntarioDTO.setCorreo(voluntario.getUsuario().getCorreo());
        voluntarioDTO.setFechaNacimiento(voluntario.getUsuario().getFecha_nacimiento());
        voluntarioDTO.setGenero(voluntario.getUsuario().getGenero());
        voluntarioDTO.setCelular(voluntario.getUsuario().getCelular());
        voluntarioDTO.setContraseña(voluntario.getUsuario().getContraseña());
        voluntarioDTO.setExperiencia(voluntario.getExperiencia());
        
        // Return the DTO in the response body
        return ResponseEntity.ok(voluntarioDTO);
    }


    // Inscribir voluntario en un proyecto
    @PostMapping("/inscribir/{idVoluntario}/{idProyecto}")
    public ResponseEntity<?> inscribirVoluntario( @PathVariable Long idVoluntario, @PathVariable Long idProyecto) {

        List<Inscripcion> inscripciones = inscripcionService.findAll();
        List<Proyecto> todosLosProyectos = proyectoService.findAll();

        boolean yaInscrito = inscripciones.stream()
                .anyMatch(inscripcion -> inscripcion.getId_voluntario().equals(idVoluntario) &&
                        todosLosProyectos.stream()
                                .anyMatch(proyecto -> proyecto.getId_proyecto().equals(inscripcion.getId_proyecto())
                                        && "activo".equals(proyecto.getEstado())));

        if (yaInscrito) {
            return ResponseEntity.badRequest().body("Ingreso negado, ya se encuentra en un proyecto en curso.");
        }

        Inscripcion nuevaInscripcion = new Inscripcion();
        nuevaInscripcion.setId_voluntario(idVoluntario);
        nuevaInscripcion.setId_proyecto(idProyecto);
        nuevaInscripcion.setFecha(new Date());

        inscripcionService.save(nuevaInscripcion);

        return ResponseEntity.ok("Te has inscrito al proyecto con éxito.");
    }

   

    // Obtener la imagen del proyecto
    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagenProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.findOne(id);
        if (proyecto != null && proyecto.getImagen() != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(proyecto.getImagen());
        }
        return ResponseEntity.notFound().build();
    }

    // Verificar existencia de cédula de voluntario
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Boolean> verificarCedulaVoluntario(@PathVariable String cedula) {
        boolean existe = voluntariosService.BuscarCedulaVoluntario(cedula);
        return ResponseEntity.ok(existe);
    }
}
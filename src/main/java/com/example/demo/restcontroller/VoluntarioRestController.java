package com.example.demo.restcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
import com.example.demo.service.IUsuarioServices;
import com.example.demo.service.IVoluntariosService;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioRestController {
	  @Autowired
	    private IVoluntariosService voluntariosServices;

	    @Autowired
	    private IUsuarioServices usuarioServices;

	    @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);
	        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
	    }

	    @GetMapping
	    public List<Map<String, Object>> listarVoluntarios() {
	        List<Voluntarios> voluntarios = voluntariosServices.findAll();
	        List<Usuarios> usuarios = usuarioServices.findAll();

	        List<Map<String, Object>> combinados = new ArrayList<>();
	        for (Usuarios usuario : usuarios) {
	            if (usuario.getVoluntarios() != null && !usuario.getVoluntarios().isEmpty()) {
	                Voluntarios voluntario = usuario.getVoluntarios().get(0);
	                Map<String, Object> datosCombinados = new HashMap<>();
	                datosCombinados.put("voluntarios", voluntario);
	                datosCombinados.put("usuario", usuario);
	                combinados.add(datosCombinados);
	            }
	        }

	        return combinados;
	    }

	    @GetMapping("/{id}")
	    public Map<String, Object> obtenerVoluntario(@PathVariable Long id) {
	        Map<String, Object> response = new HashMap<>();
	        Voluntarios voluntario = voluntariosServices.findOne(id);
	        if (voluntario != null) {
	            Usuarios usuario = usuarioServices.findOne(voluntario.getId_usuarios());
	            response.put("voluntarios", voluntario);
	            response.put("usuario", usuario);
	        } else {
	            response.put("error", "Voluntario no encontrado");
	        }
	        return response;
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
	    
	    @PutMapping("/modificar/{id}")
	    public Map<String, Object> modificarVoluntarioYUsuario(@PathVariable Long id, @RequestBody Map<String, Object> input) {
	        Map<String, Object> response = new HashMap<>();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        try {
	            Voluntarios voluntarioExistente = voluntariosServices.findOne(id);
	            if (voluntarioExistente == null) {
	                response.put("error", "Voluntario no encontrado");
	                return response;
	            }

	            Map<String, Object> usuarioInput = (Map<String, Object>) input.get("usuario");
	            Map<String, Object> voluntarioInput = (Map<String, Object>) input.get("voluntarios");

	            Usuarios usuario = usuarioServices.findOne(voluntarioExistente.getId_usuarios());
	            if (usuario != null) {
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

	                usuarioServices.save(usuario);
	            } else {
	                response.put("error", "Usuario no encontrado");
	                return response;
	            }

	            voluntarioExistente.setExperiencia((String) voluntarioInput.get("experiencia"));
	            voluntarioExistente.setEstado((Boolean) voluntarioInput.get("estado"));
	            voluntarioExistente.setDisponibilidad((Boolean) voluntarioInput.get("disponibilidad"));

	            String fechaRegistroStr = (String) voluntarioInput.get("fechaRegistro");
	            if (fechaRegistroStr != null) {
	                Date fechaRegistro = dateFormat.parse(fechaRegistroStr);
	                voluntarioExistente.setFechaRegistro(fechaRegistro);
	            }

	            voluntariosServices.save(voluntarioExistente);

	            response.put("message", "Voluntario y Usuario modificados correctamente");
	        } catch (ParseException e) {
	            response.put("error", "Error al convertir la fecha: " + e.getMessage());
	        } catch (Exception e) {
	            response.put("error", "Error al modificar: " + e.getMessage());
	        }
	        return response;
	    }
	    
	    @DeleteMapping("/{id}")
	    public Map<String, Object> deleteVoluntarios(@PathVariable Long id) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            Voluntarios voluntarios = voluntariosServices.findOne(id);
	            if (voluntarios != null) {
	                voluntariosServices.delete(id);
	                response.put("message", "Voluntario y Usuario eliminados exitosamente");
	            } else {
	                response.put("error", "Voluntario no encontrado");
	            }
	        } catch (Exception e) {
	            response.put("error", "Error al eliminar el voluntario: " + e.getMessage());
	        }
	        return response;
	    }
}

package com.example.demo.controllers;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
>>>>>>> Stashed changes
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

>>>>>>> Stashed changes
import com.example.demo.entity.Administrador;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.IAdministradorServices;
import com.example.demo.service.IUsuarioServices;

@Controller
public class ControladorAdministrador {
	
	@Autowired
	private IAdministradorServices administradorServices;
	
	@Autowired
	private IUsuarioServices usuarioServices;
	

	@GetMapping("/listarAdministradores")
	public String listarAdministradores(Model model) {
	    model.addAttribute("titulo", "Lista de Administradores");

	    List<Administrador> administradores = administradorServices.findAll();
	    List<Usuarios> usuarios = usuarioServices.findAll();

	    List<Map<String, Object>> combinados = new ArrayList<>();

	    for (Usuarios usuario : usuarios) {
	        if (!usuario.getAdministrador().isEmpty()) {
	            Administrador administrador = usuario.getAdministrador().get(0); 
	            Map<String, Object> datosCombinados = new HashMap<>();
	            datosCombinados.put("administrador", administrador);
	            datosCombinados.put("usuario", usuario);
	            combinados.add(datosCombinados);
	        }
	    }

	    model.addAttribute("combinados", combinados);
	    return "listarAdministradores";
	}

	@RequestMapping(value = "/Inicio", method = RequestMethod.GET)
	public String listarYEditar(@RequestParam(value = "id", required = false) Long id, 
	                             Map<String, Object> model, 
	                             RedirectAttributes flash) {
	    Administrador administrador = null;
	    
	    // Si se pasa un id, se obtiene el administrador para editarlo
	    if (id != null && id > 0) {
	        administrador = administradorServices.findOne(id);
	        if (administrador == null) {
	            flash.addFlashAttribute("info", "El administrador no existe en la base de datos");
	            return "redirect:/Inicio"; // Redirige si no encuentra al administrador
	        }
	    }
	    
	    model.put("administrador", administrador);
	    model.put("usuarios", usuarioServices.findAll());
	    model.put("titulo", "Editar o Crear Administrador");
	    return "Inicio"; // Devuelve la misma vista "Inicio"
	}



 
    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardar(Administrador administrador, RedirectAttributes flash) {
        String mensajeFls = (administrador.getId_administrador() != null) 
                ? "El administrador se ha editado con éxito"
                : "El administrador se ha creado con éxito";
        
        administradorServices.save(administrador);
        flash.addFlashAttribute("success", mensajeFls);
        return "redirect:/listarAdministradores"; // Redirige a la lista de administradores
    }


    
    @RequestMapping("/Inicio")
    public String crear(Map<String, Object> model) {
        model.put("administrador", new Administrador());
        model.put("usuarios", usuarioServices.findAll());
        return "Inicio"; 
    }
   
    
    @PostMapping("/delete/{id}")
    public String deleteAdministrador(@PathVariable Long id, Model model) {
        try {
            Administrador administrador = administradorServices.findOne(id);

            if (administrador == null) {
                model.addAttribute("mensaje", "Administrador no encontrado con ID: " + id);
                return "error";
            }

            administradorServices.delete(id);
            model.addAttribute("mensaje", "Administrador eliminado exitosamente");
            return "redirect:/listarAdministradores";
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el administrador: " + e.getMessage());
            return "error";
        }
    }

}

package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.IVoluntarioDao;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;
@Service
public class VoluntariosServiceImpl implements IVoluntariosService {

	@Autowired
	private IVoluntarioDao voluntarioDao;
	
	@Autowired
    private IUsuarioServices usuariosService; 
	
	@Transactional(readOnly=true)
	@Override
	public List<Voluntarios> findAll() {
		
		return voluntarioDao.findAll();
	}

	@Transactional
	@Override
	public void save(Voluntarios voluntarios) {
		voluntarioDao.save(voluntarios);
	}

	@Transactional(readOnly=true)
	@Override
	public Voluntarios findOne(Long id) {
		
		return voluntarioDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		voluntarioDao.delete(id);
		
	}
	
	@Transactional
	public void eliminarVoluntarioYUsuario(Long id) {
	    System.out.println("Intentando eliminar el voluntario con ID: " + id);
	    Voluntarios voluntario = findOne(id);
	    if (voluntario != null) {
	        System.out.println("Eliminando el voluntario con ID: " + id);
	        delete(id);  // Elimina el voluntario
	        Usuarios usuario = usuariosService.findOne(voluntario.getId_usuarios());
	        if (usuario != null) {
	            System.out.println("Eliminando el usuario con ID: " + usuario.getId_usuarios());
	            // Verifica si el usuario no tiene más voluntarios
	            if (usuario.getVoluntarios() == null || usuario.getVoluntarios().isEmpty()) {
	                usuariosService.delete(usuario.getId_usuarios());  // Elimina el usuario si no tiene más voluntarios
	            }
	        }
	    }
	}



	
	 @Transactional(readOnly = true)
	    public List<Voluntarios> findAdministradoresWithUsuarios(Long iVoluntario) {
	        return voluntarioDao.findAdministradoresWithUsuarios(iVoluntario);
	    }

}

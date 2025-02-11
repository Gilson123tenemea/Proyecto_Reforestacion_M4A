package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IUsuariosDao;
import com.example.demo.entity.Usuarios;

@Service
public class UsuariosServiceImpl implements IUsuarioServices{
	
	@Autowired
	private IUsuariosDao usuariosDao;

	@Transactional(readOnly=true)
	@Override
	public List<Usuarios> findAll() {
		return usuariosDao.findAll();
	}
	
	@Transactional
	@Override
	public void save(Usuarios usuario) {	
		usuariosDao.save(usuario);
	}

	@Transactional(readOnly=true)
	@Override
	public Usuarios findOne(Long id) {
		return usuariosDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		usuariosDao.delete(id);
	}
	
	public String authenticate(String cedula, String contraseña) {
	    Usuarios usuario = usuariosDao.findAll().stream()
	            .filter(u -> u.getCedula().equals(cedula) && u.getContraseña().equals(contraseña))
	            .findFirst()
	            .orElse(null);

	    if (usuario == null) {
	        return null; 
	    }

	    if (!usuario.getSuper_administrador().isEmpty()) {
	    	Long idSuperAdministrador = usuario.getSuper_administrador().get(0).getId_super_administrador();
	    	 System.out.println("ID del superadmin: " + idSuperAdministrador); 
	        return "superadmin"; 
	    }

	    if (!usuario.getAdministrador().isEmpty()) {
	    	Long idAdminstradir = usuario.getAdministrador().get(0).getId_administrador();
	    	 System.out.println("ID del administrador: " + idAdminstradir); 
	        return "administrador"; 
	    }

	    if (!usuario.getVoluntarios().isEmpty()) {
	        return "voluntario"; 
	    }
	    
	    if (!usuario.getPatrocinador().isEmpty()) {
	        Long idPatrocinador = usuario.getPatrocinador().get(0).getId_patrocinador();
	        System.out.println("ID del Patrocinador: " + idPatrocinador); 
	        return "patrocinador"; 
	    }

	    return null; 
	}

	@Override
	public List<Object[]> NumeroUsuariosProvincia(Long id_proyecto) {
		// TODO Auto-generated method stub
		return usuariosDao.NumeroUsuariosProvincia(id_proyecto);
	}

}


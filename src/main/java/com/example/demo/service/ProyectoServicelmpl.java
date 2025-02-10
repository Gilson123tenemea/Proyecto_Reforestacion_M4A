package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Plantas;
import com.example.demo.entity.Proyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProyectoServicelmpl implements IProyectoServices{

	@Autowired
	private IProyectoDao proyectodao;
	
	 @PersistenceContext 
	private EntityManager em;
	 
	@Transactional(readOnly = true)
	@Override
	public List<Proyecto> findAll() {
		return proyectodao.findAll();
	}

	@Transactional
	@Override
	public void save(Proyecto proyecto) {
	    if (proyecto.getId_proyecto() != null) {
	        // Cargar el proyecto existente para mantener las relaciones
	        Proyecto existingProyecto = proyectodao.findOne(proyecto.getId_proyecto());
	        
	        // Aquí asegúrate de no modificar la lista de áreas
	        proyecto.setArea(existingProyecto.getArea());
	        em.merge(proyecto);
	    } else {
	        em.persist(proyecto);
	    }
	}

    @Transactional
    public void saveWithImage(String nombre, MultipartFile file) throws IOException {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setImagen(file.getBytes());
        proyectodao.save(proyecto);
    }
	
	@Transactional(readOnly = true)
	@Override
	public Proyecto findOne(Long id) {
		return proyectodao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		proyectodao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Proyecto> listarproyectos() {
		return proyectodao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Proyecto> findActivos(Long id) {
		// TODO Auto-generated method stub
		return proyectodao.findActivos(id);
	}

	@Override
	public List<Proyecto> findInactivos() {
		// TODO Auto-generated method stub
		return proyectodao.findInactivos();	}

	@Override
	public List<Proyecto> findFinalizados() {
		// TODO Auto-generated method stub
		return proyectodao.findFinalizados();	}

	@Override
	public List<Proyecto> findByAreaId(Long idArea) {
		return proyectodao.findByAreaId(idArea); 
		
	}

	@Override
	public List<Proyecto> findByAdministradorId(Long idAdministrador) {
		 return proyectodao.findByAdministradorId(idAdministrador);
	}

	@Override
	public List<Proyecto> findactivos() {
		// TODO Auto-generated method stub
		return proyectodao.findactivos();
	}
	
	
}

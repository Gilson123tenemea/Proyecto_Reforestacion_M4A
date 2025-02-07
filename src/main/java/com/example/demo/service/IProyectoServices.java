package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Proyecto;

public interface IProyectoServices {
	
	@Query("SELECT p FROM Proyecto p")
	public List<Proyecto> findAll();
	
	public void save(Proyecto proyecto);
	
	@Query("SELECT p FROM Proyecto p WHERE p.id = ?1")
	public Proyecto findOne(Long id);
	
	public void delete(Long id);
	
	public List<Proyecto> listarproyectos(); 
	
	public List<Proyecto> findActivos();
	public List<Proyecto> findInactivos();
	public List<Proyecto> findFinalizados();
	List<Proyecto> findByAreaId(Long idArea);
	public List<Proyecto> findByAdministradorId(Long idAdministrador);
	
}

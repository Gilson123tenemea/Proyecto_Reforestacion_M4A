package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Proyecto;

public interface IProyectoDao {
	
	public List<Proyecto> findAll();
	public List<Proyecto> findActivos();
	public List<Proyecto> findInactivos();
	public List<Proyecto> findFinalizados();
	public void save(Proyecto proyecto);
	public Proyecto findOne(Long id);
	public void delete(Long id);
	public List<Proyecto> findByAreaId(Long idArea);

}


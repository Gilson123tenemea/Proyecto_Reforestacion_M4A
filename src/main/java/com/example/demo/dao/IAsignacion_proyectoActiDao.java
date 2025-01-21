package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Asignacion_proyectoActi;



public interface IAsignacion_proyectoActiDao {

    public List<Asignacion_proyectoActi> findAll();
	
	public void save (Asignacion_proyectoActi aproyecto);
	
	public Asignacion_proyectoActi findOne (Long id);

	public void delete(Long id);
}

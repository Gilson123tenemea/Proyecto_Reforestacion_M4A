package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Asignacion_equipo;


public interface IAsignacion_equipoDao {

    public List<Asignacion_equipo> findAll();
	
	public void save (Asignacion_equipo aequipo);
	
	public Asignacion_equipo findOne (Long id);

	public void delete(Long id);
}

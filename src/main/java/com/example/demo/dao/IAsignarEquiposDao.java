package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Asignar_equipos;

public interface IAsignarEquiposDao {
	
	public List<Asignar_equipos> findAll();
	public void save(Asignar_equipos asignar_equipos);
	public Asignar_equipos findOne(Long id);
	public void delete(Long id);
    public List<Asignar_equipos> findByAsignarEquipos(Long id_asignar_equipos);


}

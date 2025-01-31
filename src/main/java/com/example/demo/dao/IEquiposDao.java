package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Equipos;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Voluntarios;

public interface IEquiposDao {
	
	public List<Equipos> findAll();
	public void save(Equipos equipos);
	public Equipos findOne(Long id);
	public void delete(Long id);
	public List<Voluntarios> listarVoluntariosPorProyecto(Long id);
	 public List<Equipos> findByEquipos(Long id_equipos); 

}

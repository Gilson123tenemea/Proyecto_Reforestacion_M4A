package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Equipos;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;

public interface IEquiposService {
	
	public List<Equipos> findAll();
	public void save(Equipos equipos);
	public Equipos findOne(Long id);
	public void delete(Long id);
	public List<Usuarios> listarVoluntariosPorProyecto(Long id);
	public List<Voluntarios> ObtenerVoluntario(String cedula); 

	 

}

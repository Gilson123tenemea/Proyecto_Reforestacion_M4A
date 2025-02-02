package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Asignar_equipos;

public interface IAsignacionEquiposService {
	
	public List<Asignar_equipos> findAll();
	public void save(Asignar_equipos asignar_equipos);
	public Asignar_equipos findOne(Long id);
	public void delete(Long id);
    public List<Asignar_equipos> findByAsignarEquipos(Long id_asignar_equipos);

}

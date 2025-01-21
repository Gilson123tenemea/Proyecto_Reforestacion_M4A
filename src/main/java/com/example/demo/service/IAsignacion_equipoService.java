package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Asignacion_equipo;
@Service
public interface IAsignacion_equipoService {

    public List<Asignacion_equipo> findAll();
	
	public void save (Asignacion_equipo aequipo);
	
	public Asignacion_equipo findOne (Long id_asig_eq);

	public void delete(Long id_asig_eq);
}

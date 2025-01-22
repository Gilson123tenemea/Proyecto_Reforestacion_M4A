package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Proyecto_Participacion;
@Service
public interface IProyecto_ParticipacionService {

	public List<Proyecto_Participacion> findAll();
	
	public void save(Proyecto_Participacion proyecto_participacion);
	
	public Proyecto_Participacion findOne (Long id);
	
	public void delete(Long id);
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.asignacion_actividad;
@Service
public interface IInscripcion_actividadesService {

	public List<asignacion_actividad> findAll();
	public void save(asignacion_actividad inscripcionact);
	public asignacion_actividad findOne(Long id);
	public void delete(Long id);
	
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Inscripcion_actividades;
@Service
public interface IInscripcion_actividadesService {

	public List<Inscripcion_actividades> findAll();
	public void save(Inscripcion_actividades inscripcionact);
	public Inscripcion_actividades findOne(Long id);
	public void delete(Long id);
	
}

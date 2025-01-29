package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IInscripcionActividadesDao;

import com.example.demo.entity.asignacion_actividad;

@Service
public class Inscripcion_actividadesServiceImpl implements IInscripcion_actividadesService  {

	@Autowired
	private IInscripcionActividadesDao inscripciondao;
	
	@Transactional(readOnly = true)
	@Override
	public List<asignacion_actividad> findAll() {
		return inscripciondao.findAll();
	}

	@Transactional
	@Override
	public void save(asignacion_actividad inscripcion) {	
		inscripciondao.save(inscripcion);
	}

	@Transactional(readOnly = true)
	@Override
	public asignacion_actividad findOne(Long id) {
		return inscripciondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {	
		inscripciondao.delete(id);
	}
}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IInscripcionActividadesDao;

import com.example.demo.entity.Inscripcion_actividades;

@Service
public class Inscripcion_actividadesServiceImpl implements IInscripcion_actividadesService  {

	@Autowired
	private IInscripcionActividadesDao inscripciondao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Inscripcion_actividades> findAll() {
		return inscripciondao.findAll();
	}

	@Transactional
	@Override
	public void save(Inscripcion_actividades inscripcion) {	
		inscripciondao.save(inscripcion);
	}

	@Transactional(readOnly = true)
	@Override
	public Inscripcion_actividades findOne(Long id) {
		return inscripciondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {	
		inscripciondao.delete(id);
	}
}

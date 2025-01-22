package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IInscripcionDao;
import com.example.demo.entity.Inscripcion;

@Service
public class InscripcionServicelmpl implements IInscripcionServices{

	@Autowired
	private IInscripcionDao inscripciondao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Inscripcion> findAll() {
		return inscripciondao.findAll();
	}

	@Transactional
	@Override
	public void save(Inscripcion inscripcion) {	
		inscripciondao.save(inscripcion);
	}

	@Transactional(readOnly = true)
	@Override
	public Inscripcion findOne(Long id) {
		return inscripciondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {	
		inscripciondao.delete(id);
	}

}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IIntervencion_SueloDao;
import com.example.demo.entity.Intervencion_Suelo;

@Service
public class IntervencionSueloServiceslmpl implements IIntervencionSueloServices{

	@Autowired
	private IIntervencion_SueloDao intervenciondao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Intervencion_Suelo> findAll() {
		return intervenciondao.findAll();
	}

	@Transactional
	@Override
	public void save(Intervencion_Suelo intervencion) {		
		intervenciondao.save(intervencion);
	}

	@Transactional(readOnly = true)
	@Override
	public Intervencion_Suelo findOne(Long id) {
		return intervenciondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {	
		intervenciondao.delete(id);
	}

	@Override
	public List<Intervencion_Suelo> findByAreaAndProyecto(Long idArea, Long idProyecto) {
		 return intervenciondao.findByAreaAndProyecto(idArea, idProyecto);
	
	}

}

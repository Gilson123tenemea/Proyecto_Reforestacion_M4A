package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IPatrocinadorDao;
import com.example.demo.entity.Patrocinador;

@Service
public class PatrocinadorServiceslmpl implements IPatrocinadorServices{

	@Autowired
	private IPatrocinadorDao patrocinadordao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Patrocinador> findAll() {
		return patrocinadordao.findAll();
	}

	@Transactional
	@Override
	public void save(Patrocinador patrocinador) {		
		patrocinadordao.save(patrocinador);
	}

	@Transactional(readOnly = true)
	@Override
	public Patrocinador findOne(Long id) {
		return patrocinadordao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		patrocinadordao.delete(id);
	}

}

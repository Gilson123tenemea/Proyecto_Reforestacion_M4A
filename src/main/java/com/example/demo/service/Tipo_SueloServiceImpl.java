package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ITipo_SueloDao;
import com.example.demo.entity.Tipo_Suelo;


@Service

public class Tipo_SueloServiceImpl implements ITipo_SueloService {

	@Autowired
	private ITipo_SueloDao tpsueloDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Tipo_Suelo> findAll() {
		
		return tpsueloDao.findAll();
	}

	@Transactional
	@Override
	public void save(Tipo_Suelo tipo_suelo) {
		tpsueloDao.save(tipo_suelo);
	}

	@Transactional(readOnly=true)
	@Override
	public Tipo_Suelo findOne(Long id) {
		
		return tpsueloDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		tpsueloDao.delete(id);
		
	}

}

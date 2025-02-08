package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.dao.IPatrocinioDao;

import com.example.demo.entity.Patrocinio;

@Service
public class PatrocinioServiceImpl implements IPatrocinioService {

	@Autowired
	private IPatrocinioDao patrocinioDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Patrocinio> findAll() {
		
		return patrocinioDao.findAll();
	}

	@Transactional
	@Override
	public void save(Patrocinio patrocinio) {
		patrocinioDao.save(patrocinio);
	}

	@Transactional(readOnly=true)
	@Override
	public Patrocinio findOne(Long id) {
		
		return patrocinioDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		patrocinioDao.delete(id);
		
	}

	public List<Patrocinio> findByIdProyecto(Long idProyecto) {
	    return patrocinioDao.findByIdProyecto(idProyecto); // Implementa este método en tu DAO
	}
	


}

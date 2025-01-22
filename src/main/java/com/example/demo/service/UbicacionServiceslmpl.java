package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IUbicacionDao;
import com.example.demo.entity.Ubicacion;

@Service
public class UbicacionServiceslmpl implements IUbicacionServices{

	@Autowired
	private IUbicacionDao ubicaciondao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Ubicacion> findAll() {
		return ubicaciondao.findAll();
	}

	@Transactional
	@Override
	public void save(Ubicacion ubicacion) {
		ubicaciondao.save(ubicacion);
	}

	@Transactional(readOnly = true)
	@Override
	public Ubicacion findOne(Long id) {
		return ubicaciondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		ubicaciondao.delete(id);
	}

}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IActividadesDao;
import com.example.demo.entity.Actividades;

@Service
public class ActividadesServiceslmpl implements IActividadesServices{
	
	@Autowired
	private IActividadesDao actividadesdao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Actividades> findAll() {
		return actividadesdao.findAll();
	}

	@Transactional
	@Override
	public void save(Actividades actividades) {
		actividadesdao.save(actividades);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Actividades findOne(Long id) {
		return actividadesdao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		actividadesdao.delete(id);
	}

}

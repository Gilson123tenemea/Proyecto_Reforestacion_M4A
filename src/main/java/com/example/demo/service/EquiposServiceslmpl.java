package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IEquiposDao;
import com.example.demo.entity.Equipos;

@Service
public class EquiposServiceslmpl implements IEquiposServices{

	@Autowired
	private IEquiposDao equiposdao;

	@Transactional(readOnly = true)
	@Override
	public List<Equipos> findAll() {
		return equiposdao.findAll();
	}

	@Transactional
	@Override
	public void save(Equipos equipos) {
		equiposdao.save(equipos);
	}

	@Transactional(readOnly = true)
	@Override
	public Equipos findOne(Long id) {
		return equiposdao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		equiposdao.delete(id);
	}
	

}

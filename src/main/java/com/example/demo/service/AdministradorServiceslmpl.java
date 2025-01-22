package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAdministradorDao;
import com.example.demo.entity.Administrador;

@Service
public class AdministradorServiceslmpl implements IAdministradorServices{

	@Autowired
	private IAdministradorDao administradordao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Administrador> findAll() {
		return administradordao.findAll();
	}

	@Transactional
	@Override
	public void save(Administrador administrador) {
		administradordao.save(administrador);
	}

	@Transactional(readOnly = true)
	@Override
	public Administrador findOne(Long id) {
		return administradordao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		administradordao.delete(id);
	}

}
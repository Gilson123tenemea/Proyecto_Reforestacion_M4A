package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ISuperAdministradorDao;
import com.example.demo.entity.SuperAdministrador;

@Service
public class SuperAdministradorServiceslmpl implements ISuperAdministradorServices{

	@Autowired
	private ISuperAdministradorDao superadministrador;
	
	@Transactional(readOnly = true)
	@Override
	public List<SuperAdministrador> findAll() {
		return superadministrador.findAll();
	}

	@Transactional
	@Override
	public void save(SuperAdministrador superadmin) {	
		superadministrador.save(superadmin);
	}

	@Transactional(readOnly = true)
	@Override
	public SuperAdministrador findOne(Long id) {
		return superadministrador.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		superadministrador.delete(id);
	}

}

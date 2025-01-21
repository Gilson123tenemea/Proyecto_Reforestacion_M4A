package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.SuperAdministrador;


public interface ISuperAdministradorDao {
	
	public List<SuperAdministrador> findAll();
	public void save(SuperAdministrador superadministrador);
	public SuperAdministrador findOne(Long id);
	public void delete(Long id);

}

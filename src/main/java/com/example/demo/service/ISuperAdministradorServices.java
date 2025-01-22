package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SuperAdministrador;

public interface ISuperAdministradorServices {
	public List<SuperAdministrador> findAll();
	public void save(SuperAdministrador superadmin);
	public SuperAdministrador findOne(Long id);
	public void delete(Long id);

}

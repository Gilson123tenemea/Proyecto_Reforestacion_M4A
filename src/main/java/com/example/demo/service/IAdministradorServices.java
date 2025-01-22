package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Administrador;

public interface IAdministradorServices {
	
	public List<Administrador> findAll();
	public void save(Administrador administrador);
	public Administrador findOne(Long id);
	public void delete(Long id);

}

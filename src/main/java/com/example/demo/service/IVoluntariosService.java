package com.example.demo.service;

import java.util.List;


import com.example.demo.entity.Voluntarios;

public interface IVoluntariosService {

	public List<Voluntarios> findAll();
	public void save(Voluntarios voluntarios);
	public Voluntarios findOne (Long id);
	public void delete(Long id);
	public void eliminarVoluntarioYUsuario(Long id);
	
}

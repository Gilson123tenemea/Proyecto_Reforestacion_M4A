package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Voluntarios;
@Service
public interface IVoluntariosService {

	public List<Voluntarios> findAll();
	public void save(Voluntarios voluntarios);
	public Voluntarios findOne (Long id);
	public void delete(Long id);
	
}

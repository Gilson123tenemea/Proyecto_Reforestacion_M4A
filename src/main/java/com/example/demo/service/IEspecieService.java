package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Especie;

public interface IEspecieService {

	public List<Especie> findAll();
	public void save(Especie especie);
	public Especie findOne(Long id);
	public void delete(Long id);
	Especie findByNombre(String nombre);
}

package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Suelo;


public interface ISueloService {
	public List<Suelo> findAll();
	public void save(Suelo suelo);
	public Suelo findOne(Long id);
	public void delete(Long id);
	public List<Suelo> listarsuelos(); 
	public String findSueloName(Long idSuelo);
	long countParcelasBySueloId(Long idSuelo);
}

package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Suelo;

public interface ISueloDao {
	
	public List<Suelo> findAll();
	public void save(Suelo suelo);
	public Suelo findOne(Long id);
	public void delete(Long id);
	public List<Suelo> listarsuelos(); 
	long countParcelasBySueloId(Long idSuelo);

}

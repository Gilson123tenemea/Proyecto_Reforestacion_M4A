package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Ubicacion;

public interface IUbicacionDao {
	
	public List<Ubicacion> findAll();
	public void save(Ubicacion ubicacion);
	public Ubicacion findOne(Long id);
	public void delete(Long id);

}

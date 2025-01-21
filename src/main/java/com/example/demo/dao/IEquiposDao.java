package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Equipos;


public interface IEquiposDao {
	
	public List<Equipos> findAll();
	public void save(Equipos equipos);
	public Equipos findOne(Long id);
	public void delete(Long id);

}

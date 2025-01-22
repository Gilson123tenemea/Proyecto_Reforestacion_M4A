package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Proyecto;

public interface IProyectoDao {
	
	public List<Proyecto> findAll();
	public void save(Proyecto proyecto);
	public Proyecto findOne(Long id);
	public void delete(Long id);

}


package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Actividades;

public interface IActividadesDao {

	public List<Actividades> findAll();
	public void save(Actividades actividades);
	public Actividades findOne(Long id);
	public void delete(Long id);
}

package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Actividades;


public interface IActividadesServices {
	public List<Actividades> findAll();
	public void save(Actividades actividades);
	public Actividades findOne(Long id);
	public void delete(Long id);

}

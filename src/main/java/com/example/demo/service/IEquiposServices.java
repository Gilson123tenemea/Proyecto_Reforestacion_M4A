package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Equipos;


public interface IEquiposServices {
	public List<Equipos> findAll();
	public void save(Equipos equipos);
	public Equipos findOne(Long id);
	public void delete(Long id);

}

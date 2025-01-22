package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Proyecto;

public interface IProyectoServices {
	public List<Proyecto> findAll();
	public void save(Proyecto proyecto);
	public Proyecto findOne(Long id);
	public void delete(Long id);

}
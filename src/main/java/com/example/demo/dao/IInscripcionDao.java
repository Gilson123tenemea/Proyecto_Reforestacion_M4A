package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Inscripcion;

public interface IInscripcionDao {
	
	public List<Inscripcion> findAll();
	public void save(Inscripcion inscripcion);
	public Inscripcion findOne(Long id);
	public void delete(Long id);

}

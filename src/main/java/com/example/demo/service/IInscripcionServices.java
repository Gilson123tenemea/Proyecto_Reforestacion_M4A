package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Inscripcion;

public interface IInscripcionServices {
	public List<Inscripcion> findAll();
	public void save(Inscripcion inscripcion);
	public Inscripcion findOne(Long id);
	public void delete(Long id);

}

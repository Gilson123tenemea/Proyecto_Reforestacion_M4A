package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Inscripcion;
@Service
public interface IInscripcionServices {
	public List<Inscripcion> findAll();
	public void save(Inscripcion inscripcion);
	public Inscripcion findOne(Long id);
	public void delete(Long id);

}

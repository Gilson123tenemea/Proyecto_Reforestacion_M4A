package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Provincia;

public interface IProvinciaService {

	public List<Provincia> findAll();
	public void save(Provincia provincia);
	public Provincia findOne(Long id);
	public void delete(Long id);
}

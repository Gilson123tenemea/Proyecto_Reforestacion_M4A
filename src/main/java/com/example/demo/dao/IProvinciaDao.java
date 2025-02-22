package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Provincia;

public interface IProvinciaDao {
	
	public List<Provincia> findAll();
	public void save(Provincia provincia);
	public Provincia findOne(Long id);
	public void delete(Long id);
    long countCantonesByProvinciaId(Long idProvincia);


}

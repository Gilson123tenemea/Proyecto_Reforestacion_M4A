package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Patrocinador;


public interface IPatrocinadorDao {
	
	public List<Patrocinador> findAll();
	public void save(Patrocinador patrocinador);
	public Patrocinador findOne(Long id);
	public void delete(Long id);

}

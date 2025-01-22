package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Patrocinador;

public interface IPatrocinadorServices {
	public List<Patrocinador> findAll();
	public void save(Patrocinador patrocinador);
	public Patrocinador findOne(Long id);
	public void delete(Long id);

}

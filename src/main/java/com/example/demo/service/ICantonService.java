package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Canton;
import com.example.demo.entity.Provincia;

public interface ICantonService {
	public List<Canton> findAll();
	public void save(Canton canton);
	public Canton findOne(Long id);
	public void delete(Long id);
	public List<Canton> listarCanton(); 
	public List<Canton> findByProvincia(Long idProvincia); 
	long countParroquiasByCantonId(Long idCanton);
	 
}

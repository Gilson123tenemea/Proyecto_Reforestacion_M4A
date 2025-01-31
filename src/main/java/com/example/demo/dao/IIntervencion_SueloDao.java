package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Intervencion_Suelo;

public interface IIntervencion_SueloDao {
	
	public List<Intervencion_Suelo> findAll();
	public void save(Intervencion_Suelo intervencion_Suelo);
	public Intervencion_Suelo findOne(Long id);
	public void delete(Long id);
	List<Intervencion_Suelo> findByAreaAndProyecto(Long idArea, Long idProyecto);

}

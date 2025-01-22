package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Intervencion_Suelo;


public interface IIntervencionSueloServices {
	public List<Intervencion_Suelo> findAll();
	public void save(Intervencion_Suelo intervencion);
	public Intervencion_Suelo findOne(Long id);
	public void delete(Long id);

}

package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Tipo_Suelo;


public interface ITipo_SueloDao {

    public List<Tipo_Suelo> findAll();
	
	public void save (Tipo_Suelo tpsuelo);
	
	public Tipo_Suelo findOne (Long id);

	public void delete(Long id);
	
}

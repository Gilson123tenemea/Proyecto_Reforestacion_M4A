package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Tipo_Actividades;


public interface ITipo_ActividadesDao {

    public List<Tipo_Actividades> findAll();
	
	public void save (Tipo_Actividades tpactividades);
	
	public Tipo_Actividades findOne (Long id);

	public void delete(Long id);
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Tipo_Actividades;
@Service
public interface ITipo_ActividadesService {

    public List<Tipo_Actividades> findAll();
	
	public void save (Tipo_Actividades tipo_actividades);
	
	public Tipo_Actividades findOne (Long id);

	public void delete(Long id);
}

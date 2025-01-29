package com.example.demo.dao;

import java.util.List;
import com.example.demo.entity.asignacion_actividad;

public interface IInscripcionActividadesDao {

	   
	public List<asignacion_actividad> findAll();
	public void save(asignacion_actividad inscripcionact);
	public asignacion_actividad findOne(Long id);
	public void delete(Long id);
	

}

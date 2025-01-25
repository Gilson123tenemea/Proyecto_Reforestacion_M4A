package com.example.demo.dao;

import java.util.List;
import com.example.demo.entity.Inscripcion_actividades;

public interface IInscripcionActividadesDao {

	   
	public List<Inscripcion_actividades> findAll();
	public void save(Inscripcion_actividades inscripcionact);
	public Inscripcion_actividades findOne(Long id);
	public void delete(Long id);
	

}

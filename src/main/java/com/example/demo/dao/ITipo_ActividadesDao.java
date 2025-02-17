package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Tipo_Actividades;


public interface ITipo_ActividadesDao {

    public List<Tipo_Actividades> findAll();
	public void save (Tipo_Actividades tipo_actividades);
	public Tipo_Actividades findOne (Long id);
	public void delete(Long id);
	List<Tipo_Actividades> findByAdministradorId(Long adminId);
	long countAsignacionesByTipoActividadId(Long idTipoActividad);
	 long countRegistrosByTipoActividadId(Long idTipoActividad);
	
}

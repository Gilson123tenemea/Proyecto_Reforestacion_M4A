package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.ITipo_ActividadesDao;
import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Tipo_Actividades;

@Service

public class Tipo_ActividadesServiceImpl implements ITipo_ActividadesService {

	@Autowired
	private ITipo_ActividadesDao tpactividadesDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Tipo_Actividades> findAll() {
		
		return tpactividadesDao.findAll();
	}

	@Transactional
	@Override
	public void save(Tipo_Actividades tipo_actividades) {
	    if (tipo_actividades.getId_tipoActividades() != null) {
	        Tipo_Actividades existing = findOne(tipo_actividades.getId_tipoActividades());
	        if (existing != null) {
	            tipo_actividades.setAsignacion_proyectoacti(existing.getAsignacion_proyectoacti());
	        }
	    }
	    tpactividadesDao.save(tipo_actividades);
	}

	@Transactional(readOnly=true)
	@Override
	public Tipo_Actividades findOne(Long id) {
		
		return tpactividadesDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		tpactividadesDao.delete(id);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Tipo_Actividades> listaractividades() {
		return tpactividadesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tipo_Actividades> findByAdministradorId(Long adminId) {
		 return tpactividadesDao.findByAdministradorId(adminId);
	}
	
	@Override
	public long countAsignacionesByTipoActividadId(Long idTipoActividad) {
	    return tpactividadesDao.countAsignacionesByTipoActividadId(idTipoActividad);
	}

	@Override
	public long countRegistrosByTipoActividadId(Long idTipoActividad) {
	    return tpactividadesDao.countRegistrosByTipoActividadId(idTipoActividad);
	}
	@Override
	@Transactional(readOnly = true)
	public List<Asignacion_proyectoActi> findAsignacionesByTipo(Long idTipoActividad) {
	    return tpactividadesDao.findAsignacionesByTipo(idTipoActividad);
	}

}

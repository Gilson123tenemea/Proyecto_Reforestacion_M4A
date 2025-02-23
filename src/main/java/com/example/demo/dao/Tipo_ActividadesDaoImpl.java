package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Tipo_Actividades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Tipo_ActividadesDaoImpl implements ITipo_ActividadesDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo_Actividades> findAll() {
		return em.createQuery("from Tipo_Actividades").getResultList();
	}

	@Override
	public void save(Tipo_Actividades tipo_actividades) {
		if (tipo_actividades.getId_tipoActividades() != null 
				&& tipo_actividades.getId_tipoActividades() > 0) {
			em.merge(tipo_actividades);
		} else {
			em.persist(tipo_actividades);
		}
	}

	@Override
	public Tipo_Actividades findOne(Long id) {
		return em.find(Tipo_Actividades.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@Override
	public List<Tipo_Actividades> findByAdministradorId(Long adminId) {
		return em.createQuery(
		        "SELECT t FROM Tipo_Actividades t WHERE t.id_administrador = :adminId", 
		        Tipo_Actividades.class)
		        .setParameter("adminId", adminId)
		        .getResultList();
	}
	
	@Override
	public long countAsignacionesByTipoActividadId(Long idTipoActividad) {
	    return em.createQuery("SELECT COUNT(a) FROM Asignacion_proyectoActi a WHERE a.id_tipoActividades = :idTipoActividad", Long.class)
	             .setParameter("idTipoActividad", idTipoActividad)
	             .getSingleResult();
	}

	@Override
	public long countRegistrosByTipoActividadId(Long idTipoActividad) {
	    return em.createQuery("SELECT COUNT(r) FROM RegistroActividadRealiza r WHERE r.id_tipoActividades = :idTipoActividad", Long.class)
	             .setParameter("idTipoActividad", idTipoActividad)
	             .getSingleResult();
	}

	@Override
	public List<Asignacion_proyectoActi> findAsignacionesByTipo(Long idTipoActividad) {
		   return em.createQuery(
		            "SELECT a FROM Asignacion_proyectoActi a WHERE a.id_tipoActividades = :idTipoActividad", 
		            Asignacion_proyectoActi.class)
		            .setParameter("idTipoActividad", idTipoActividad)
		            .getResultList();
	}
}

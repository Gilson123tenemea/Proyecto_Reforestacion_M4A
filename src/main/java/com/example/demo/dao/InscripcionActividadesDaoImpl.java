package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.example.demo.entity.asignacion_actividad;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class InscripcionActividadesDaoImpl  implements IInscripcionActividadesDao {

	@PersistenceContext
	private EntityManager em;
    

	@SuppressWarnings("unchecked")
	@Override
	public List<asignacion_actividad> findAll() {
		return em.createQuery("from Inscripcion_actividades").getResultList();
		
	}

	@Override
	public void save(asignacion_actividad inscripcionact) {
		if (inscripcionact.getId_inscripcionactividades() != null && inscripcionact.getId_inscripcionactividades() > 0) {
			em.merge(inscripcionact);
		} else {
			em.persist(inscripcionact);
		}
	}

	@Override
	public asignacion_actividad findOne(Long id) {
		return em.find(asignacion_actividad.class, id);
	
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
		
	}
}

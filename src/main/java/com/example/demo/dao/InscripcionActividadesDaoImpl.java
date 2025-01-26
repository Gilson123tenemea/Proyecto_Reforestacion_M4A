package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.example.demo.entity.Inscripcion_actividades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class InscripcionActividadesDaoImpl  implements IInscripcionActividadesDao {

	@PersistenceContext
	private EntityManager em;
    

	@SuppressWarnings("unchecked")
	@Override
	public List<Inscripcion_actividades> findAll() {
		return em.createQuery("from Inscripcion_actividades").getResultList();
		
	}

	@Override
	public void save(Inscripcion_actividades inscripcionact) {
		if (inscripcionact.getId_inscripcionactividades() != null && inscripcionact.getId_inscripcionactividades() > 0) {
			em.merge(inscripcionact);
		} else {
			em.persist(inscripcionact);
		}
	}

	@Override
	public Inscripcion_actividades findOne(Long id) {
		return em.find(Inscripcion_actividades.class, id);
	
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
		
	}
}

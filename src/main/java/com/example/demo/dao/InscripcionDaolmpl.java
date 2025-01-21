package com.example.demo.dao;

import java.util.List;


import com.example.demo.entity.Inscripcion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class InscripcionDaolmpl implements IInscripcionDao {
	
	@PersistenceContext
	private EntityManager em;
    

	@SuppressWarnings("unchecked")
	@Override
	public List<Inscripcion> findAll() {
		return em.createQuery("from Inscripcion").getResultList();
		
	}

	@Override
	public void save(Inscripcion inscripcion) {
		if (inscripcion.getId_inscripcion() != null && inscripcion.getId_inscripcion() > 0) {
			em.merge(inscripcion);
		} else {
			em.persist(inscripcion);
		}
	}

	@Override
	public Inscripcion findOne(Long id) {
		return em.find(Inscripcion.class, id);
	
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
		
	}

}

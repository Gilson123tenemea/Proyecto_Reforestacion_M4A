package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Intervencion_Suelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class Intervencion_SueloDaolmpl implements IIntervencion_SueloDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervencion_Suelo> findAll() {
		return em.createQuery("from Intervencion_Suelo").getResultList();
	}

	@Override
	public void save(Intervencion_Suelo intervencion_Suelo) {
		if (intervencion_Suelo.getId_asignacionActividades() != null && intervencion_Suelo.getId_asignacionActividades() > 0) {
			em.merge(intervencion_Suelo);
		} else {
			em.persist(intervencion_Suelo);
		}
		
	}

	@Override
	public Intervencion_Suelo findOne(Long id) {
		return em.find(Intervencion_Suelo.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
		
	}

}

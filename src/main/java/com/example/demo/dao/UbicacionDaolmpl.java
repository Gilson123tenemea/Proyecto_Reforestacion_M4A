package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Ubicacion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UbicacionDaolmpl implements IUbicacionDao{
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ubicacion> findAll() {
		return em.createQuery("from Ubicacion").getResultList();
	}

	@Override
	public void save(Ubicacion ubicacion) {
		if (ubicacion.getId_ubicacion() != null && ubicacion.getId_ubicacion() > 0) {
			em.merge(ubicacion);
		} else {
			em.persist(ubicacion);
		}
		
	}

	@Override
	public Ubicacion findOne(Long id) {
		return em.find(Ubicacion.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));	
		
	}

}

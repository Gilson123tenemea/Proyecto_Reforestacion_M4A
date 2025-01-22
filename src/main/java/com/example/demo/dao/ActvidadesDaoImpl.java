package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Actividades;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ActvidadesDaoImpl implements IActividadesDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Actividades> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Actividades").getResultList();
	}

	@Override
	public void save(Actividades actividades) {
		if (actividades.getId_actividades() != null 
				&& actividades.getId_actividades() > 0) {
			em.merge(actividades);
		} else {
			em.persist(actividades);
		}
		
	}

	@Override
	public Actividades findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Actividades.class, id);
	}

	@Override
	public void delete(Long id) {
		
		em.remove(findOne(id));
		
	}

}

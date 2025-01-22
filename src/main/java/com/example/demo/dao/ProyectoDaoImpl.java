package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProyectoDaoImpl implements IProyectoDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Proyecto").getResultList();
	}

	@Override
	public void save(Proyecto proyecto) {
		if (proyecto.getId_proyecto() != null 
				&& proyecto.getId_proyecto() > 0) {
			em.merge(proyecto);
		} else {
			em.persist(proyecto);
		}

	}

	@Override
	public Proyecto findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Proyecto.class, id);
	}

	@Override
	public void delete(Long id) {
		
		em.remove(findOne(id));

	}

}

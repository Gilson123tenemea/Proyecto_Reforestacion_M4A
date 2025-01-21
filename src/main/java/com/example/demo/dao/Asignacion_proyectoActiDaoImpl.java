package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Asignacion_proyectoActi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class Asignacion_proyectoActiDaoImpl implements IAsignacion_proyectoActiDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Asignacion_proyectoActi> findAll() {
		return em.createQuery("from Asignacion_proyectoActi").getResultList();
	}

	@Override
	public void save(Asignacion_proyectoActi asignacion_proyecto) {
		if (asignacion_proyecto.getId_asignacionproyecto() != null 
				&& asignacion_proyecto.getId_asignacionproyecto() > 0) {
			em.merge(asignacion_proyecto);
		} else {
			em.persist(asignacion_proyecto);
		}
	}

	@Override
	public Asignacion_proyectoActi findOne(Long id) {
		return em.find(Asignacion_proyectoActi.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}

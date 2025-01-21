package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Asignacion_equipo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Asignacion_equipoDaoImpl implements IAsignacion_equipoDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Asignacion_equipo> findAll() {
		return em.createQuery("from Asignacion_equipo").getResultList();
	}

	@Override
	public void save(Asignacion_equipo asignacion_equipos) {
		if (asignacion_equipos.getId_asig_eq() != null 
				&& asignacion_equipos.getId_asig_eq() > 0) {
			em.merge(asignacion_equipos);
		} else {
			em.persist(asignacion_equipos);
		}
	}

	@Override
	public Asignacion_equipo findOne(Long id) {
		return em.find(Asignacion_equipo.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}

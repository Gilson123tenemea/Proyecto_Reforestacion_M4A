package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Equipos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EquiposDaolmpl implements IEquiposDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Equipos> findAll() {
		return em.createQuery("from Equipos").getResultList();
	}

	@Override
	public void save(Equipos equipos) {
		if (equipos.getId_equipo() != null
				&& equipos.getId_equipo() > 0) {
			em.merge(equipos);
		} else {
			em.persist(equipos);
		}
	}

	@Override
	public Equipos findOne(Long id) {
		return em.find(Equipos.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}
}

package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Tipo_Actividades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Tipo_ActividadesDaoImpl implements ITipo_ActividadesDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo_Actividades> findAll() {
		return em.createQuery("from Tipo_Actividades").getResultList();
	}

	@Override
	public void save(Tipo_Actividades tipo_actividades) {
		if (tipo_actividades.getId_tipoActividades() != null 
				&& tipo_actividades.getId_tipoActividades() > 0) {
			em.merge(tipo_actividades);
		} else {
			em.persist(tipo_actividades);
		}
	}

	@Override
	public Tipo_Actividades findOne(Long id) {
		return em.find(Tipo_Actividades.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}
}

package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Equipos;
import com.example.demo.entity.Especie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EspecieDaoImpl implements IEspecieDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Especie> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Especie").getResultList();
	}

	@Override
	public void save(Especie especie) {
		if (especie.getId_especie() != null
				&& especie.getId_especie() > 0) {
			em.merge(especie);
		} else {
			em.persist(especie);
		}

	}

	@Override
	public Especie findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Especie.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));

	}

}

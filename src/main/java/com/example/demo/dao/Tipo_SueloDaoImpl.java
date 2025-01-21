package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Tipo_Suelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Tipo_SueloDaoImpl implements ITipo_SueloDao {
	

		@PersistenceContext
		private EntityManager em;

		@SuppressWarnings("unchecked")
		@Override
		public List<Tipo_Suelo> findAll() {
			return em.createQuery("from Tipo_Suelo").getResultList();
		}

		@Override
		public void save(Tipo_Suelo tipo_suelos) {
			if (tipo_suelos.getId_tiposuelo() != null 
					&& tipo_suelos.getId_tiposuelo() > 0) {
				em.merge(tipo_suelos);
			} else {
				em.persist(tipo_suelos);
			}
		}

		@Override
		public Tipo_Suelo findOne(Long id) {
			return em.find(Tipo_Suelo.class, id);
		}

		@Override
		public void delete(Long id) {
			em.remove(findOne(id));
		}
}

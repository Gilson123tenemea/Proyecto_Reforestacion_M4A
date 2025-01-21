package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Administrador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AdministradorDaolmpl implements IAdministradorDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Administrador> findAll() {
		return em.createQuery("from Administrador").getResultList();
	}

	@Override
	public void save(Administrador administrador) {
		if (administrador.getId_administrador() != null 
				&& administrador.getId_administrador() > 0) {
			em.merge(administrador);
		} else {
			em.persist(administrador);
		}
	}

	@Override
	public Administrador findOne(Long id) {
		return em.find(Administrador.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}
}

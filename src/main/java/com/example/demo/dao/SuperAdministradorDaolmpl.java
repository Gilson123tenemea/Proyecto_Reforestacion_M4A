package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.SuperAdministrador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class SuperAdministradorDaolmpl implements ISuperAdministradorDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SuperAdministrador> findAll() {
		return em.createQuery("from SuperAdministrador").getResultList();
	}

	@Override
	public void save(SuperAdministrador superadministrador) {
		if (superadministrador.getId_super_administrador() != null
				&& superadministrador.getId_super_administrador() > 0) {
			em.merge(superadministrador);
		} else {
			em.persist(superadministrador);
		}
	}

	@Override
	public SuperAdministrador findOne(Long id) {
		return em.find(SuperAdministrador.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}
}

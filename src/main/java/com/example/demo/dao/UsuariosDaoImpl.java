package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Usuarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuariosDaoImpl implements IUsuariosDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override

	public List<Usuarios> findAll() {
		return em.createQuery("from Usuarios").getResultList();
	}

	@Override
	public void save(Usuarios usuarios) {
		if (usuarios.getId_usuarios()!= null && usuarios.getId_usuarios() > 0) {
			em.merge(usuarios);
		} else {
			em.persist(usuarios);
		}
	}

	@Override
	public Usuarios findOne(Long id) {
		return em.find(Usuarios.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}

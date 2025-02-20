package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Voluntarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PatrocinadorDaolmpl implements IPatrocinadorDao{
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Patrocinador> findAll() {
		return em.createQuery("from Patrocinador ").getResultList();
	}

	@Override
	public void save(Patrocinador patrocinador) {
		if (patrocinador.getId_patrocinador() != null
				&& patrocinador.getId_patrocinador() > 0) {
			em.merge(patrocinador);
		} else {
			em.persist(patrocinador);
		}
	}

	@Override
	public Patrocinador findOne(Long id) {
		return em.find(Patrocinador.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@Override
	public List<Patrocinador> PatrocinadorUsuario(Long id_patrocinador) {
	    String query = "SELECT a FROM Patrocinador a JOIN FETCH a.usuario WHERE a.id_patrocinador = :id_patrocinador";
	    return em.createQuery(query, Patrocinador.class)
	             .setParameter("id_patrocinador", id_patrocinador)
	             .getResultList();
	}
	
}

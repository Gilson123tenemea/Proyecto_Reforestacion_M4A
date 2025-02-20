package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Suelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository

public class SueloDaoImpl implements ISueloDao {
	@PersistenceContext
	private EntityManager en;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Suelo> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Suelo").getResultList();
	}

	
	@Transactional
	@Override
	public void save(Suelo suelo) {
		if (suelo.getId_suelo() != null 
				&& suelo.getId_suelo() > 0) {
			en.merge(suelo);
		} else {
			en.persist(suelo);
		}

	}

	@Override
	public Suelo findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Suelo.class, id);
	}

	@Override
	public void delete(Long id) {
		en.remove(findOne(id));
		
	}
	
	@Transactional
	@Override
	public List<Suelo> listarsuelos() {
	    return en.createQuery("SELECT s FROM Suelo s JOIN FETCH s.tipo_suelos", Suelo.class).getResultList();
	}
	
	@Override
	public long countParcelasBySueloId(Long idSuelo) {
	    return en.createQuery("SELECT COUNT(p) FROM Parcelas p WHERE p.id_suelo = :idSuelo", Long.class)
	             .setParameter("idSuelo", idSuelo)
	             .getSingleResult();
	}

}

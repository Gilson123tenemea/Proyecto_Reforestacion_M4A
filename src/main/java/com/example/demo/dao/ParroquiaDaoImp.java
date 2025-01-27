package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Parroquia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ParroquiaDaoImp implements IParroquiaDao {
	

	@PersistenceContext
	private EntityManager en;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Parroquia> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Parroquia").getResultList();
	}

	
	@Transactional
	@Override
	public void save(Parroquia parroquia) {
		// TODO Auto-generated method stub
		
	    if (parroquia.getId_parroquia() == null) {
	        en.persist(parroquia); 
	    } else {
	        en.merge(parroquia); 
	    }
		
	}

	
	@Transactional(readOnly = true)
	@Override
	public Parroquia findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Parroquia.class, id);
	}

	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		en.remove(findOne(id));
		
	}

	@Override
	public List<Parroquia> findByCanton(Long idCanton) {
	    return en.createQuery("SELECT p FROM Parroquia p WHERE p.id_canton = :idCanton", Parroquia.class)
	             .setParameter("idCanton", idCanton)
	             .getResultList();
	}
	
}
package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Provincia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProvinciaDaimpl implements IProvinciaDao {
	
	@PersistenceContext
	private EntityManager en;

	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Provincia> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Provincia").getResultList();
	}

	
	@Transactional
	@Override
	public void save(Provincia provincia) {
		// TODO Auto-generated method stub
		if(provincia.getId_provincia() != null && provincia.getId_provincia()>0) {
			en.persist(provincia);
		}else {
			en.merge(provincia);
		}
		
	}

	@Transactional(readOnly = true)
	@Override
	public Provincia findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Provincia.class, id);
	}

	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		en.remove(findOne(id));
		
	}

}

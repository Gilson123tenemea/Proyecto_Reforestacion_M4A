package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Monitoreo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository

public class MonitoreoDaoImpl implements IMonitoreoDao {

	@PersistenceContext
	private EntityManager en;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Monitoreo> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Monitoreo").getResultList();
	}

	@Transactional
	@Override
	public void save(Monitoreo monitoreo) {
		// TODO Auto-generated method stub
		
		if(monitoreo.getId_monitoreo() != null && monitoreo.getId_monitoreo()>0) {
			en.persist(monitoreo);
		}else {
			en.merge(monitoreo);
		}
		
	}

	
	@Transactional(readOnly = true)
	@Override
	public Monitoreo findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Monitoreo.class, id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		en.remove(findOne(id));
		
	}

}

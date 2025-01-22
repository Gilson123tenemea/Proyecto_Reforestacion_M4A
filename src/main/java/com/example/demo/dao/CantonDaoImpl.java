package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Canton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CantonDaoImpl implements ICantonDao {
	
	
	@PersistenceContext
	private EntityManager en;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Canton> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Canton").getResultList();
	}

	
	@Transactional
	@Override
	public void save(Canton canton) {
		// TODO Auto-generated method stub
		
		if(canton.getId_canton() != null && canton.getId_canton()>0) {
			en.persist(canton);
		}else {
			en.merge(canton);
		}
		
	}

	
	@Transactional(readOnly = true)
	@Override
	public Canton findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Canton.class, id);
	}

	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		en.remove(findOne(id));
		
	}

}

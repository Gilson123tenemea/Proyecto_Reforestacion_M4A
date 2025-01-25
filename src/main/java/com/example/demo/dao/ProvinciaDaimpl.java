package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Canton;
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
	    if (provincia.getCanton() != null) {
	        for (Canton canton : provincia.getCanton()) {
	            canton.setId_provincia(provincia.getId_provincia()); // Establecer la relación
	            en.merge(canton); // Guardar o actualizar el canton
	        }
	    }
	    if (provincia.getId_provincia() == null) {
	        en.persist(provincia); // Si es nueva provincia
	    } else {
	        en.merge(provincia); // Si es una provincia existente
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

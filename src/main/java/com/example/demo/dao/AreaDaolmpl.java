package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Area;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class AreaDaolmpl implements IAreaDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> findAll() {
		return em.createQuery("from Area").getResultList();
	}

	@Override
	public void save(Area area) {
		if (area.getId_area() != null && area.getId_area() > 0) {
			em.merge(area);
		} else {
			em.persist(area);
		}
	}

	@Override
	public Area findOne(Long id) {
		return em.find(Area.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));		
	}

	@Override
	public List<Area> findByProyectoIdAdministrador(Long adminId) {
		 return em.createQuery(
			        "SELECT a FROM Area a WHERE a.id_proyecto IN (SELECT p.id_proyecto FROM Proyecto p WHERE p.id_administrador = :adminId)", 
			        Area.class)
			        .setParameter("adminId", adminId)
			        .getResultList();
	}

}

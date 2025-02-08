package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Patrocinio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PatrocinioDaoImpl implements IPatrocinioDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Patrocinio> findAll() {
		return em.createQuery("from Patrocinio").getResultList();
	}

	@Override
	public void save(Patrocinio patrocinio) {
		if(patrocinio.getId_patrocina()!=null && patrocinio.getId_patrocina()>0) {
			em.merge(patrocinio);
		}else {
			em.persist(patrocinio);
		}
	}

	@Override
	public Patrocinio findOne(Long id) {
		return em.find(Patrocinio.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@Override
	public List<Patrocinio> findByIdProyecto(Long idProyecto) {
	    return em.createQuery("SELECT p FROM Patrocinio p WHERE p.id_proyecto = :idProyecto", Patrocinio.class)
	             .setParameter("idProyecto", idProyecto)
	             .getResultList();
	}
	

}

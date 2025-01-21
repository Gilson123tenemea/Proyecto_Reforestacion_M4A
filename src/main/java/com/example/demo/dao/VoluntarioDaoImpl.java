package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Voluntarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class VoluntarioDaoImpl implements IVoluntarioDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	
	@Override
	public List<Voluntarios> findAll() {
		return em.createQuery("from Voluntarios").getResultList();
	}

	@Override
	public void save(Voluntarios voluntarios) {
		if(voluntarios.getId_valuntario()!=null && voluntarios.getId_valuntario() > 0) {
			em.merge(voluntarios);
		}else{
			em.persist(voluntarios);
		}
	}

	@Override
	public Voluntarios findOne(Long id) {
		return em.find(Voluntarios.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}
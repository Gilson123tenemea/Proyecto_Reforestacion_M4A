package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Equipos;
import com.example.demo.entity.Especie;
import com.example.demo.entity.Voluntarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EquiposDaoImpl implements IEquiposDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	
	@Override
	public List<Equipos> findAll() {
		return em.createQuery("from Equipos").getResultList();
	}

	@Override
	public void save(Equipos equipos) {
		if (equipos.getId_equipos() != null
				&& equipos.getId_equipos() > 0) {
			em.merge(equipos);
		} else {
			em.persist(equipos);
		}
		
	}

	@Override
	public Equipos findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Equipos.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
		
	}

	

	@Override
	public List<Equipos> findByEquipos(Long id_equipos) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Voluntarios> listarVoluntariosPorProyecto(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("from Voluntarios").getResultList();
	}

}

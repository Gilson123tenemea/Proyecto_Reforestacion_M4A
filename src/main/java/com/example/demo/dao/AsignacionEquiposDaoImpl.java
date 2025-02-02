package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Parroquia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AsignacionEquiposDaoImpl implements IAsignarEquiposDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Asignar_equipos> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Asignar_Equipos").getResultList();
	}

	@Transactional
	@Override
	public void save(Asignar_equipos asignar_equipos) {
		if (asignar_equipos.getId_asignar_equipos() != null
				&& asignar_equipos.getId_asignar_equipos() > 0) {
			em.merge(asignar_equipos);
		} else {
			em.persist(asignar_equipos);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Asignar_equipos findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Asignar_equipos.class, id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		em.remove(findOne(id));

	}

	@Override
	public List<Asignar_equipos> findByAsignarEquipos(Long id_asignar_equipos) {
		// TODO Auto-generated method stub
		return null;
	}


}

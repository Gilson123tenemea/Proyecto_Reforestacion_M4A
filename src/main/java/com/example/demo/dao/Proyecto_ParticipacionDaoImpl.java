package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Proyecto_Participacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Proyecto_ParticipacionDaoImpl implements  IProyecto_ParticipacionDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")

	@Override
	public List<Proyecto_Participacion> findAll() {
		return em.createQuery("from Proyecto_Participacion").getResultList();
	}

	@Override
	public void save(Proyecto_Participacion proyecto_participacion) {
		if(proyecto_participacion.getId_proyecto_participacion()!=null && proyecto_participacion.getId_proyecto_participacion() >0) {
			em.merge(proyecto_participacion);
		} else {
			em.persist(proyecto_participacion);
		}
		
	}

	@Override
	public Proyecto_Participacion findOne(Long id) {
		return em.find(Proyecto_Participacion.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}

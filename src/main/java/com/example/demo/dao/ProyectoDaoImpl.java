package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.demo.entity.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProyectoDaoImpl implements IProyectoDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Proyecto").getResultList();
	}

	@Override
	public void save(Proyecto proyecto) {
		if (proyecto.getId_proyecto() != null 
				&& proyecto.getId_proyecto() > 0) {
			em.merge(proyecto);
		} else {
			em.persist(proyecto);
		}

	}

	@Override
	public Proyecto findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Proyecto.class, id);
	}

	@Override
	public void delete(Long id) {
		
		em.remove(findOne(id));

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> findActivos() {
		// TODO Auto-generated method stub
		return em.createQuery("from Proyecto where estado = :estado")
	             .setParameter("estado", "activo")
	             .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> findInactivos() {
		// TODO Auto-generated method stub
		return em.createQuery("select p.nombre from Proyecto p where p.estado = :estado")
	             .setParameter("estado", "inactivo")
	             .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> findFinalizados() {
		// TODO Auto-generated method stub

		return em.createQuery("select p.nombre from Proyecto p where p.estado = :estado")
	             .setParameter("estado", "Finalizado")
	             .getResultList();
	}

	@Override
	public List<Proyecto> findByAreaId(Long idArea) {
		return em.createQuery("SELECT p FROM Proyecto p JOIN Area a ON p.id_proyecto = a.id_proyecto WHERE a.id_area = :idArea", Proyecto.class)
                .setParameter("idArea", idArea)
                .getResultList();
	
	}

	@Override
	public List<Proyecto> findByAdministradorId(Long idAdministrador) {
		 return em.createQuery("SELECT p FROM Proyecto p WHERE p.id_administrador = :idAdministrador", Proyecto.class)
	             .setParameter("idAdministrador", idAdministrador)
	             .getResultList();
	}
	
	

}
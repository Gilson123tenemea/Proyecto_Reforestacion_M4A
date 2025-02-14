package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Asignacion_proyectoActi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
@Repository
public class Asignacion_proyectoActiDaoImpl implements IAsignacion_proyectoActiDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Asignacion_proyectoActi> findAll() {
		return em.createQuery("from Asignacion_proyectoActi").getResultList();
	}

	@Override
	public void save(Asignacion_proyectoActi asignacion_proyecto) {
		if (asignacion_proyecto.getId_asignacionproyecto() != null 
				&& asignacion_proyecto.getId_asignacionproyecto() > 0) {
			em.merge(asignacion_proyecto);
		} else {
			em.persist(asignacion_proyecto);
		}
	}

	@Override
	public Asignacion_proyectoActi findOne(Long id) {
		return em.find(Asignacion_proyectoActi.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@Override
	public List<Asignacion_proyectoActi> findByAdministradorId(Long adminId) {
		 return em.createQuery(
			        "SELECT a FROM Asignacion_proyectoActi a " +
			        "JOIN Tipo_Actividades t ON a.id_tipoActividades = t.id_tipoActividades " +
			        "WHERE t.id_administrador = :adminId", 
			        Asignacion_proyectoActi.class)
			        .setParameter("adminId", adminId)
			        .getResultList();
	}
	
	@Override
	public long countByProyectoId(Long idProyecto) {
	    return em.createQuery("SELECT COUNT(a) FROM Asignacion_proyectoActi a WHERE a.id_proyecto = :idProyecto", Long.class)
	                        .setParameter("idProyecto", idProyecto)
	                        .getSingleResult();
	}
	
	 public List<Asignacion_proyectoActi> findByProyectoId(Long idProyecto) {
	        TypedQuery<Asignacion_proyectoActi> query = em.createQuery("SELECT a FROM Asignacion_proyectoActi a WHERE a.id_proyecto = :idProyecto", Asignacion_proyectoActi.class);
	        query.setParameter("idProyecto", idProyecto);
	        return query.getResultList();
	    }

}

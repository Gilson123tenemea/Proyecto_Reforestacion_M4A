package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Voluntarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
		if(voluntarios.getId_voluntario()!=null && voluntarios.getId_voluntario() > 0) {
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
	
	public List<Object[]> findAllVoluntariosUserDetails() {
	    String jpql = "SELECT v.id_voluntario, u.cedula, u.nombre, u.apellido, u.correo, "
	                 + "v.actividades_gestionadas "
	                 + "FROM Voluntarios v JOIN Usuarios u ON v.id_usuarios = u.id_usuarios";
	    TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
	    return query.getResultList();
	}


	@Override
	public List<Voluntarios> findAdministradoresWithUsuarios(Long iVoluntario) {
		 String query = "SELECT a FROM Voluntarios a JOIN FETCH a.usuario WHERE a.id_voluntario = :iVoluntario";
	        return em.createQuery(query, Voluntarios.class)
	                 .setParameter("iVoluntario", iVoluntario)
	                 .getResultList();
		}
	

}

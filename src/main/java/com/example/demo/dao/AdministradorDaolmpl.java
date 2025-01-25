package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Administrador;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class AdministradorDaolmpl implements IAdministradorDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Administrador> findAll() {
		return em.createQuery("from Administrador").getResultList();
	}

	@Override
	public void save(Administrador administrador) {
		if (administrador.getId_administrador() != null 
				&& administrador.getId_administrador() > 0) {
			em.merge(administrador);
		} else {
			em.persist(administrador);
		}
	}

	@Override
	public Administrador findOne(Long id) {
		return em.find(Administrador.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}
	
	
	public List<Object[]> findAllAdminUserDetails() {
	    String jpql = "SELECT a.id_administrador, u.cedula, u.nombre, u.apellido, u.correo, "
	                 + "a.actividades_gestionadas, a.id_super_administrador "
	                 + "FROM Administrador a JOIN Usuarios u ON a.id_usuarios = u.id_usuarios";
	    TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
	    List<Object[]> results = query.getResultList();
	    
	    // Log the results to verify data
	    System.out.println("Retrieved admin user details: " + results);
	    
	    return results;
	}

	@Override
	public List<Administrador> findAdministradoresWithUsuarios(Long idAdministrador) {
        String query = "SELECT a FROM Administrador a JOIN FETCH a.usuario WHERE a.id_administrador = :idAdministrador";
        return em.createQuery(query, Administrador.class)
                 .setParameter("idAdministrador", idAdministrador)
                 .getResultList();
	}
}

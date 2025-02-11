package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Usuarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuariosDaoImpl implements IUsuariosDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override

	public List<Usuarios> findAll() {
		return em.createQuery("from Usuarios").getResultList();
	}

	@Override
	public void save(Usuarios usuarios) {
		if (usuarios.getId_usuarios()!= null && usuarios.getId_usuarios() > 0) {
			em.merge(usuarios);
		} else {
			em.persist(usuarios);
		}
	}

	@Override
	public Usuarios findOne(Long id) {
		return em.find(Usuarios.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> NumeroUsuariosProvincia(Long id_proyecto) {
	    return em.createQuery("SELECT COUNT(*) AS Total_voluntarios, pro.nombreProvincia " +
	            "FROM Voluntarios v " +
	            "JOIN Usuarios u ON v.id_usuarios = u.id_usuarios " +
	            "JOIN Parroquia pa ON u.id_parroquia = pa.id_parroquia " +
	            "JOIN Canton ca ON pa.id_canton = ca.id_canton " +
	            "JOIN Provincia pro ON ca.id_provincia = pro.id_provincia " +
	            "JOIN Inscripcion i ON i.id_voluntario = v.id_voluntario " +
	            "WHERE i.id_proyecto = :id_proyecto " +
	            "GROUP BY pro.nombreProvincia")
	            .setParameter("id_proyecto", id_proyecto)
	            .getResultList();
	}



}

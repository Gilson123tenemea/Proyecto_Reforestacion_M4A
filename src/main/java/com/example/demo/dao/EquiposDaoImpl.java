package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Especie;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.entity.Usuarios;
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

	

	

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuarios> listarVoluntariosPorProyecto(Long id) {
	    return em.createQuery(
	        "SELECT u FROM Usuarios u " +
	        "JOIN Voluntarios v ON u.id_usuarios = v.id_usuarios " +
	        "JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario " +
	        "WHERE i.id_proyecto = :id", Usuarios.class)
	        .setParameter("id", id)
	        .getResultList();
	}

	
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Voluntarios> ObtenerVoluntario(String cedula) {
		System.out.println("ced"+cedula);
	    return em.createQuery("FROM Voluntarios v JOIN Usuarios u ON u.id_usuarios = v.id_usuarios where u.cedula = :cedula",Voluntarios.class)
	             .setParameter("cedula", cedula)
	             .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo_Actividades> listarActividades(Long id_proyecto) {
		// TODO Auto-generated method stub
		return em.createQuery("Select T.nombre_act, a.id_asignacionproyecto from Tipo_Actividades T JOIN Asignacion_proyectoActi a on T.id_tipoActividades = a.id_tipoActividades where  a.id_proyecto = :id_proyecto")
				.setParameter("id_proyecto", id_proyecto)
	             .getResultList();
	}

}

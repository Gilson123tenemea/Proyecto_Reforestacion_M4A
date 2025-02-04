package com.example.demo.dao;

import com.example.demo.entity.RegistroActividadRealiza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class RegistroActividadRealizadaDaoImpl implements IRegistroActividadRealizadaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId) {
		return entityManager
				.createQuery("SELECT r FROM RegistroActividadRealiza r WHERE r.id_voluntario = :id_voluntario",
						RegistroActividadRealiza.class)
				.setParameter("id_voluntario", voluntarioId).getResultList();
	}

	@Override
	public List<Object[]> findActividadesRealizadas(Long voluntarioId) {
		return entityManager.createQuery(
				"SELECT u.nombre, p.nombre, tip.descripcion, r.cantidad_realizada, r.descripcion, r.foto, "
						+ "r.validacion_admin_tareaRealizada, r.validacion_voluntario_tareaRealizada "
						+ "FROM Usuarios u " + "INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios "
						+ "INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario "
						+ "INNER JOIN Equipos e ON i.id_inscripcion = e.id_inscripcion "
						+ "INNER JOIN Asignacion_proyectoActi tac ON e.id_asignacionproyecto = tac.id_asignacionproyecto "
						+ "INNER JOIN Tipo_Actividades tip ON tac.id_tipoActividades = tip.id_tipoActividades "
						+ "INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto "
						+ "INNER JOIN Intervencion_Suelo ins ON e.id_equipos = ins.id_equipos "
						+ "INNER JOIN RegistroActividadRealiza r ON ins.id_intervencion_suelo = r.id_intervencion_suelo "
						+ "WHERE v.id_voluntario = :voluntarioId " + "AND r.validacion_admin_tareaRealizada = TRUE", // Filtrar
																														// solo
																														// tareas
																														// validadas
																														// por
																														// el
																														// admin
				Object[].class).setParameter("voluntarioId", voluntarioId).getResultList();
	}

	@Override
	public Optional<Object[]> findDetalleActividadById(Long actividadId) {
	    List<Object[]> resultado = entityManager.createQuery(
	            "SELECT u.nombre, p.nombre, tip.descripcion, r.cantidad_realizada, r.descripcion, r.foto, " +
	                    "r.validacion_admin_tareaRealizada, r.validacion_voluntario_tareaRealizada " +
	                    "FROM Usuarios u " +
	                    "INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios " +
	                    "INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario " +
	                    "INNER JOIN Equipos e ON i.id_inscripcion = e.id_inscripcion " +
	                    "INNER JOIN Asignacion_proyectoActi tac ON e.id_asignacionproyecto = tac.id_asignacionproyecto " +
	                    "INNER JOIN Tipo_Actividades tip ON tac.id_tipoActividades = tip.id_tipoActividades " +
	                    "INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto " +
	                    "INNER JOIN Intervencion_Suelo ins ON e.id_equipos = ins.id_equipos " +
	                    "INNER JOIN RegistroActividadRealiza r ON ins.id_intervencion_suelo = r.id_intervencion_suelo " +
	                    "WHERE r.id_registroactividadrealizada = :actividadId", // Filtra por ID de actividad
	            Object[].class)
	        .setParameter("actividadId", actividadId)
	        .getResultList();

	    return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
	}


}

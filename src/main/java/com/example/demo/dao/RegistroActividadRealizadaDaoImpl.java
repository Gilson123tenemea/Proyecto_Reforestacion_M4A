package com.example.demo.dao;

import com.example.demo.entity.RegistroActividadRealiza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

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
						+ "WHERE v.id_voluntario = :voluntarioId " + "AND r.validacion_admin_tareaRealizada = TRUE",

				Object[].class).setParameter("voluntarioId", voluntarioId).getResultList();
	}

	@Override
	public Optional<Object[]> findDetalleActividadById(Long actividadId) {
		List<Object[]> resultado = entityManager
				.createQuery("SELECT u.nombre, p.nombre, tip.descripcion, r.cantidad_realizada, r.descripcion, r.foto, "
						+ "r.validacion_admin_tareaRealizada, r.validacion_voluntario_tareaRealizada "
						+ "FROM Usuarios u " + "INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios "
						+ "INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario "
						+ "INNER JOIN Equipos e ON i.id_inscripcion = e.id_inscripcion "
						+ "INNER JOIN Asignacion_proyectoActi tac ON e.id_asignacionproyecto = tac.id_asignacionproyecto "
						+ "INNER JOIN Tipo_Actividades tip ON tac.id_tipoActividades = tip.id_tipoActividades "
						+ "INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto "
						+ "INNER JOIN Intervencion_Suelo ins ON e.id_equipos = ins.id_equipos "
						+ "INNER JOIN RegistroActividadRealiza r ON ins.id_intervencion_suelo = r.id_intervencion_suelo "
						+ "WHERE r.id_registroactividadrealizada = :actividadId", Object[].class)
				.setParameter("actividadId", actividadId).getResultList();

		return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
	}

	@Override
	public List<RegistroActividadRealiza> findAll() {
		return entityManager.createQuery("SELECT r FROM RegistroActividadRealiza r", RegistroActividadRealiza.class)
				.getResultList();
	}

	@Override
	public List<Object[]> findActividadesRealizadas2(Long voluntarioId) {
	    return entityManager.createQuery("""
	            SELECT u.nombre, a.nombre, p.nombre, r.id_intervencion_suelo, tip.descripcion
	            FROM Usuarios u
	            INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios
	            INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario
	            INNER JOIN Equipos e ON i.id_inscripcion = e.id_inscripcion
	            INNER JOIN Asignacion_proyectoActi tac ON e.id_asignacionproyecto = tac.id_asignacionproyecto
	            INNER JOIN Tipo_Actividades tip ON tac.id_tipoActividades = tip.id_tipoActividades
	            INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto
	            INNER JOIN Intervencion_Suelo ins ON e.id_equipos = ins.id_equipos
	            INNER JOIN Parcelas pa ON ins.id_parcelas = pa.id_parcelas
	            INNER JOIN Area a ON pa.id_area = a.id_area
	            INNER JOIN RegistroActividadRealiza r ON r.id_intervencion_suelo = ins.id_intervencion_suelo
	            WHERE v.id_voluntario = :voluntarioId
	              AND r.validacion_admin_tareaRealizada = TRUE
	              AND r.validacion_voluntario_tareaRealizada = TRUE
	            """, Object[].class)
	        .setParameter("voluntarioId", voluntarioId)
	        .getResultList();
	}


    @Override
    public List<Object[]> findNombreSueloPorRegistro(Long voluntarioId) {
        return entityManager.createQuery(
                "SELECT a.nombre "
                        + "FROM RegistroActividadRealiza r "
                        + "INNER JOIN Intervencion_Suelo ins ON r.id_intervencion_suelo = ins.id_intervencion_suelo "
                        + "INNER JOIN Parcelas p ON ins.id_parcelas = p.id_parcelas "
                        + "INNER JOIN Area a ON p.id_area = a.id_area "
                        + "WHERE r.id_voluntario = :voluntarioId",
                Object[].class)
                .setParameter("voluntarioId", voluntarioId)
                .getResultList();
    }


    @Override
    public List<Object[]> findVoluntariosConProyectos() {
        return entityManager.createQuery(
                "SELECT u.nombre, p.nombre "
                        + "FROM RegistroActividadRealiza r "
                        + "INNER JOIN Intervencion_Suelo ins ON r.id_intervencion_suelo = ins.id_intervencion_suelo "
                        + "INNER JOIN Equipos e ON ins.id_equipos = e.id_equipos "
                        + "INNER JOIN Inscripcion i ON e.id_inscripcion = i.id_inscripcion "
                        + "INNER JOIN Voluntarios v ON i.id_voluntario = v.id_voluntario "
                        + "INNER JOIN Usuarios u ON v.usuario.id_usuarios = u.id_usuarios "
                        + "INNER JOIN Proyecto p ON i.id_proyecto = p.id_proyecto", 
                Object[].class)
                .getResultList();
    }

    @Override
    public List<Object[]> findActividadPorNombreVoluntario(String nombreVoluntario) {
        return entityManager.createQuery(
                "SELECT u.nombre, p.nombre, a.nombre "
                        + "FROM Usuarios u "
                        + "INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios "
                        + "INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario "
                        + "INNER JOIN Proyecto p ON i.id_proyecto = p.id_proyecto "
                        + "INNER JOIN Parcelas pa ON i.id_proyecto = pa.id_proyecto "
                        + "INNER JOIN Area a ON pa.id_area = a.id_area "
                        + "WHERE u.nombre LIKE :nombreVoluntario", 
                Object[].class)
                .setParameter("nombreVoluntario", "%" + nombreVoluntario + "%")
                .getResultList();
    }

    @Override
    public List<Object[]> findActividadPorIdVoluntario(Long voluntarioId) {
        return entityManager.createQuery(
                "SELECT u.nombre, p.nombre, a.nombre, i.id_intervencion_suelo "
                        + "FROM Usuarios u "
                        + "INNER JOIN Voluntarios v ON u.id_usuarios = v.usuario.id_usuarios "
                        + "INNER JOIN Inscripcion i ON v.id_voluntario = i.id_voluntario "
                        + "INNER JOIN Asignacion_proyectoActi tac ON i.id_inscripcion = tac.id_inscripcion "
                        + "INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto "
                        + "INNER JOIN Area a ON tac.id_area = a.id_area "
                        + "WHERE v.id_voluntario = :voluntarioId", 
                Object[].class)
                .setParameter("voluntarioId", voluntarioId)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        RegistroActividadRealiza registro = entityManager.find(RegistroActividadRealiza.class, id);
        if (registro != null) {
            entityManager.remove(registro);
        }
    }

    @Override
    public Optional<RegistroActividadRealiza> findOne(Long id) {
        RegistroActividadRealiza registro = entityManager.find(RegistroActividadRealiza.class, id);
        return Optional.ofNullable(registro);
    }

    @Override
    @Transactional
    public void save(RegistroActividadRealiza registro) {
        if (registro.getId_registroactividadrealizada() == null) {
            entityManager.persist(registro); // Guarda un nuevo registro
        } else {
            entityManager.merge(registro); // Actualiza un registro existente
        }
    }

	@Override
	public Optional<RegistroActividadRealiza> findByIntervencionSueloId(Long intervencionSueloId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<RegistroActividadRealiza> findById(Long idActividad) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List findActividadesPendientes() {
		// TODO Auto-generated method stub
		return null;
	}
}
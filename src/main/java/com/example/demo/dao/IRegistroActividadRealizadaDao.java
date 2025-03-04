package com.example.demo.dao;

import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRegistroActividadRealizadaDao {
    List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId);
    List<Object[]> findActividadesRealizadas(Long voluntarioId);
    Optional<Object[]> findDetalleActividadById(Long actividadId);

    
    List<RegistroActividadRealiza> findAll();
	public List<RegistroActividadRealiza> FiltroActividades(Long id_administrador,Long id_proyecto);


    void deleteById(Long id);

    Optional<RegistroActividadRealiza> findOne(Long id);
	List<Object[]> findNombreSueloPorRegistro(Long voluntarioId);
	List<Object[]> findVoluntariosConProyectos();
	void save(RegistroActividadRealiza registro);
	 List<Object[]> findActividadPorNombreVoluntario(String nombreVoluntario);
	List<Object[]> findActividadPorIdVoluntario(Long voluntarioId);
	@Query("SELECT r FROM RegistroActividadRealiza r WHERE r.id_intervencion_suelo = :intervencionSueloId")
	Optional<RegistroActividadRealiza> findByIntervencionSueloId(@Param("intervencionSueloId") Long intervencionSueloId);
	List<Object[]> findActividadesRealizadas2(Long voluntarioId);
	Optional<RegistroActividadRealiza> findById(Long idActividad);
	List findActividadesPendientes();
	List<Object[]> findActividadesPorAceptar(Long voluntarioId);
	
	@Query("SELECT a.porcentajeActividad FROM Asignacion_proyectoActi a WHERE a.id_tipoActividades = :idTipoActividad")
	List<Double> findPorcentajeByTipoActividad(@Param("idTipoActividad") Long idTipoActividad);
	
	@Query("SELECT a.id_proyecto FROM Asignacion_proyectoActi a WHERE a.id_tipoActividades = :idTipoActividad")
	Long findProyectoByTipoActividad(@Param("idTipoActividad") Long idTipoActividad);
	
	@Query("SELECT e.id_equipos, e.nombre AS equipoNombre, u.nombre AS voluntarioNombre, r.cantidad_realizada, r.descripcion, r.foto, ta.nombre_act AS actividadNombre, p.nombre AS proyectoNombre "
		      + "FROM RegistroActividadRealiza r "
		      + "INNER JOIN Voluntarios v ON r.id_voluntario = v.id_voluntario "
		      + "INNER JOIN Usuarios u ON v.id_usuarios = u.id_usuarios "
		      + "INNER JOIN Asignar_equipos ae ON v.id_voluntario = ae.id_voluntario "
		      + "INNER JOIN Equipos e ON ae.id_equipos = e.id_equipos "
		      + "INNER JOIN Asignacion_proyectoActi tac ON e.id_asignacionproyecto = tac.id_asignacionproyecto "
		      + "INNER JOIN Tipo_Actividades ta ON tac.id_tipoActividades = ta.id_tipoActividades "
		      + "INNER JOIN Proyecto p ON tac.id_proyecto = p.id_proyecto "
		      + "WHERE r.id_registroactividadrealizada = :idRegistro")
		List<Object[]> findDetallesPorRegistroNuevo(@Param("idRegistro") Long idRegistro);
		
		
		@Query("SELECT u.nombre, u.apellido FROM RegistroActividadRealiza r "
			      + "JOIN Voluntarios v ON r.id_voluntario = v.id_voluntario "
			      + "JOIN Usuarios u ON v.usuario.id_usuarios = u.id_usuarios "
			      + "WHERE r.id_registroactividadrealizada = :idRegistro")
			List<Object[]> findVoluntariosPorActividad(@Param("idRegistro") Long idRegistro);
	
			List<Object[]> findInfo_RegistroRealizado(Long voluntarioId, Long RegistroActividadRealizada_Id);
			
			 List<Object[]> findInfo_RegistroRealizado_cumplido(Long voluntarioId, Long RegistroActividadRealizada_Id);

			
		///////////////////////////////////////////////////////////////////////////
			List<Asignar_equipos> listarvoluntariosporequipos (Long idvolunta);
			
			List<Usuarios> listarvoluntariosUsuarios(Long idusu);
			
			
}
package com.example.demo.dao;

import com.example.demo.entity.RegistroActividadRealiza;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRegistroActividadRealizadaDao {
    List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId);
    List<Object[]> findActividadesRealizadas(Long voluntarioId);
    Optional<Object[]> findDetalleActividadById(Long actividadId);

    
    List<RegistroActividadRealiza> findAll();

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

}
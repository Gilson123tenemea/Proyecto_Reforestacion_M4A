package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;

public interface IEquiposDao {

	public List<Equipos> findAll();

	public void save(Equipos equipos);

	public Equipos findOne(Long id);

	public void delete(Long id);

	public List<Usuarios> listarVoluntariosPorProyecto(Long id);
	public List<Tipo_Actividades> ActividadesEquipo(Long id);
	public List<Equipos> findEquiposPorProyectoYActividad(Long idProyecto);

	public List<Tipo_Actividades> listarActividades(Long id_proyecto);
	public List<Asignar_equipos> MostarIntegrantesEquipo(Long idequipo);

	public List<Voluntarios> ObtenerVoluntario(String cedula);

	Equipos findByVoluntarioId(Long voluntarioId);

	List<Equipos> findEquiposPorProyecto(Long idProyecto);

	List<Object[]> findActividadesPorHacer(Long voluntarioId);
	
	List<Equipos> findByProyectoId(Long idProyecto);  // Método nuevo
}
package com.example.demo.service;

import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IRegistroActividadRealizadaService {

    List<RegistroActividadRealiza> findAll();

    List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId);

    List<Object[]> findActividadesRealizadas(Long voluntarioId);
    List<Object[]> findActividadesPorAceptar(Long voluntarioId);

    List<Object[]> findNombreSueloPorRegistro(Long voluntarioId);

    List<Object[]> findVoluntariosConProyectos();

    void deleteById(Long id);

    Optional<RegistroActividadRealiza> findOne(Long id);

    void save(RegistroActividadRealiza registro);

	List<Object[]> findActividadPorNombreVoluntario(String nombreVoluntario);

	List<Object[]> findActividadPorIdVoluntario(Long id);
	List<Object[]> findById(Long id);
	List<RegistroActividadRealiza> findAllActividades();
    void confirmarActividad(Long id);
    Long obtenerIdProyectoPorTipoActividad(Long idTipoActividad);
    List<Object[]> obtenerDetallesPorRegistroNuevo(Long idRegistro);
    
	List<Asignar_equipos> listarvoluntariosporequipos (Long idvolunta);
	List<Usuarios> listarvoluntariosUsuarios(Long idusu);
}

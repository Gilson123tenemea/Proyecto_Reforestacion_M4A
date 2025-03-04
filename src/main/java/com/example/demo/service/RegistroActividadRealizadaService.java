package com.example.demo.service;

import com.example.demo.dao.IRegistroActividadRealizadaDao;
import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.RegistroActividadRealiza;
import com.example.demo.entity.Usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroActividadRealizadaService {

    @Autowired
    private IRegistroActividadRealizadaDao registroActividadRealizadaDao;

    public List<Object[]> obtenerActividadesRealizadas(Long voluntarioId) {
        return registroActividadRealizadaDao.findActividadesRealizadas(voluntarioId);
    }
    
    
    public Optional<Object[]> obtenerDetalleActividad(Long actividadId) {
        return registroActividadRealizadaDao.findDetalleActividadById(actividadId);
    }

    public List<RegistroActividadRealiza> findAll() {
        return registroActividadRealizadaDao.findAll();
    }

    public List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId) {
        return registroActividadRealizadaDao.findByVoluntarioId(voluntarioId);
    }

    public List<Object[]> findActividadesRealizadas(Long voluntarioId) {
        return registroActividadRealizadaDao.findActividadesRealizadas(voluntarioId);
    }
    
    public List<Object[]> findActividadesRealizadas2(Long voluntarioId) {
        return registroActividadRealizadaDao.findActividadesRealizadas2(voluntarioId);
    }

    public List<Object[]> findActividadesPorAceptar(Long voluntarioId) {
        return registroActividadRealizadaDao.findActividadesPorAceptar(voluntarioId);
    }

    public List<Object[]> findNombreSueloPorRegistro(Long voluntarioId) {
        return registroActividadRealizadaDao.findNombreSueloPorRegistro(voluntarioId);
    }

    public List<Object[]> findVoluntariosConProyectos() {
        return registroActividadRealizadaDao.findVoluntariosConProyectos();
    }

    public void deleteById(Long id) {
        registroActividadRealizadaDao.deleteById(id);
    }

    public Optional<RegistroActividadRealiza> findOne(Long id) {
        return registroActividadRealizadaDao.findOne(id);
    }

    public void save(RegistroActividadRealiza registro) {
        registroActividadRealizadaDao.save(registro);
    }

    public List<Object[]> findActividadPorNombreVoluntario(String nombreVoluntario) {
        return registroActividadRealizadaDao.findActividadPorNombreVoluntario(nombreVoluntario);
    }

    public List<Object[]> findActividadPorIdVoluntario(Long id) {
        return registroActividadRealizadaDao.findActividadPorIdVoluntario(id);
    }

    public Optional<RegistroActividadRealiza> findByIntervencionSueloId(Long intervencionSueloId) {
        return registroActividadRealizadaDao.findByIntervencionSueloId(intervencionSueloId);
    }

    public Optional<RegistroActividadRealiza> findById(Long id) {
        return registroActividadRealizadaDao.findById(id);
    }
    public List<Object[]> findComparar(Long voluntarioId) {
    	return registroActividadRealizadaDao.findActividadesPendientes();
    }
    public List<RegistroActividadRealiza> findAllActividades() {
        return registroActividadRealizadaDao.findAll();
    }
    public void confirmarActividad(Long id) {
        Optional<RegistroActividadRealiza> actividadOpt = registroActividadRealizadaDao.findOne(id);
        if (actividadOpt.isPresent()) {
            RegistroActividadRealiza actividad = actividadOpt.get();
            actividad.setValidacion_admin_tareaRealizada(true);
            registroActividadRealizadaDao.save(actividad);
        }
    }
    public List<Double> obtenerPorcentajesPorTipoActividad(Long idTipoActividad) {
        return registroActividadRealizadaDao.findPorcentajeByTipoActividad(idTipoActividad);
    } 
    public Long obtenerIdProyectoPorTipoActividad(Long idTipoActividad) {
        return registroActividadRealizadaDao.findProyectoByTipoActividad(idTipoActividad);
    }
    public List<Object[]> obtenerDetallesPorRegistroNuevo(Long idRegistro) {
        return registroActividadRealizadaDao.findDetallesPorRegistroNuevo(idRegistro);
    }
    public List<Object[]> obtenerVoluntariosPorActividad(Long idRegistro) {
        return registroActividadRealizadaDao.findVoluntariosPorActividad(idRegistro);
    }
    
   List<Object[]> findInfo_RegistroRealizado(Long voluntarioId, Long RegistroActividadRealizada_Id){
    	
    	return registroActividadRealizadaDao.findInfo_RegistroRealizado(voluntarioId, RegistroActividadRealizada_Id);
    }
   
   List<Object[]> findInfo_RegistroRealizado_cumplido(Long voluntarioId, Long RegistroActividadRealizada_Id){
	   return registroActividadRealizadaDao.findInfo_RegistroRealizado_cumplido(voluntarioId, RegistroActividadRealizada_Id);
   }
    
    
	public List<Asignar_equipos> listarvoluntariosporequipos (Long idvolunta){
		return registroActividadRealizadaDao.listarvoluntariosporequipos(idvolunta);
	}
	public List<Usuarios> listarvoluntariosUsuarios(Long idusu){
		return registroActividadRealizadaDao.listarvoluntariosUsuarios(idusu);
	}
	
	
	public List<RegistroActividadRealiza> FiltroActividades(Long id_administrador,Long id_proyecto){
		return registroActividadRealizadaDao.FiltroActividades(id_administrador, id_proyecto);
	}

}

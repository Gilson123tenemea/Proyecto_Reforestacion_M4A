package com.example.demo.service;

import com.example.demo.dao.IRegistroActividadRealizadaDao;
import com.example.demo.entity.RegistroActividadRealiza;

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
}

package com.example.demo.service;

import com.example.demo.dao.IRegistroActividadRealizadaDao;
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
}

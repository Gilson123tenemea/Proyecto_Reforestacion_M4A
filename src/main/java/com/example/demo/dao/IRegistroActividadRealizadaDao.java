package com.example.demo.dao;

import com.example.demo.entity.RegistroActividadRealiza;
import java.util.List;
import java.util.Optional;

public interface IRegistroActividadRealizadaDao {
    List<RegistroActividadRealiza> findByVoluntarioId(Long voluntarioId);
    List<Object[]> findActividadesRealizadas(Long voluntarioId);
    Optional<Object[]> findDetalleActividadById(Long actividadId);
}

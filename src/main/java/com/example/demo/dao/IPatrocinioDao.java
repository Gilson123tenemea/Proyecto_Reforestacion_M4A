package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Patrocinio;

public interface IPatrocinioDao {
	public List<Patrocinio> findAll();
	public void save(Patrocinio patrocinio);
	public Patrocinio findOne (Long id);
	public void delete(Long id);
    @Query("SELECT p FROM Patrocinio p WHERE p.id_proyecto = :idProyecto")
    List<Patrocinio> findByIdProyecto(@Param("idProyecto") Long idProyecto);
    
    @Query("SELECT p FROM Patrocinio p JOIN FETCH p.patrocinador WHERE p.id_proyecto = :idProyecto")
    public List<String> findNombresEmpresasByIdProyecto(Long idProyecto);
}

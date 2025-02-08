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
    List<Patrocinio> findByIdProyecto(@Param("idProyecto") Long idProyecto);
    public List<String> findNombresEmpresasByIdProyecto(Long idProyecto);
    
    
}

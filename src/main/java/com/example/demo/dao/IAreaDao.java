package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Area;

public interface IAreaDao {
   
	public List<Area> findAll();
	public void save(Area area);
	public Area findOne(Long id);
	public void delete(Long id);
	List<Area> findByProyectoIdAdministrador(Long adminId); 
	long countParcelasByAreaId(Long idArea);
}

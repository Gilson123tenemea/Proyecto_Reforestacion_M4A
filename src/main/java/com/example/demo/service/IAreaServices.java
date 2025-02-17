package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Area;


public interface IAreaServices {
	public List<Area> findAll();
	public void save(Area area);
	public Area findOne(Long id);
	public void delete(Long id);
	public List<Area> listarAreas(); 
	public String findProyectoNameByAreaId(Long idArea);
	List<Area> findByProyectoIdAdministrador(Long adminId);
	long countParcelasByAreaId(Long idArea);
}

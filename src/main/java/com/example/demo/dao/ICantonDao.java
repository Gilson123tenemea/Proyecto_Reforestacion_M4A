package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Canton;

public interface ICantonDao {
	
	public List<Canton> findAll();
	public void save(Canton canton);
	public Canton findOne(Long id);
	public void delete(Long id);
    public List<Canton> findByProvincia(Long idProvincia);

}

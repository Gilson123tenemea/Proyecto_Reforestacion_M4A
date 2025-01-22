package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Parroquia;

public interface IParroquiaDao {
	
	public List<Parroquia> findAll();
	public void save(Parroquia parroquia);
	public Parroquia findOne(Long id);
	public void delete(Long id);

}

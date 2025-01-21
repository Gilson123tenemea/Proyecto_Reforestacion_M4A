package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Usuarios;

public interface IUsuariosDao {
	public List<Usuarios> findAll();
	public void save(Usuarios usuarios);
	public Usuarios findOne (Long id);
	public void delete(Long id);

}

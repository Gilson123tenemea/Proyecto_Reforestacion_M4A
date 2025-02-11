package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;

public interface IUsuariosDao {
	public List<Usuarios> findAll();
	public void save(Usuarios usuarios);
	public Usuarios findOne (Long id);
	public void delete(Long id);
	public List<Object[]> NumeroUsuariosProvincia(Long id_proyecto);

}

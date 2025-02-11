package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Usuarios;


public interface IUsuarioServices {
	public List<Usuarios> findAll();
	public void save(Usuarios usuario);
	public Usuarios findOne(Long id);
	public void delete(Long id);
	public List<Object[]> NumeroUsuariosProvincia(Long id_proyecto);

}

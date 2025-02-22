package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Parroquia;

public interface IParroquiaService {

	public List<Parroquia> findAll();
	public void save(Parroquia parroquia);
	public Parroquia findOne(Long id);
	public void delete(Long id);
	public List<Parroquia> findByCanton(Long idCanton);
	long countUsuariosByParroquiaId(Long idParroquia);
    long countProyectosByParroquiaId(Long idParroquia);
}

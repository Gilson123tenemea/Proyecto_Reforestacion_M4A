package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Administrador;

public interface IAdministradorDao {

	public List<Administrador> findAll();
	public void save(Administrador administrador);
	public Administrador findOne(Long id);
	public void delete(Long id);
	List<Administrador> findAdministradoresWithUsuarios(Long idAdministrador);
	long countProyectosByAdministradorId(Long idAdministrador);
    long countEquiposByAdministradorId(Long idAdministrador);
    long countTipoActividadesByAdministradorId(Long idAdministrador);

}

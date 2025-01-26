package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.IVoluntarioDao;

import com.example.demo.entity.Usuarios;

import com.example.demo.entity.Tipo_Actividades;

import com.example.demo.entity.Voluntarios;
@Service
public class VoluntariosServiceImpl implements IVoluntariosService {

	@Autowired
	private IVoluntarioDao voluntarioDao;
	
	@Autowired
    private IUsuarioServices usuariosService; 
	
	@Transactional(readOnly=true)
	@Override
	public List<Voluntarios> findAll() {
		
		return voluntarioDao.findAll();
	}

	@Transactional
	@Override
	public void save(Voluntarios voluntarios) {
		voluntarioDao.save(voluntarios);
	}

	@Transactional(readOnly=true)
	@Override
	public Voluntarios findOne(Long id) {
		
		return voluntarioDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		voluntarioDao.delete(id);
		
	}
	
	 @Transactional(readOnly = true)
	    public List<Voluntarios> findAdministradoresWithUsuarios(Long iVoluntario) {
	        return voluntarioDao.findAdministradoresWithUsuarios(iVoluntario);
	    }
	 
	 @Transactional(readOnly = true)
		@Override
		public List<Voluntarios> listarvoluntarios() {
			return 	voluntarioDao.findAll();
		}

}

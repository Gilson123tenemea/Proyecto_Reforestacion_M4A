package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.IAsignacion_proyectoActiDao;
import com.example.demo.entity.Asignacion_proyectoActi;
@Service
public class Asignacion_proyectoActiServiceImpl implements IAsignacion_proyectoActiService {

	@Autowired
	private IAsignacion_proyectoActiDao asg_proyectoDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Asignacion_proyectoActi> findAll() {
		
		return asg_proyectoDao.findAll();
	}

	@Transactional
	@Override
	public void save(Asignacion_proyectoActi asignacion_proyecto) {
		asg_proyectoDao.save(asignacion_proyecto);
	}

	@Transactional(readOnly=true)
	@Override
	public Asignacion_proyectoActi findOne(Long id) {
		
		return asg_proyectoDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		asg_proyectoDao.delete(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignacion_proyectoActi> findByAdministradorId(Long idAdministrador) {
		return asg_proyectoDao.findByAdministradorId(idAdministrador);
	}
}

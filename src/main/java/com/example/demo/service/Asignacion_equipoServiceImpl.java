package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.IAsignacion_equipoDao;
import com.example.demo.entity.Asignacion_equipo;

@Service
public class Asignacion_equipoServiceImpl implements IAsignacion_equipoService {

	@Autowired
	private IAsignacion_equipoDao asg_equipoDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Asignacion_equipo> findAll() {
		
		return asg_equipoDao.findAll();
	}

	@Transactional
	@Override
	public void save(Asignacion_equipo asignacion_equipos) {
		asg_equipoDao.save(asignacion_equipos);
	}

	@Transactional(readOnly=true)
	@Override
	public Asignacion_equipo findOne(Long id) {
		
		return asg_equipoDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		asg_equipoDao.delete(id);
		
	}

}

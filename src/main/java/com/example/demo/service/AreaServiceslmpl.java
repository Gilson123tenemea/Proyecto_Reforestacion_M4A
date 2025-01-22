package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAreaDao;
import com.example.demo.entity.Area;

@Service
public class AreaServiceslmpl implements IAreaServices{
	
	@Autowired
	private IAreaDao areadao;

	@Transactional(readOnly = true)
	@Override
	public List<Area> findAll() {
		return areadao.findAll();
	}

	@Transactional
	@Override
	public void save(Area area) {
		areadao.save(area);
	}

	@Transactional(readOnly = true)
	@Override
	public Area findOne(Long id) {
		return areadao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		areadao.delete(id);
	}

}

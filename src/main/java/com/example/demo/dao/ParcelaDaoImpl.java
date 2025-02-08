package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Parcelas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ParcelaDaoImpl implements IParcelaDao{

	@PersistenceContext
	private EntityManager en;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Parcelas> findAll() {
		// TODO Auto-generated method stub
		return en.createQuery("from Parcelas").getResultList();
	}

	
	@Transactional
	@Override
	public void save(Parcelas parcela) {
		// TODO Auto-generated method stub
		if(parcela.getId_parcelas() != null && parcela.getId_parcelas()>0) {
			en.persist(parcela);
		}else {
			en.merge(parcela);
		}
		
	}

	@Transactional(readOnly = true)
	@Override
	public Parcelas findOne(Long id) {
		// TODO Auto-generated method stub
		return en.find(Parcelas.class, id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		en.remove(findOne(id));
		
	}


	@Override
	public List<Parcelas> findByAreaId(Long idArea) {
		return en.createQuery("SELECT p FROM Parcelas p WHERE p.id_area = :idArea", Parcelas.class)
                .setParameter("idArea", idArea)
                .getResultList();
		
	}


	@Override
	public List<Parcelas> findByAdministradorId(Long adminId) {
		  return en.createQuery(
			        "SELECT p FROM Parcelas p WHERE p.id_area IN " +
			        "(SELECT a.id_area FROM Area a WHERE a.id_proyecto IN " +
			        "(SELECT pr.id_proyecto FROM Proyecto pr WHERE pr.id_administrador = :adminId))", 
			        Parcelas.class)
			        .setParameter("adminId", adminId)
			        .getResultList();
	}

	
	
	
	

}

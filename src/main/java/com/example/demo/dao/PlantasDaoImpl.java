package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Plantas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PlantasDaoImpl implements IPlantasDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Plantas> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Plantas").getResultList();
	}

	@Override
	public void save(Plantas plantas) {
		if(plantas.getId_plantas()!=null && plantas.getId_plantas()>0) {
			em.merge(plantas);
		}else {
			em.persist(plantas);
		}

	}

	@Override
	public Plantas findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Plantas.class, id);
	}

	@Override
	public void delete(Long id) {
		
		em.remove(findOne(id));

	}
	
	@Override
	public long countParcelasByPlantaId(Long idPlanta) {
	    return em.createQuery("SELECT COUNT(p) FROM Parcelas p WHERE p.id_plantas = :idPlanta", Long.class)
	             .setParameter("idPlanta", idPlanta)
	             .getSingleResult();
	}

}

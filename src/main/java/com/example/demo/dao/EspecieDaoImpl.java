package com.example.demo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Especie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class EspecieDaoImpl implements IEspecieDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Especie> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Especie").getResultList();
	}

	@Override
	public void save(Especie especie) {
		if (especie.getId_especie() != null
				&& especie.getId_especie() > 0) {
			em.merge(especie);
		} else {
			em.persist(especie);
		}

	}

	@Override
	public Especie findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Especie.class, id);
	}

	@Override
	public void delete(Long id) {
		em.remove(findOne(id));

	}
	
	@Override
	public Especie findByNombre(String nombre) {
	    try {
	        TypedQuery<Especie> query = em.createQuery(
	            "SELECT e FROM Especie e WHERE LOWER(e.nombre) = LOWER(:nombre)", Especie.class);
	        query.setParameter("nombre", nombre.toLowerCase());
	        List<Especie> resultados = query.getResultList();
	        
	        // Devuelve el primer resultado si existe, o null si la lista está vacía
	        return resultados.isEmpty() ? null : resultados.get(0);
	    } catch (Exception e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error en findByNombre: ", e);
	        return null;
	    }
	}

}

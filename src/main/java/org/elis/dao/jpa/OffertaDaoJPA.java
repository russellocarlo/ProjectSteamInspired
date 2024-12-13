package org.elis.dao.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.dao.OffertaDao;
import org.elis.model.Gioco;
import org.elis.model.Offerta;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class OffertaDaoJPA implements OffertaDao{

private static OffertaDaoJPA instance;
	
	public static OffertaDaoJPA getInstance() {
		if(instance == null) {
			instance = new OffertaDaoJPA();
		}
		return instance;
	}
	
	@Override
	public Offerta add(String nome, double sconto, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		 
		Offerta o = new Offerta();
		o.setNome(nome);
		o.setSconto(sconto);
		o.setData_inizio(data_inizio);
		o.setData_fine(data_fine);
		
		t.begin();
		em.persist(o);
		t.commit();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Offerta> getAll() {
		try(EntityManager em = JPADaoFactory.getEntityManager();){
			Query q = em.createQuery("select o from Offerta o");
		return q.getResultList();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Offerta getByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Offerta.class, nome);
	}

	@Override
	public Offerta updateNome(long id, String nome) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Offerta o = null;
	    
	    
	        t.begin();
	        
	        o = em.find(Offerta.class, id);
	        
	        if (o != null) {
	            o.setNome(nome);  
	            em.merge(o);      
	        }
	        
	        t.commit();
	        
	 
	    
	    return o;
	}

	@Override
	public Offerta deleteById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Offerta o = null;
	    
	    try {
	        t.begin();
	        
	        o = em.createQuery("SELECT o FROM Offerta o WHERE o.id = :id", Offerta.class)
	                .setParameter("id", id)
	                .getSingleResult();
	        
	        if (o != null) {
	            em.remove(o);
	        }
	       
	        t.commit();
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return o;
	}

	@Override
	public Offerta getById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Offerta.class, id);
	}

	@Override
	public Offerta updateSconto(long id, double sconto) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Offerta o = null;
	    
	    try {
	        t.begin();
	        
	        o = em.find(Offerta.class, id);
	        
	        if (o != null) {
	            o.setSconto(sconto);  
	            em.merge(o);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return o;
	}

	@Override
	public Offerta updateData(long id, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Offerta o = null;
	    
	    try {
	        t.begin();
	        
	        o = em.find(Offerta.class, id);
	        
	        if (o != null) {
	            o.setData_inizio(data_inizio);
	            o.setData_fine(data_fine);
	            em.merge(o);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return o;
	}

	@Override
	public Offerta deleteByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Offerta o = null;
	    
	    try {
	      
	        
	        o = em.createQuery("SELECT o FROM Offerta o WHERE o.nome = :nome", Offerta.class)
	                .setParameter("nome", nome)
	                .getSingleResult();
	        
	        if (o != null) {
	        	t.begin();
	        	
	        	for(Gioco gioco : o.getGiochi()) {
	        		gioco.setOfferta(null);
	        	}
	        	t.commit();
	        	t.begin();
	            em.remove(o);
	            t.commit();
	        }
	       
	       
	        
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    } 
	    
	    return o;
	}

}

package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.GenereDao;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Offerta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class GenereDaoJPA implements GenereDao{
	
private GenereDaoJPA() {}
	
	private static GenereDaoJPA instance;
	
	public static GenereDaoJPA getInstance() {
		if(instance == null) {
			instance = new GenereDaoJPA();
		}
		return instance;
	}

	@Override
	public Genere add(String nome, Offerta offerta) throws Exception{
		
		Genere g = new Genere();
		
		g.setNome(nome);
		g.setOfferta(offerta);
		
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(g);
		t.commit();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Genere> getAll() {
		try(EntityManager em = JPADaoFactory.getEntityManager();){
			Query q = em.createQuery("select g from Genere g");
		return q.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

	@Override
	public Genere getByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
	    try {
	        
	        Query query = em.createQuery("SELECT g FROM Genere g WHERE g.nome = :nome");
	        query.setParameter("nome", nome);
	        return  (Genere) query.getSingleResult();
	    } catch (NoResultException e) {
	        e.printStackTrace();
	        return null;
	    } 
	}

	@Override
	public Genere getById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Genere.class, id);
	}

	@Override
	public Genere updateName(long id, String nuovoNome) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Genere g = null;
	    
	    
	        t.begin();
	        
	        g = em.find(Genere.class, id);
	        
	        if (g != null) {
	            g.setNome(nuovoNome);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	       
	    
	    return g;
	}

	@Override
	public Genere updateOfferta(long id, Offerta offerta) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Genere g = null;
	    
	   
	        t.begin();
	        
	        g = em.find(Genere.class, id);
	        
	        if (g != null) {
	            g.setOfferta(offerta);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	   
	    
	    return g;
	}

	@Override
	public Genere deleteByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Genere g = null;
	    
	    try {
	        
	        
	        g = em.createQuery("SELECT g FROM Genere g WHERE g.nome = :nome", Genere.class)
	                .setParameter("nome", nome)
	                .getSingleResult();
	        
	        if (g != null) {
	        	t.begin();
	        	for(Gioco gioco : g.getGiochiCategoria()) {
	        		gioco.getGeneri().remove(g);
	        	}
	        	t.commit();
	        	t.begin();
	            em.remove(g);
	            t.commit();
	        }
	       
	        
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return g;
	}

	@Override
	public Genere addGenereGiocoById(long id_genere, long id_gioco) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		
		
			t.begin();
			Genere genere = em.find(Genere.class, id_genere);
			Gioco gioco = em.find(Gioco.class, id_gioco);
			gioco.getGeneri().add(genere);
			t.commit();
		
		return null;
	}

}

package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.RecensioneDao;
import org.elis.model.Gioco;
import org.elis.model.Recensione;
import org.elis.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class RecensioneDaoJPA implements RecensioneDao{

private static RecensioneDaoJPA instance;
	
	public static RecensioneDaoJPA getInstance() {
		if(instance == null) {
			instance = new RecensioneDaoJPA();
		}
		return instance;
	}
	
	@Override
	public Recensione add(int voto, String testo, long id_utente, long id_gioco) throws Exception{
		 
		try( 
		 EntityManager em = JPADaoFactory.getEntityManager();
		 ){
			EntityTransaction t = em.getTransaction();
		    Recensione recensione = new Recensione();
		    
		   
		        t.begin();
		        
		        Utente utente = em.find(Utente.class, id_utente);
		        Gioco gioco = em.find(Gioco.class, id_gioco);
		        
		        if (utente != null && gioco != null) {
		            recensione.setVoto(voto);
		            recensione.setTesto(testo);
		            recensione.setUtente(utente);  
		            recensione.setGioco(gioco);    
		            
		            em.persist(recensione);
		        }
		        
		        t.commit();
		
		    
		    return recensione;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recensione> getAll() {
		EntityManager em = JPADaoFactory.getEntityManager();
		Query q = em.createQuery("select r from Recensione r");
		return q.getResultList();
	}

	@Override
	public Recensione getById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Recensione.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recensione> getByUtente(long id_utente) {
		EntityManager em = JPADaoFactory.getEntityManager();
		List<Recensione> recensioni= null;
		try {	
		Query q = em.createQuery("select r from Recensione r where r.id_utente = :id_utente", Recensione.class);
		 q.setParameter("id_utente", id_utente);
	        
	        recensioni = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    
	    return recensioni;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recensione> getRecensioniByGioco(long id_gioco) {
		
		List<Recensione> recensioni= null;
		try (EntityManager em = JPADaoFactory.getEntityManager();){	
		Query q = em.createQuery("select r from Recensione r where r.gioco.id = :id_gioco", Recensione.class);
		 q.setParameter("id_gioco", id_gioco);
	        
	        recensioni = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return recensioni;
	}
	
	@Override
	public boolean giocoRecensito(long id_gioco, long id_utente) {
	    try (EntityManager em = JPADaoFactory.getEntityManager()) {
	        EntityTransaction t = em.getTransaction();
	        t.begin();

	        try {
	            Recensione recensione = em.createQuery(
	                    "SELECT r FROM Recensione r WHERE r.gioco.id = :id_gioco AND r.utente.id = :id_utente", Recensione.class)
	                .setParameter("id_gioco", id_gioco)
	                .setParameter("id_utente", id_utente)
	                .getSingleResult();

	            t.commit();
	            if(recensione != null) {return true;};
	        } catch (NoResultException e) {
	            // Nessuna recensione trovata per l'utente e il gioco specificati
	            t.commit();
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    return false;
	}


	@Override
	public Recensione updateVoto(long id, int voto) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Recensione r = null;
	    
	    try {
	        t.begin();
	        
	        r = em.find(Recensione.class, id);
	        
	        if (r != null) {
	            r.setVoto(voto);  
	            em.merge(r);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    } finally {
	        em.close();
	    }
	    
	    return r;
	}

	@Override
	public Recensione updateTesto(long id, String testo) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Recensione r = null;
	    
	    try {
	        t.begin();
	        
	        r = em.find(Recensione.class, id);
	        
	        if (r != null) {
	            r.setTesto(testo);  
	            em.merge(r);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return r;
	}
	
	

	@Override
	public Recensione deleteById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Recensione r = null;
	    
	    try {
	        t.begin();
	        
	        r = em.createQuery("SELECT r FROM Recensione r WHERE r.id = :id", Recensione.class)
	                .setParameter("id", id)
	                .getSingleResult();
	        
	        if (r != null) {
	            em.remove(r);
	        }
	       
	        t.commit();
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    } finally {
	        em.close();
	    }
	    
	    return r;
	}

}

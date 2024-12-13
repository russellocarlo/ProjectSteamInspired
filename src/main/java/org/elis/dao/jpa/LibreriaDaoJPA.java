package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.LibreriaDao;
import org.elis.model.Gioco;
import org.elis.model.Libreria;
import org.elis.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class LibreriaDaoJPA implements LibreriaDao{

private static LibreriaDaoJPA instance;
	
	public static LibreriaDaoJPA getInstance() {
		if(instance == null) {
			instance = new LibreriaDaoJPA();
		}
		return instance;
	}
	
	@Override
	public Libreria add(String nome, Utente utente) throws Exception{
		Libreria l = new Libreria();
		l.setNome(nome);
		l.setUtente(utente);
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(l);
		t.commit();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Libreria> getAll() {
		EntityManager em = JPADaoFactory.getEntityManager();
		Query q = em.createQuery("select l from Libreria l");
		return q.getResultList();
	}

	@Override
	public Libreria getByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Libreria.class, nome);
	}

	@Override
	public Libreria updateName(long id, String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Libreria l = null;
	    
	    try {
	        t.begin();
	        
	        l = em.find(Libreria.class, id);
	        
	        if (l != null) {
	            l.setNome(nome);  
	            em.merge(l);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return l;
	}
	

	@Override
	public Libreria deleteGiocoLibreria(long id_libreria, long id_gioco) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
	        Libreria libreria = em.find(Libreria.class, id_libreria);
	        Gioco gioco = em.find(Gioco.class, id_gioco);
	        
	        if (libreria != null && gioco != null) {
	            t.begin();
	            
	            libreria.getGiochi().remove(gioco);
	            
	            em.persist(libreria);
	            t.commit();
	        }
	        
	        return libreria;
	    } catch (Exception e) {
	        e.printStackTrace();
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        return null;
	    }
	}

	@Override
	public Libreria deleteByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Libreria l = null;
	    
	    try {
	        t.begin();
	        
	        l = em.createQuery("SELECT l FROM Libreria l WHERE l.nome = :nome", Libreria.class)
	                .setParameter("nome", nome)
	                .getSingleResult();
	        
	        if (l != null) {
	            em.remove(l);
	        }
	       
	        t.commit();
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Libreria> getLibrerieByUtente(long id_utente) {
		EntityManager em = JPADaoFactory.getEntityManager();
		@SuppressWarnings("unused")
		List<Libreria> librerie= null;
		try {
	        Query query = em.createQuery("SELECT l FROM Libreria l WHERE l.utente.id = :id_utente", Libreria.class);
	        query.setParameter("id_utente", id_utente);
	        return query.getResultList();
	    } catch (NoResultException e) {
	        return null;
	        }
	}

	@Override
	public Libreria addGiocoAITuoiGiochi(Utente utente, long id_gioco) {
		EntityManager em = JPADaoFactory.getEntityManager();
	    EntityTransaction t = em.getTransaction();
	    
	    try {
	    	
	    	String query = "SELECT l FROM Libreria l WHERE l.utente.id = :id_utente AND l.nome = :nome";
	        Libreria libreria = em.createQuery(query, Libreria.class)
	                              .setParameter("id_utente", utente.getId())
	                              .setParameter("nome", "Libreria di " + utente.getId())
	                              .getSingleResult();
	        
	        Gioco gioco = em.find(Gioco.class, id_gioco);
	        
	        if (libreria != null && gioco != null) {
	        	t.begin();
	        	libreria.getGiochi().add(gioco);
	        	em.persist(libreria);
	        	t.commit();
	        }
	        return libreria;
	    }catch (Exception e) {
	        e.printStackTrace();
	        if (t.isActive()) {
	            t.rollback();
	        }
	        return null;
	    }
	}

	@Override
	public Libreria getLibreriaByUtente(long id_utente) {
		EntityManager em = JPADaoFactory.getEntityManager();
		Libreria librerie= null;
		try {
	        Query query = em.createQuery("SELECT l FROM Libreria l WHERE l.utente.id = :id_utente", Libreria.class);
	        query.setParameter("id_utente", id_utente);
	        return (Libreria) query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	        }
	}

}

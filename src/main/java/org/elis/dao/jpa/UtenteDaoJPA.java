package org.elis.dao.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.dao.UtenteDao;
import org.elis.model.Libreria;
import org.elis.model.Recensione;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class UtenteDaoJPA implements UtenteDao{

private static UtenteDaoJPA instance;
	
	public static UtenteDaoJPA getInstance() {
		if(instance == null) {
			instance = new UtenteDaoJPA();
		}
		return instance;
	}
	
	@Override
	public Utente add(String username, String email, String password, LocalDateTime data_nascita, Ruolo ruolo) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = new Utente();
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password);
		u.setDataNascita(data_nascita);
		u.setRuolo(ruolo);
		t.begin();
		em.persist(u);
		em.flush(); //controllo sulla creazione dell'utente
		
		Libreria libreria = new Libreria();
        libreria.setNome("Libreria di " + u.getId());
        libreria.setUtente(u);
        em.persist(libreria);
        
		t.commit();
		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Utente> getAll() {
		EntityManager em = JPADaoFactory.getEntityManager();
		Query q = em.createQuery("select u from Utenteug");
		return q.getResultList();
	}

	@Override
	public Utente getByUsername(String username) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Utente.class, username);
	}

	@Override
	public Utente updateUsername(long id, String username) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = null;
	    
	        t.begin();
	        
	        u = em.find(Utente.class, id);
	        
	        if (u != null) {
	            u.setUsername(username);  
	            em.merge(u);      
	        }
	        
	        t.commit();
	        
	    
	    return u;
	}

	@Override
	public Utente deleteByUsername(String username) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = null;
	    
	    try {
	        
	        
	        u = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username", Utente.class)
	                .setParameter("username", username)
	                .getSingleResult();
	        
	        if (u != null) {
	        	t.begin();
	        	
	        	for(Recensione r : u.getRecensioni()) {
	        		r.setUtente(null);
	        	}
	        	
	        	t.commit();
	        	
	        	t.begin();
	        	
	        	for(Libreria l : u.getLibrerie()) {
	        		l.setUtente(null);
	        	}
	        	
	        	t.commit();
	        	
		        t.begin();
		        u = em.merge(u);
		        em.remove(u);
		        t.commit();
	        }
	       
	        
	        
	    } catch (Exception e) {
	        
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }
	    
	    return u;
	}

	@Override
	public Utente deleteById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = null;
	    
	    try {
	        t.begin();
	        
	        u = em.createQuery("SELECT u FROM Utente u WHERE u.id = :id", Utente.class)
	                .setParameter("id", id)
	                .getSingleResult();
	        
	        if (u != null) {
	            em.remove(u);
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
	    
	    return u;
	}

	@Override
	public Utente getById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		return em.find(Utente.class, id);
	}

	@Override
	public Utente updateEmail(long id, String email) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = null;
	    
	  
	        t.begin();
	        
	        u = em.find(Utente.class, id);
	        
	        if (u != null) {
	            u.setEmail(email);  
	            em.merge(u);      
	        }
	        
	        t.commit();
	    
	    return u;
	}

	@Override
	public Utente updatePassword(long id, String password) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Utente u = null;
	    
	    try {
	        t.begin();
	        
	        u = em.find(Utente.class, id);
	        
	        if (u != null) {
	            u.setPassword(password);  
	            em.merge(u);      
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
	    
	    return u;
	}

	@Override
	public Utente loginUtente(String usernameLogin, String passwordLogin) {
		EntityManager em = JPADaoFactory.getEntityManager();
	    Utente u = null;
	    
	    try {
	        u = em.createQuery("SELECT u FROM Utente u WHERE (u.username = :username or u.email = :email) AND u.password = :password", Utente.class)
	                .setParameter("username", usernameLogin)
	                .setParameter("email", usernameLogin)
	                .setParameter("password", passwordLogin)
	                .getSingleResult();
	    } catch (NoResultException e) {
	        // Nessun utente trovato con le credenziali fornite
	        return null;
	    } finally {
	        em.close();
	    }
	    
	    return u;
	}

	@Override
	public boolean emailOrUsernameExists(String email, String username) {
		EntityManager em = JPADaoFactory.getEntityManager();
	    long count = 0;
	    
	    try {
	        count = em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.email = :email OR u.username = :username", Long.class)
	                .setParameter("email", email)
	                .setParameter("username", username)
	                .getSingleResult();
	    } finally {
	        em.close();
	    }
	    
	    return count > 0;
	}

	@Override
	public boolean emailExists(String email) {
		 EntityManager em = JPADaoFactory.getEntityManager();
		    long count = 0;
		    
		    try {
		        count = em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.email = :email", Long.class)
		                .setParameter("email", email)
		                .getSingleResult();
		    } finally {
		        em.close();
		    }
		    
		    return count > 0;
	}

	@Override
	public boolean usernameExists(String username) {
		EntityManager em = JPADaoFactory.getEntityManager();
	    long count = 0;
	    
	    try {
	        count = em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.username = :username", Long.class)
	                .setParameter("username", username)
	                .getSingleResult();
	    } finally {
	        em.close();
	    }
	    
	    return count > 0;
	}

}

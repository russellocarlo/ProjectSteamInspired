package org.elis.dao.jpa;
import java.time.LocalDateTime;
import java.util.List;
import org.elis.dao.GiocoDao;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Offerta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class GiocoDaoJPA implements GiocoDao{
	
private GiocoDaoJPA() {}
	
	private static GiocoDaoJPA instance;
	
	public static GiocoDaoJPA getInstance() {
		if(instance == null) {
			instance = new GiocoDaoJPA();
		}
		return instance;
	}

	@Override
	public Gioco add(Gioco gioco) throws PersistenceException{
		try(EntityManager em = JPADaoFactory.getEntityManager();
		){
			EntityTransaction t = em.getTransaction();
			t.begin();
		em.persist(gioco);
		t.commit();
		}
		
		return null;
		
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gioco> getAll() {
		EntityManager em = JPADaoFactory.getEntityManager();
		Query q = em.createQuery("select g from Gioco g where g.eliminato=false");
		return q.getResultList();
	}

	@Override
	public Gioco getByName(String nome) {
		try(EntityManager em = JPADaoFactory.getEntityManager();){
			Gioco g = em.find(Gioco.class, nome);
			if(g.isEliminato())return null;
			return g;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Gioco updateName(long id, String nome) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	   
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setNome(nome);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	    
	    return g;
	}

	@Override
	public Gioco deleteByName(String nome) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	    try {
	        t.begin();
	        g = em.createQuery("SELECT g FROM Gioco g WHERE g.nome = :nome", Gioco.class)
	                .setParameter("nome", nome)
	                .getSingleResult();
	        
	        if (g != null) {
	            em.remove(g);
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
	    
	    return g;
	}

	@Override
	public Gioco getById(long id) {
		try(EntityManager em = JPADaoFactory.getEntityManager();){
			Gioco g = em.find(Gioco.class, id);
			
			g.getGeneri();
			return g;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

	@Override
	public Gioco deleteById(long id) {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	    try {
	    	t.begin();
	        g = em.find(Gioco.class,id);
	        
	        if (g != null) {
	        	
	        	g.setEliminato(true);
	           
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
	    
	    return g;
	}

	@Override
	public Gioco updateDataRilascio(long id, LocalDateTime nData_rilascio) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	  
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setData_rilascio(nData_rilascio);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	    return g;
	}

	@Override
	public Gioco updateDescrizione(long id, String descrizione) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setDescrizione(descrizione);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	    
	    return g;
	}

	@Override
	public Gioco updatePrezzo(long id, double prezzo) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	    
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setPrezzo(prezzo);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	    
	    return g;
	}

	@Override
	public Gioco updateImmagine(long id, byte[] byteImmagine) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	   
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setByteImmagine(byteImmagine);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	    return g;
	}

	@Override
	public Gioco updateOfferta(long id, Offerta offerta) throws Exception{
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
	    
	        t.begin();
	        
	        g = em.find(Gioco.class, id);
	        
	        if (g != null) {
	            g.setOfferta(offerta);  
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	    return g;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gioco> getGiochiByLibreria(long id_libreria) {
		EntityManager em = JPADaoFactory.getEntityManager();
		List<Gioco> giochi= null;
		try {	
		Query q = em.createQuery("SELECT g FROM Gioco g JOIN g.librerie libreria WHERE libreria.id = :id_libreria", Gioco.class);
		 q.setParameter("id_libreria", id_libreria);
	        
	        giochi = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    
	    return giochi;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gioco> getGiochiPubblicati(long id_utente) {
		EntityManager em = JPADaoFactory.getEntityManager();
		List<Gioco> giochi= null;
		try {	
		Query q = em.createQuery("select g from Gioco g where g.id_utente = :id_utente", Gioco.class);
		 q.setParameter("id_utente", id_utente);
	        
	        giochi = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return giochi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gioco> getGiochiByGenere(long id_genere) {
		EntityManager em = JPADaoFactory.getEntityManager();
		List<Gioco> giochi= null;
		try {	
		Query q = em.createQuery("SELECT g FROM Gioco g JOIN g.generi genere WHERE genere.id = :id_genere and g.eliminato=false", Gioco.class);
		 q.setParameter("id_genere", id_genere);
	        
	        giochi = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return giochi;
	}

	@Override
	public Gioco updateGenereGioco(List<Genere> listaGeneri, long idGioco) throws Exception {
		EntityManager em = JPADaoFactory.getEntityManager();
		EntityTransaction t = em.getTransaction();
		Gioco g = null;
		
	    
	    try {
	        t.begin();
	        g = em.find(Gioco.class, idGioco);
	        
	        if (g != null) {
	            g.setGeneri(listaGeneri);
	            em.merge(g);      
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive()) {
	            t.rollback();
	        }
	        e.printStackTrace(); 
	    }   
	    return g;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gioco> getGiochiByName(String name) {
	    EntityManager em = JPADaoFactory.getEntityManager();
	    List<Gioco> giochi = null;
	    
	    try {
	        Query q = em.createQuery("SELECT g FROM Gioco g WHERE g.nome LIKE :name", Gioco.class);
	        
	        q.setParameter("name", "%" + name + "%");

	        giochi = q.getResultList();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    return giochi;
	}

}
package org.elis.dao.jpa;

import org.elis.dao.DaoFactory;
import org.elis.dao.GenereDao;
import org.elis.dao.GiocoDao;
import org.elis.dao.LibreriaDao;
import org.elis.dao.OffertaDao;
import org.elis.dao.RecensioneDao;
import org.elis.dao.UtenteDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPADaoFactory extends DaoFactory{
	
	private static EntityManagerFactory entityManagerFactory;
	
	public static EntityManager getEntityManager() {
		if(entityManagerFactory==null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("ProgettoSteam");
		}
		return entityManagerFactory.createEntityManager();
	}

	@Override
	public UtenteDao getUtenteDao() {
		return UtenteDaoJPA.getInstance();
	}

	@Override
	public GiocoDao getGiocoDao() {
		return GiocoDaoJPA.getInstance();
	}

	@Override
	public LibreriaDao getLibreriaDao() {
		return LibreriaDaoJPA.getInstance();
	}

	@Override
	public GenereDao getGenereDao() {
		return GenereDaoJPA.getInstance();
	}

	@Override
	public RecensioneDao getRecensioneDao() {
		return RecensioneDaoJPA.getInstance();
	}

	@Override
	public OffertaDao getOffertaDao() {
		return OffertaDaoJPA.getInstance();
	}

	
	
}

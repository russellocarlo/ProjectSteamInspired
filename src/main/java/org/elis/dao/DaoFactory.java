package org.elis.dao;
import org.elis.dao.jdbc.JDBCDaoFactory;
import org.elis.dao.jpa.JPADaoFactory;

public abstract class DaoFactory {
	
	private static DaoFactory instance;
	
	
	public abstract UtenteDao getUtenteDao();
	public abstract GiocoDao getGiocoDao();
	public abstract LibreriaDao getLibreriaDao();
	public abstract GenereDao getGenereDao();
	public abstract RecensioneDao getRecensioneDao();
	public abstract OffertaDao getOffertaDao();


	public static DaoFactory getDaoFactory(String s) {
		
		if(instance == null) {
			switch(s) {
			case "JDBC":
				instance = new JDBCDaoFactory();
				break;
			case "JPA":
				instance = new JPADaoFactory();
				break;
			}
		}
		
		return instance;
	
		//return new JDBCDaoFactory();
	    //return new JPADaoFactory();
	}
}
package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.elis.dao.DaoFactory;
import org.elis.dao.GenereDao;
import org.elis.dao.GiocoDao;
import org.elis.dao.LibreriaDao;
import org.elis.dao.OffertaDao;
import org.elis.dao.RecensioneDao;
import org.elis.dao.UtenteDao;

public class JDBCDaoFactory extends DaoFactory{

	protected static Connection getConnection() throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/steamPezzotto","root","rootroot");
	}

	@Override
	public UtenteDao getUtenteDao() {
		return UtenteDaoJDBC.getInstance();
	}

	@Override
	public GiocoDao getGiocoDao() {
		return GiocoDaoJDBC.getInstance();
	}
	
	@Override
	public GenereDao getGenereDao() {
		return GenereDaoJDBC.getInstance();
	}
	
	public RecensioneDao getRecensioneDao() {
		return RecensioneDaoJDBC.getInstance();
	}

	@Override
	public OffertaDao getOffertaDao() {
		return OffertaDaoJDBC.getInstance();
	}

	@Override
	public LibreriaDao getLibreriaDao() {
		return LibreriaDaoJDBC.getInstance();
	}
}
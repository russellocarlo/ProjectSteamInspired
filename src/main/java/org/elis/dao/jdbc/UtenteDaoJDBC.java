package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.UtenteDao;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class UtenteDaoJDBC implements UtenteDao {

	private UtenteDaoJDBC() {}
	
	private static UtenteDaoJDBC instance;
	
	public static UtenteDaoJDBC getInstance() {
		if(instance == null) {
			instance = new UtenteDaoJDBC();
		}
		return instance;
	}
	
	@Override
	public Utente add(String username, String email, String password, LocalDateTime data_nascita, Ruolo ruolo){
		String query = "INSERT INTO Utente (Username, Email, Password, data_nascita, Ruolo) VALUES(?, ?, ?, ?,?)";
		String queryLibreria = "INSERT INTO Libreria (nome, id_utente) VALUES (?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement psLibreria = c.prepareStatement(queryLibreria)
				){
			
			ps.setInt(5, ruolo.ordinal());
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setTimestamp(4, Timestamp.valueOf(data_nascita));
			
			ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        long idUtente = -1;
	        if (generatedKeys.next()) {
	            idUtente = generatedKeys.getLong(1);
	        } else {
	            throw new SQLException("Creazione utente fallita, nessun ID utente ottenuto.");
	        }
	        
	        psLibreria.setString(1, "Libreria di " + username);
	        psLibreria.setLong(2, idUtente);
	        psLibreria.executeUpdate();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public List<Utente> getAll() {
		
		List<Utente> utenti = new ArrayList<>();
		String query = "SELECT * FROM UTENTE";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Utente u = new Utente();
					long id = rs.getLong("id");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp dataNascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime data_nascita = dataNascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(data_nascita);
					u.setRuolo(ruolo);
					utenti.add(u);
				}
				if(!utenti.isEmpty()){
					return utenti;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utente getByUsername(String username) {
		String query = "SELECT * FROM UTENTE WHERE USERNAME = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Utente u = new Utente();
					long id = rs.getLong("id");
					String username2 = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp dataNascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime data_nascita = dataNascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id);
					u.setUsername(username2);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(data_nascita);
					u.setRuolo(ruolo);
					return u;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utente updateUsername(long id, String nuovoUsername) {
		String query = "UPDATE UTENTE SET USERNAME = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, nuovoUsername);
			ps.setLong(2, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Utente updateEmail(long id, String email) {
		String query = "UPDATE UTENTE SET email = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, email);
			ps.setLong(2, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utente updatePassword(long id, String password) {
		String query = "UPDATE UTENTE SET PASSWORD = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, password);
			ps.setLong(2, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utente deleteByUsername(String username) {
				String query = "DELETE FROM UTENTE WHERE USERNAME = ?";
				
				try(
						Connection c = JDBCDaoFactory.getConnection();
						PreparedStatement ps = c .prepareStatement(query);
						)
				{
					ps.setString(1, username);
					
					ps.executeUpdate();
				} catch (SQLException e) {						
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}				
		return null;
	}

	@Override
	public Utente getById(long id) {
		String query = "SELECT * FROM UTENTE WHERE ID = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Utente u = new Utente();
					long id2 = rs.getLong("id");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp dataNascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime data_nascita = dataNascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id2);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(data_nascita);
					u.setRuolo(ruolo);
					return u;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Utente loginUtente(String usernameLogin, String passwordLogin) {
		String query = "SELECT * FROM UTENTE WHERE email = ? OR username = ? AND password = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setString(2, usernameLogin);
				ps.setString(1, usernameLogin);
				ps.setString(3, passwordLogin);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {		
						Utente u = new Utente();
						
						long id = rs.getLong("id");
						String username = rs.getString("username");
						String email = rs.getString("email");
						String password = rs.getString("password");
						Timestamp data_nascitaTimestamp = rs.getTimestamp("data_nascita");
						LocalDateTime dataNascita = data_nascitaTimestamp.toLocalDateTime();
						int roleIntValue = rs.getInt("ruolo");
						Ruolo[] ruoli = Ruolo.values();
						Ruolo ruolo = ruoli[roleIntValue];
						u.setId(id);
						u.setUsername(username);
						u.setEmail(email);
						u.setPassword(password);
						u.setDataNascita(dataNascita);
						u.setRuolo(ruolo);
						return u;
					} else {
						return null;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public boolean emailOrUsernameExists(String email, String username) {
		String query = "SELECT * FROM UTENTE WHERE email = ? OR username = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{
			ps.setString(1, email);
			ps.setString(2, username);
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean emailExists(String email) {
		String query = "SELECT * FROM utente WHERE email = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean usernameExists(String username) {
		String query = "SELECT * FROM utente WHERE username = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			return rs.next();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Utente deleteById(long id) {
		String query = "DELETE FROM UTENTE WHERE id = ?";
		
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setLong(1, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
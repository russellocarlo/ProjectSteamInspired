package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.LibreriaDao;
import org.elis.model.Libreria;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class LibreriaDaoJDBC implements LibreriaDao {
	
	private LibreriaDaoJDBC() {}
	
	private static LibreriaDaoJDBC instance;

	public static LibreriaDaoJDBC getInstance() {
		if(instance == null) {
			instance = new LibreriaDaoJDBC();
		}
		return instance;
	}

	@Override
	public Libreria add(String nome, Utente utente) {
		String query = "INSERT INTO Libreria (nome, id_utente) VALUES(?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, nome);
			ps.setLong(2, utente.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Libreria> getAll() {
		List<Libreria> librerie = new ArrayList<>();
		String query = "SELECT *"
				+ "FROM libreria"
				+ "JOIN utente"
				+ "ON libreria.id_utente = utente.id;";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Libreria l = new Libreria();
					String nome = rs.getString("nome");
					l.setNome(nome);
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					Utente u = new Utente();
					u.setId(rs.getLong("id_utente")); 
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					u.setDataNascita(rs.getTimestamp("data_nascita").toLocalDateTime());
					u.setRuolo(ruolo);
					l.setUtente(u);
					
					librerie.add(l);
				}
				if(!librerie.isEmpty()){
					return librerie;
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
	public Libreria getByName(String nome) {
		String query = "SELECT * FROM libreria WHERE nome = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setString(1, nome);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Libreria l = new Libreria();
					long id = rs.getLong("id");
					String nome2 = rs.getString("nome");
					l.setId(id);
					l.setNome(nome2);
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					Utente u = new Utente();
					u.setId(rs.getLong("id_utente")); 
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setDataNascita(rs.getTimestamp("data_nascita").toLocalDateTime());
					u.setPassword(rs.getString("password"));
					u.setRuolo(ruolo);
					l.setUtente(u);
					return l;
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
	public Libreria updateName(long id, String nome) {
		String query = "UPDATE libreria SET nome = ? WHERE id = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, nome);
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
	public Libreria deleteGiocoLibreria(long id_libreria, long id_gioco) {
		String query = "DELETE FROM libreria_gioco WHERE id_libreria = ? AND id_gioco = ? ";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			){
			
			ps.setLong(1, id_libreria);
			ps.setLong(2, id_gioco);
			
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Libreria deleteByName(String nome) {
		String query = "DELETE FROM libreria WHERE nome = ?";
		
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setString(1, nome);
			
			ps.executeUpdate();
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public List<Libreria> getLibrerieByUtente(long id_utente) {
		List<Libreria> librerie = null;
		Libreria libreria = null;
		String query = "SELECT * FROM libreria WHERE id_utente = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setLong(1, id_utente);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					long id = rs.getLong("id");
					String nome = rs.getString("nome");
					libreria.setId(id);
					libreria.setNome(nome);
					librerie.add(libreria);
					
				}
				
				return librerie;
			}
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Libreria addGiocoAITuoiGiochi(Utente utente, long id_gioco) {
		String getLibreriaQuery = "SELECT id_libreria FROM libreria WHERE id_utente = ? AND nome = ?";
		String InsertQuery = "INSERT INTO libreria_gioco (id_libreria, id_gioco) VALUES (?, ?)";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps1 = c.prepareStatement(getLibreriaQuery);
				PreparedStatement ps2 = c.prepareStatement(InsertQuery);
			){
			
				ps1.setLong(1, utente.getId());
				ps1.setString(2, "Libreria di " + utente.getId());
			    ResultSet rs = ps1.executeQuery();
			    
			    long id_libreria;
			    if (rs.next()) {
		            id_libreria = rs.getLong("id_libreria");
		        } else {
		            throw new SQLException("Libreria non trovata dell'utente");
		        }
			
				ps2.setLong(1, id_libreria);
				ps2.setLong(2, id_gioco);
				
				ps2.executeUpdate();
			
			}catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public Libreria getLibreriaByUtente(long id_utente) {
		Libreria libreria = null;
		String query = "SELECT * FROM libreria WHERE id_utente = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setLong(1, id_utente);
			try (ResultSet rs = ps.executeQuery())
			{
				if(rs.next()) {
					
					long id = rs.getLong("id");
					String nome = rs.getString("nome");
					libreria.setId(id);
					libreria.setNome(nome);
					
				}
				
				return libreria;
			}
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
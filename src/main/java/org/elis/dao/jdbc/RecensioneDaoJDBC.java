package org.elis.dao.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.RecensioneDao;
import org.elis.model.Gioco;
import org.elis.model.Recensione;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class RecensioneDaoJDBC implements RecensioneDao {

	private RecensioneDaoJDBC() {
	}

	private static RecensioneDaoJDBC instance;

	public static RecensioneDaoJDBC getInstance() {
		if (instance == null) {
			instance = new RecensioneDaoJDBC();
		}
		return instance;
	}

	@Override
	public Recensione add(int voto, String testo, long id_utente, long id_gioco) {
		String query = "INSERT INTO Recensione (voto, testo, id_utente, id_gioco) VALUES(?, ?, ?, ?)";
		try (Connection c = JDBCDaoFactory.getConnection(); PreparedStatement ps = c.prepareStatement(query);) {
			ps.setInt(1, voto);
			ps.setString(2, testo);
			ps.setLong(3, id_utente);
			ps.setLong(3, id_gioco);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Recensione> getAll() {
		List<Recensione> recensioni = new ArrayList<>();
		String query = "SELECT *, recensione.id as id_recensione, gioco.id as id_gioco, utente.id as id_utente FROM Recensione JOIN utente ON id_utente = utente.id "
				+ "JOIN gioco ON id_gioco = gioco.id";
		try (Connection c = JDBCDaoFactory.getConnection(); 
			 PreparedStatement ps = c.prepareStatement(query);

		) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {

					Recensione r = new Recensione();
					long id = rs.getLong("id_recensione");
					int voto = rs.getInt("voto");
					String testo = rs.getString("testo");
					Timestamp data_creazione = rs.getTimestamp("data_creazione");
					LocalDateTime data_creazione2 = data_creazione.toLocalDateTime();
					Timestamp data_ultima_modifica = rs.getTimestamp("data_ultima_modifica");
					LocalDateTime data_ultima_modifica2 = data_ultima_modifica.toLocalDateTime();
					
					r.setId(id);
					r.setVoto(voto);
					r.setTesto(testo);
					r.setData_creazione(data_creazione2);
					r.setData_modifica(data_ultima_modifica2);
					
					
					Utente u = new Utente();
					
					long id2 = rs.getLong("id_utente");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp data_nascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime dataNascita = data_nascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id2);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(dataNascita);
					u.setRuolo(ruolo);
					r.setUtente(u);
					
					Gioco g = new Gioco();
					long id3 = rs.getLong("id_gioco");
					String nome = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					g.setId(id3);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					r.setGioco(g);
					
					recensioni.add(r);
				}
				if (!recensioni.isEmpty()) {
					return recensioni;
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
	public Recensione getById(long id) {
		String query = "SELECT *, recensione.id as id_recensione, gioco.id as id_gioco, utente.id as id_utente FROM Recensione JOIN utente ON id_utente = utente.id "
				+ "JOIN gioco ON id_gioco = gioco.id WHERE recensione.id = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {

					Recensione r = new Recensione();
					int voto = rs.getInt("voto");
					String testo = rs.getString("testo");
					Timestamp data_creazione = rs.getTimestamp("data_creazione");
					LocalDateTime data_creazione2 = data_creazione.toLocalDateTime();
					Timestamp data_ultima_modifica = rs.getTimestamp("data_ultima_modifica");
					LocalDateTime data_ultima_modifica2 = data_ultima_modifica.toLocalDateTime();
					
					r.setId(id);
					r.setVoto(voto);
					r.setTesto(testo);
					r.setData_creazione(data_creazione2);
					r.setData_modifica(data_ultima_modifica2);
					
					
					Utente u = new Utente();
					
					long id2 = rs.getLong("id_utente");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp data_nascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime dataNascita = data_nascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id2);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(dataNascita);
					u.setRuolo(ruolo);
					r.setUtente(u);
					
					Gioco g = new Gioco();
					long id3 = rs.getLong("id_gioco");
					String nome = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					g.setId(id3);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					r.setGioco(g);
					return r;
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
	public List<Recensione> getByUtente(long id_utente) {
		List<Recensione> recensioni = new ArrayList<>();
		String query = "SELECT *, recensione.id as id_recensione, gioco.id as id_gioco, utente.id as id_utente FROM Recensione JOIN utente ON id_utente = utente.id "
				+ "JOIN gioco ON id_gioco = gioco.id WHERE utente.id = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setLong(1, id_utente);
			try (ResultSet rs = ps.executeQuery())
			{	
				while (rs.next()) {

					Recensione r = new Recensione();
					long id = rs.getLong("id_recensione");
					int voto = rs.getInt("voto");
					String testo = rs.getString("testo");
					Timestamp data_creazione = rs.getTimestamp("data_creazione");
					LocalDateTime data_creazione2 = data_creazione.toLocalDateTime();
					Timestamp data_ultima_modifica = rs.getTimestamp("data_ultima_modifica");
					LocalDateTime data_ultima_modifica2 = data_ultima_modifica.toLocalDateTime();
					
					r.setId(id);
					r.setVoto(voto);
					r.setTesto(testo);
					r.setData_creazione(data_creazione2);
					r.setData_modifica(data_ultima_modifica2);
					
					
					Utente u = new Utente();
					
					long id2 = rs.getLong("id_utente");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp data_nascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime dataNascita = data_nascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id2);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(dataNascita);
					u.setRuolo(ruolo);
					r.setUtente(u);
					
					Gioco g = new Gioco();
					long id3 = rs.getLong("id_gioco");
					String nome = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					g.setId(id3);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					r.setGioco(g);
					
					recensioni.add(r);
				}
				if (!recensioni.isEmpty()) {
					return recensioni;
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
	public Recensione updateVoto(long id, int voto) {
		String query = "UPDATE Recensione SET voto = ? WHERE id = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setInt(1, voto);
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
	public Recensione updateTesto(long id, String testo) {
		String query = "UPDATE Recensione SET testo = ? WHERE id = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, testo);
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
	public Recensione deleteById(long id) {
		String query = "DELETE FROM Recensione WHERE id = ?";
		
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setLong(1,id);
			
			ps.executeUpdate();
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Recensione> getRecensioniByGioco(long id_gioco) {
		List<Recensione> recensioni = new ArrayList<>();
		String query = "SELECT *, recensione.id as id_recensione, gioco.id as id_gioco, utente.id as id_utente "
				+ "FROM Recensione JOIN utente ON id_utente = utente.id JOIN gioco ON id_gioco = gioco.id WHERE gioco.id = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setLong(1, id_gioco);
			try (ResultSet rs = ps.executeQuery())
			{	
				while(rs.next()) {

					Recensione r = new Recensione();
					long id = rs.getLong("id_recensione");
					int voto = rs.getInt("voto");
					String testo = rs.getString("testo");
					Timestamp data_creazione = rs.getTimestamp("data_creazione");
					LocalDateTime data_creazione2 = data_creazione.toLocalDateTime();
					Timestamp data_ultima_modifica = rs.getTimestamp("data_ultima_modifica");
					LocalDateTime data_ultima_modifica2 = data_ultima_modifica.toLocalDateTime();
					
					r.setId(id);
					r.setVoto(voto);
					r.setTesto(testo);
					r.setData_creazione(data_creazione2);
					r.setData_modifica(data_ultima_modifica2);
					
					
					Utente u = new Utente();
					
					long id2 = rs.getLong("id_utente");
					String username = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					Timestamp data_nascitaTimestamp = rs.getTimestamp("data_nascita");
					LocalDateTime dataNascita = data_nascitaTimestamp.toLocalDateTime();
					int roleIntValue = rs.getInt("ruolo");
					Ruolo[] ruoli = Ruolo.values();
					Ruolo ruolo = ruoli[roleIntValue];
					u.setId(id2);
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setDataNascita(dataNascita);
					u.setRuolo(ruolo);
					r.setUtente(u);
					
					Gioco g = new Gioco();
					long id3 = rs.getLong("id_gioco");
					String nome = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					g.setId(id3);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					r.setGioco(g);
					recensioni.add(r);
				}
				if (!recensioni.isEmpty()) {
					return recensioni;
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
	public boolean giocoRecensito(long id_gioco, long id_utente) {
		// TODO Auto-generated method stub
		return false;
	}
}
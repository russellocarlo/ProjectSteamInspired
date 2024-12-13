package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.GiocoDao;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Libreria;
import org.elis.model.Offerta;
import org.elis.model.Recensione;
import org.elis.model.Utente;

public class GiocoDaoJDBC implements GiocoDao{

	private GiocoDaoJDBC() {}
	
	private static GiocoDaoJDBC instance;
	
	public static GiocoDaoJDBC getInstance() {
		if(instance == null) {
			instance = new GiocoDaoJDBC();
		}
		return instance;
	}

	@Override
	public Gioco add(Gioco gioco) {
		String query = "INSERT INTO gioco (nome, data_rilascio, descrizione, prezzo, immagine, id_offerta, id_utente) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				){

			ps.setString(1, gioco.getNome());
			ps.setTimestamp(2, Timestamp.valueOf(gioco.getData_rilascio()));
			ps.setString(3, gioco.getDescrizione());
			ps.setDouble(4, gioco.getPrezzo());
			ps.setBytes(5, gioco.getByteImmagine());
			
			if(gioco.getOfferta() != null) {
				ps.setLong(6, gioco.getOfferta().getId());
			} else {
				ps.setNull(6, java.sql.Types.BIGINT);
			}
			ps.setLong(7, gioco.getUtente().getId());
			
			ps.executeUpdate();
						
			try (ResultSet rs = ps.getGeneratedKeys())
			{
				if(rs.next()) {
					long id_gioco = rs.getLong(1);
					
					
					String queryGiocoGenere = "insert into genere_gioco values(?,?)";
					PreparedStatement psGiocoGenere = c.prepareStatement(queryGiocoGenere);
					
					psGiocoGenere.setLong(2, id_gioco);
					if(gioco.getGeneri()!=null && !gioco.getGeneri().isEmpty()) {
						for(Genere g : gioco.getGeneri()) {
							psGiocoGenere.setLong(1, g.getId());
							psGiocoGenere.executeUpdate();							
						}
					}
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
	public List<Gioco> getAll() {
			
			List<Gioco> giochi = new ArrayList<>();
			String query = "SELECT *, gioco.id as id_gioco, gioco.nome as nome_gioco, id_offerta, id_utente, offerta.nome as nome_offerta FROM GIOCO LEFT JOIN OFFERTA ON ID_OFFERTA = offerta.id JOIN UTENTE ON id_utente = utente.id";
			try (
					Connection c = JDBCDaoFactory.getConnection();
					PreparedStatement ps = c.prepareStatement(query);
					
				)	
			{
				try (ResultSet rs = ps.executeQuery())
				{
					while(rs.next()) {
						Gioco g = new Gioco();
						long id_gioco = rs.getLong("id_gioco");
						String nome = rs.getString("nome_gioco");
						Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
						LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
						String descrizione = rs.getString("descrizione");
						double prezzo = rs.getDouble("prezzo");
						byte[] byteImmagine = rs.getBytes("immagine");
						long id_offerta = rs.getLong("id_offerta");						
						long id_utente = rs.getLong("id_utente");	
						g.setId(id_gioco);
						g.setNome(nome);
						g.setData_rilascio(data_rilascio);
						g.setDescrizione(descrizione);
						g.setPrezzo(prezzo);
						g.setByteImmagine(byteImmagine);
						
						
						Offerta o = new Offerta();
						o.setId(id_offerta);
						o.setNome(rs.getString("nome_offerta"));
						o.setSconto(rs.getDouble("sconto"));
						// Ho eliminato i set delle date perchè ho aggiunto un gioco al db non collegato ad un'offerta
						// e quando richiamavo il getGiochi() mi dava una nullPointerException
						Utente u = new Utente();
						u.setId(id_utente);
						u.setUsername(rs.getString("username"));
						u.setEmail(rs.getString("email"));
						u.setPassword(rs.getString("password"));						
						
						g.setOfferta(o);
						g.setUtente(u);
						
						giochi.add(g);
					}
					if(!giochi.isEmpty()){
						return giochi;
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
	public Gioco getByName(String nome) {
		String query = "SELECT *, gioco.id as id_gioco, gioco.nome as nome_gioco, id_offerta, id_utente, offerta.nome as nome_offerta FROM GIOCO JOIN OFFERTA ON ID_OFFERTA = offerta.id JOIN UTENTE ON id_utente = utente.id WHERE gioco.nome= ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
			)	
		{	ps.setString(1, nome);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Gioco g = new Gioco();
					long id = rs.getLong("id");
					String nome1 = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					long id_offerta = rs.getLong("id_offerta");
					long id_utente = rs.getLong("id_utente");
					g.setId(id);
					g.setNome(nome1);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					
					Offerta o = new Offerta();
					o.setId(id_offerta);
					o.setNome(rs.getString("nome_sconto"));
					o.setSconto(rs.getDouble("sconto"));
					o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
					o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime());
					
					Utente u = new Utente();
					u.setId(id_utente);
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					
					g.setOfferta(o);
					g.setUtente(u);
					return g;
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
	public List<Gioco> getGiochiByName(String nome) {
	    List<Gioco> giochi = new ArrayList<>();
	    String query = "SELECT *, gioco.id as id_gioco, gioco.nome as nome_gioco, id_offerta, id_utente, offerta.nome as nome_offerta "
	                 + "FROM GIOCO "
	                 + "JOIN OFFERTA ON ID_OFFERTA = offerta.id "
	                 + "JOIN UTENTE ON id_utente = utente.id "
	                 + "WHERE gioco.nome LIKE ?";

	    try (
	    		Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
			)	
	    { 
	        ps.setString(1, "%" + nome + "%");

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Gioco g = new Gioco();
	                
	                long id = rs.getLong("id_gioco");
	                String nomeGioco = rs.getString("nome_gioco");
	                Timestamp dataRilascioTimestamp = rs.getTimestamp("data_rilascio");
	                LocalDateTime dataRilascio = dataRilascioTimestamp.toLocalDateTime();
	                String descrizione = rs.getString("descrizione");
	                double prezzo = rs.getDouble("prezzo");
	                byte[] byteImmagine = rs.getBytes("immagine");
	                long idOfferta = rs.getLong("id_offerta");
	                long idUtente = rs.getLong("id_utente");
	                
	                g.setId(id);
	                g.setNome(nomeGioco);
	                g.setData_rilascio(dataRilascio);
	                g.setDescrizione(descrizione);
	                g.setPrezzo(prezzo);
	                g.setByteImmagine(byteImmagine);

	                Offerta o = new Offerta();
	                o.setId(idOfferta);
	                o.setNome(rs.getString("nome_offerta"));
	                o.setSconto(rs.getDouble("sconto"));
	                o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
	                o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime());

	                Utente u = new Utente();
	                u.setId(idUtente);
	                u.setUsername(rs.getString("username"));
	                u.setEmail(rs.getString("email"));
	                u.setPassword(rs.getString("password"));

	                g.setOfferta(o);
	                g.setUtente(u);

	                giochi.add(g);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			e.printStackTrace();
		}

	    return giochi;
	}

	@Override
	public Gioco updateName(long id, String nuovoNome) {
			String query = "UPDATE GIOCO SET nome = ? WHERE id = ?";
			try(
					Connection c = JDBCDaoFactory.getConnection();
					PreparedStatement ps = c .prepareStatement(query);
					){
				ps.setString(1, nuovoNome);
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
	public Gioco updateDataRilascio(long id, LocalDateTime nData_rilascio) {
			String query = "UPDATE GIOCO SET DATA_RILASCIO = ? WHERE ID = ?";
			try(
					Connection c = JDBCDaoFactory.getConnection();
					PreparedStatement ps = c .prepareStatement(query);
					){
				ps.setTimestamp(1, Timestamp.valueOf(nData_rilascio));
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
	public Gioco updateDescrizione(long id, String descrizione) {
			String query = "UPDATE GIOCO SET DESCRIZIONE = ? WHERE ID = ?";
			try(
					Connection c = JDBCDaoFactory.getConnection();
					PreparedStatement ps = c .prepareStatement(query);
					){
				ps.setString(1, descrizione);
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
	public Gioco updatePrezzo(long id, double prezzo) {
		String query = "UPDATE GIOCO SET PREZZO = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setDouble(1, prezzo);
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
	public Gioco updateImmagine(long id, byte[] byteImmagine) {
		String query = "UPDATE GIOCO SET IMMAGINE = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setBytes(1, byteImmagine);
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
	public Gioco updateOfferta(long id, Offerta offerta) {
		String query = "UPDATE GIOCO SET ID_OFFERTA = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setLong(1, offerta.getId());
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
	public Gioco deleteByName(String nome) {
		String query = "DELETE FROM GENERE_GIOCO WHERE ID_GIOCO = ?";
		
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

	@Override
	public Gioco getById(long id) {
		String query = "SELECT *, gioco.id as id_gioco, gioco.nome as nome_gioco, id_offerta, id_utente, offerta.nome as nome_offerta "
				+ "FROM GIOCO LEFT JOIN OFFERTA ON ID_OFFERTA = offerta.id "
				+ "JOIN UTENTE ON id_utente = utente.id "
				+ "WHERE gioco.id= ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{	ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Gioco g = new Gioco();
					long id2 = rs.getLong("id");
					String nome = rs.getString("nome");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					long id_offerta = rs.getLong("id_offerta");
					long id_utente = rs.getLong("id_utente");
					g.setId(id2);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					
					Offerta o = new Offerta();
					o.setId(id_offerta);
					o.setNome(rs.getString("nome_offerta"));
					o.setSconto(rs.getDouble("sconto"));
					//o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
					//o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime()); commentate per lo stesso motivo di cui sopra
					
					Utente u = new Utente();
					u.setId(id_utente);
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					
					g.setOfferta(o);
					g.setUtente(u);
					
					List<Genere> generi = new ArrayList<>();
					String query2 = "SELECT * FROM genere_gioco JOIN genere ON id_genere = genere.id WHERE id_gioco = ?";
					try( 
							PreparedStatement ps2 = c.prepareStatement(query2);
							)
					{
						ps2.setLong(1, id2);
						try (ResultSet rs2 = ps2.executeQuery()){
						
							while(rs2.next()) {
								Genere ge = new Genere();
								long id3 = rs2.getLong("id_genere");
								String nomeGenere = rs2.getString("nome");
								ge.setId(id3);
								ge.setNome(nomeGenere);
								generi.add(ge);
								g.setGeneri(generi);
							}
							
						}
					}
					List<Libreria> librerie = new ArrayList<>();
					String query3 = "SELECT * FROM libreria_gioco JOIN libreria ON id_libreria = libreria.id WHERE id_gioco = ?";
					try( 
							PreparedStatement ps3 = c.prepareStatement(query3);
							)
					{
						ps3.setLong(1, id2);
						try (ResultSet rs3 = ps3.executeQuery()){
						
							while(rs3.next()) {
								Libreria l = new Libreria();
								long id3 = rs3.getLong("id_libreria");
								String nomeLibreria = rs3.getString("nome");
								l.setId(id3);
								l.setNome(nomeLibreria);
								librerie.add(l);
								g.setLibrerie(librerie);
							}
							
						}
					}
					
					List<Recensione> recensioni = new ArrayList<>();
					String query4 = "SELECT *, recensione.id as id_recensione FROM gioco JOIN recensione ON id_gioco = gioco.id WHERE id_gioco = ?";
					try( 
							PreparedStatement ps4 = c.prepareStatement(query4);
							)
					{
						ps4.setLong(1, id2);
						try (ResultSet rs4 = ps4.executeQuery()){
						
							while(rs4.next()) {
								Recensione r = new Recensione();
								long id4 = rs4.getLong("id_recensione");
								String testo = rs4.getString("testo");
								int voto = rs4.getInt("voto");
								long id_utente2 = rs.getLong("id_utente");
								r.setId(id4);
								r.setTesto(testo);
								r.setVoto(voto);
								
								Utente u2 = new Utente();
								u2.setId(id_utente2);
								
								r.setUtente(u2);
								recensioni.add(r);
								g.setRecensioni(recensioni);
							}							
						}
					}
					return g;
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
	public Gioco deleteById(long id) {
		String query = "DELETE FROM GENERE_GIOCO WHERE ID_GIOCO = ?";
		String query2 = "DELETE FROM RECENSIONE WHERE ID_GIOCO = ?";
		String query3 = "DELETE FROM LIBRERIA_GIOCO WHERE ID_GIOCO = ?";
		String query4 = "DELETE FROM GIOCO WHERE ID = ?";
		
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				PreparedStatement ps2 = c .prepareStatement(query2);
				PreparedStatement ps3 = c .prepareStatement(query3);
				PreparedStatement ps4 = c .prepareStatement(query4);
				)
		{
			ps.setLong(1,id);			
			ps.executeUpdate();
			
			ps2.setLong(1,id);			
			ps2.executeUpdate();
			
			ps3.setLong(1,id);			
			ps3.executeUpdate();
			
			ps4.setLong(1,id);			
			ps4.executeUpdate();
			
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	@Override
	public List<Gioco> getGiochiByLibreria(long id_libreria) {
		List<Gioco> giochi = new ArrayList<>();
		String query = "SELECT *, gioco.nome AS nome_gioco, offerta.nome AS nome_offerta "
				+ "FROM libreria_gioco JOIN gioco ON libreria_gioco.id_gioco = gioco.id "
				+ "JOIN offerta ON gioco.id_offerta = offerta.id "
				+ "JOIN utente ON id_utente = utente.id "
				+ "WHERE id_libreria = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{
			ps.setLong(1, id_libreria);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Gioco g = new Gioco();
					g.setId(rs.getLong("id_gioco"));
					g.setNome(rs.getString("nome_gioco"));
					g.setData_rilascio(rs.getTimestamp("data_rilascio").toLocalDateTime());
					g.setDescrizione(rs.getString("descrizione"));
					g.setPrezzo(rs.getDouble("prezzo"));
					g.setByteImmagine(rs.getBytes("immagine"));
					
					Offerta o = new Offerta();
					o.setId(rs.getLong("id_offerta"));
					o.setNome(rs.getString("nome_offerta"));
					o.setSconto(rs.getDouble("sconto"));
					o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
					o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime());
					g.setOfferta(o);
					
					Utente u = new Utente();
					u.setId(rs.getLong("id_utente"));
					u.setUsername(rs.getString("username"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					
					g.setUtente(u);
					
					
					if (g != null) {
	                    giochi.add(g);
	                }
					
					return giochi;
					
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
	public List<Gioco> getGiochiPubblicati(long id_utente) {
		List<Gioco> giochi = new ArrayList<>();
		String query = "SELECT *, gioco.id as id_gioco, gioco.nome as nome_gioco, id_offerta, id_utente, offerta.nome as nome_offerta FROM GIOCO JOIN OFFERTA ON ID_OFFERTA = offerta.id WHERE id_utente = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);	
			)	
		{
			ps.setLong(1, id_utente);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					Gioco g = new Gioco();
					long id_gioco = rs.getLong("id_gioco");
					String nome = rs.getString("nome_gioco");
					Timestamp data_rilascioTimestamp = rs.getTimestamp("data_rilascio");
					LocalDateTime data_rilascio = data_rilascioTimestamp.toLocalDateTime();
					String descrizione = rs.getString("descrizione");
					double prezzo = rs.getDouble("prezzo");
					byte[] byteImmagine = rs.getBytes("immagine");
					long id_offerta = rs.getLong("id_offerta");							
					g.setId(id_gioco);
					g.setNome(nome);
					g.setData_rilascio(data_rilascio);
					g.setDescrizione(descrizione);
					g.setPrezzo(prezzo);
					g.setByteImmagine(byteImmagine);
					
					
					Offerta o = new Offerta();
					o.setId(id_offerta);
					o.setNome(rs.getString("nome_offerta"));
					o.setSconto(rs.getDouble("sconto"));
					o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
					o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime());						
					
					g.setOfferta(o);
					
					giochi.add(g);
				}
				if(!giochi.isEmpty()){
					return giochi;
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
	public List<Gioco> getGiochiByGenere(long id_genere) {
		List<Gioco> giochi = new ArrayList<>();
		String query = "SELECT *, offerta.nome as nome_offerta FROM genere_gioco JOIN GIOCO ON id_gioco = gioco.id JOIN utente ON id_utente=utente.id LEFT JOIN offerta ON id_offerta=offerta.id WHERE id_genere = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{
			ps.setLong(1, id_genere);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
										
					Gioco g = new Gioco();
					g.setId(rs.getLong("id_gioco"));
					g.setNome(rs.getString("nome"));
					g.setData_rilascio(rs.getTimestamp("data_rilascio").toLocalDateTime());
					g.setDescrizione(rs.getString("descrizione"));
					g.setPrezzo(rs.getDouble("prezzo"));
					g.setByteImmagine(rs.getBytes("immagine"));
					
					Long idOfferta = rs.getLong("id_offerta");
					if (!rs.wasNull()) {  // Controlla se l'offerta è effettivamente null
					    Offerta o = new Offerta();
					    o.setId(idOfferta);
					    o.setNome(rs.getString("nome_offerta"));
					    o.setSconto(rs.getDouble("sconto"));
					    o.setData_inizio(rs.getTimestamp("data_inizio").toLocalDateTime());
					    o.setData_fine(rs.getTimestamp("data_fine").toLocalDateTime());
					    g.setOfferta(o);
					} else {
					    g.setOfferta(null); // Non c'è offerta per questo gioco
					}

					
					Utente u = new Utente();
					u.setId(rs.getLong("id_utente"));
					u.setUsername(rs.getString("username"));				
					
					g.setUtente(u);
									
	                    giochi.add(g);
	                    
				}
					return giochi;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Gioco updateGenereGioco(List<Genere> listaGeneri, long idGioco ){
		 String deleteQuery = "DELETE FROM genere_gioco WHERE id_gioco = ?";
		 String insertQuery = "INSERT INTO genere_gioco (id_gioco, id_genere) VALUES (?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement psDelete = c.prepareStatement(deleteQuery);
			    PreparedStatement psInsert = c.prepareStatement(insertQuery);
				){
			
			psDelete.setLong(1, idGioco);
			psDelete.executeUpdate();
			
	        for (Genere genere : listaGeneri) {
	            psInsert.setLong(1, idGioco);
	            psInsert.setLong(2, genere.getId());
	            psInsert.addBatch();
	        }
	        
	        psInsert.executeBatch();
	        
	        
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
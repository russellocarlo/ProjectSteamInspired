package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.elis.businesslogic.BusinessLogic;
import org.elis.dao.GenereDao;
import org.elis.model.Genere;
import org.elis.model.Offerta;

public class GenereDaoJDBC implements GenereDao{

private GenereDaoJDBC() {}
	
	private static GenereDaoJDBC instance;

	public static GenereDaoJDBC getInstance() {
		if(instance == null) {
			instance = new GenereDaoJDBC();
		}
		return instance;
	}

	@Override
	public Genere add(String nome, Offerta offerta){
		String query = "INSERT INTO Genere (nome, id_offerta) VALUES(?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(2, nome);
			if(offerta != null) {
				ps.setLong(6, offerta.getId());
			} else {
				ps.setNull(6, java.sql.Types.BIGINT);
			}
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Genere> getAll(){
		List<Genere> generi = new ArrayList<>();
		String query = "SELECT * FROM GENERE";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Genere g = new Genere();
					long id = rs.getLong("id");
					String nome = rs.getString("nome");
					g.setId(id);
					g.setNome(nome);
					
					generi.add(g);
				}
				if(!generi.isEmpty()){
					return generi;
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
	public Genere getByName(String nome){
		String query = "SELECT *, genere.id as id_genere, genere.nome as nome_genere, offerta.nome as nome_offerta FROM GENERE JOIN OFFERTA ON ID_OFFERTA = offerta.id WHERE GENERE.NOME = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setString(1, nome);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Genere g = new Genere();
					long id = rs.getLong("id");
					String nome2 = rs.getString("nome");
					long id_offerta = rs.getLong("id_offerta");
					g.setId(id);
					g.setNome(nome2);
					g.setOfferta(BusinessLogic.getOffertaById(id_offerta));
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
	
	
	public Genere getById(long id){
		String query = "SELECT *, offerta.id as id_offerta, offerta.nome as nome_offerta FROM GENERE LEFT JOIN OFFERTA ON id_offerta=offerta.id WHERE genere.id=?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);
				
			)	
		{	ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery())
			{
				while(rs.next()) {
					
					Genere g = new Genere();
					
					String nome = rs.getString("nome");
					
					g.setId(id);
					g.setNome(nome);
					
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
	public Genere updateName(long id, String nuovoNome){
		String query = "UPDATE Genere SET nome = ? WHERE ID = ?";
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
	public Genere updateOfferta(long id, Offerta offerta){
		String query = "UPDATE Genere SET id_offerta = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			if(offerta != null) {
				ps.setLong(1, offerta.getId());
			} else {
				ps.setNull(1, java.sql.Types.BIGINT);
			}
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
	public Genere addGenereGiocoById(long id_genere, long id_gioco) {
		String query = "INSERT INTO genere_gioco (id_genere, id_gioco) VALUES (?,?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setLong(1, id_genere);
			ps.setLong(2, id_gioco);
			
			ps.executeUpdate();
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}


	@Override
	public Genere deleteByName(String nome){
	String query = "DELETE FROM Genere WHERE nome = ?";
			
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

}
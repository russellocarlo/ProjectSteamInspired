package org.elis.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.OffertaDao;
import org.elis.model.Offerta;

public class OffertaDaoJDBC implements OffertaDao{
	
private OffertaDaoJDBC() {}
	
	private static OffertaDaoJDBC instance;

	public static OffertaDaoJDBC getInstance() {
		if(instance == null) {
			instance = new OffertaDaoJDBC();
		}
		return instance;
	}

	@Override
	public Offerta add(String nome, double sconto, LocalDateTime data_inizio, LocalDateTime data_fine) {
		String query = "INSERT INTO Offerta (nome, sconto, data_inizio, data_fine) VALUES(?, ?, ?, ?)";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setString(1, nome);
			ps.setDouble(2, sconto);
			ps.setTimestamp(3, Timestamp.valueOf(data_inizio));
			ps.setTimestamp(4,Timestamp.valueOf(data_fine));
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Offerta> getAll() {
			
			List<Offerta> offerte = new ArrayList<>();
			String query = "SELECT * FROM OFFERTA";
			try (
					Connection c = JDBCDaoFactory.getConnection();
					PreparedStatement ps = c.prepareStatement(query);
					
				)	
			{
				try (ResultSet rs = ps.executeQuery())
				{
					while(rs.next()) {
						
						Offerta o = new Offerta();
						long id = rs.getLong("id");
						String nome = rs.getString("nome");
						double sconto = rs.getDouble("sconto");
						Timestamp data_inizioTimestamp = rs.getTimestamp("data_inizio");
						LocalDateTime data_inizio = data_inizioTimestamp.toLocalDateTime(); 
						Timestamp data_fineTimestamp = rs.getTimestamp("data_inizio");
						LocalDateTime data_fine = data_fineTimestamp.toLocalDateTime();
						o.setId(id);
						o.setNome(nome);
						o.setSconto(sconto);
						o.setData_inizio(data_inizio);
						o.setData_fine(data_fine);
						offerte.add(o);
					}
					if(!offerte.isEmpty()){
						return offerte;
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
	public Offerta updateNome(long id, String nome) {
		String query = "UPDATE OFFERTA SET NOME = ? WHERE ID = ?";
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
	public Offerta updateSconto(long id, double sconto) {
		String query = "UPDATE OFFERTA SET SCONTO = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setDouble(1, sconto);
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
	public Offerta updateData(long id, LocalDateTime data_inizio, LocalDateTime data_fine) {
		String query = "UPDATE OFFERTA SET data_inizio = ?, data_fine = ? WHERE ID = ?";
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				){
			ps.setTimestamp(1, Timestamp.valueOf(data_inizio));
			ps.setTimestamp(2, Timestamp.valueOf(data_fine));
			ps.setLong(3, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Offerta deleteById(long id) {
		String query = "DELETE FROM OFFERTA WHERE ID = ?";
		
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
	public Offerta deleteByName(String nome) {
		String query = "DELETE FROM OFFERTA WHERE NOME = ?";
		
		try(
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c .prepareStatement(query);
				)
		{
			ps.setString(1,nome);
			
			ps.executeUpdate();
		} catch (SQLException e) {						
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	@Override
	public Offerta getByName(String nome) {
		String query = "SELECT * FROM OFFERTA WHERE NOME = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setString(1, nome);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Offerta o = new Offerta();
					long id = rs.getLong("id");
					String nome2 = rs.getString("nome");
					double sconto = rs.getDouble("sconto");
					Timestamp data_inizioTimestamp = rs.getTimestamp("data_inizio");
					LocalDateTime data_inizio = data_inizioTimestamp.toLocalDateTime();
					Timestamp data_fineTimestamp = rs.getTimestamp("data_fine");
					LocalDateTime data_fine = data_fineTimestamp.toLocalDateTime();
					o.setId(id);
					o.setNome(nome2);
					o.setSconto(sconto);
					o.setData_inizio(data_inizio);
					o.setData_fine(data_fine);
					return o;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Offerta getById(long id) {
		String query = "SELECT * FROM OFFERTA WHERE ID = ?";
		try (
				Connection c = JDBCDaoFactory.getConnection();
				PreparedStatement ps = c.prepareStatement(query);				
			)
		{		ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery())
			{	
				if(rs.next()) {
					Offerta o = new Offerta();
					long id2 = rs.getLong("id");
					String nome = rs.getString("nome");
					double sconto = rs.getDouble("sconto");
					Timestamp data_inizioTimestamp = rs.getTimestamp("data_inizio");
					LocalDateTime data_inizio = data_inizioTimestamp.toLocalDateTime();
					Timestamp data_fineTimestamp = rs.getTimestamp("data_fine");
					LocalDateTime data_fine = data_fineTimestamp.toLocalDateTime();
					o.setId(id2);
					o.setNome(nome);
					o.setSconto(sconto);
					o.setData_inizio(data_inizio);
					o.setData_fine(data_fine);
					return o;
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

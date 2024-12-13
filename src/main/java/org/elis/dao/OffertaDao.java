package org.elis.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.model.Offerta;

public interface OffertaDao {
	Offerta add(String nome, double sconto, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception;
	List<Offerta> getAll();
	Offerta getByName(String nome);
	Offerta updateNome(long id, String nome) throws Exception;
	Offerta deleteById(long id);
	Offerta getById(long id);
	Offerta updateSconto(long id, double sconto);
	Offerta updateData(long id, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception;
	Offerta deleteByName(String nome);
}

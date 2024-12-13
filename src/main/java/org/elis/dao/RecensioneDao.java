package org.elis.dao;

import java.util.List;

import org.elis.model.Recensione;

public interface RecensioneDao {
	Recensione add(int voto, String testo, long id_utente, long id_gioco) throws Exception;
	List<Recensione> getAll();
	Recensione getById(long id);
	List<Recensione> getByUtente(long id_utente);
	List<Recensione> getRecensioniByGioco(long id_gioco);
	Recensione updateVoto(long id, int voto);
	Recensione updateTesto(long id, String testo);
	Recensione deleteById(long id);
	boolean giocoRecensito(long id_gioco, long id_utente);
}

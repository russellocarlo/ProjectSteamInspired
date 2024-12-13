package org.elis.dao;

import java.util.List;

import org.elis.model.Genere;
import org.elis.model.Offerta;


public interface GenereDao {
	Genere add(String nome, Offerta offerta) throws Exception;
	List<Genere> getAll();
	Genere getByName(String nome);
	Genere getById(long id);
	Genere updateName(long id, String nuovoNome) throws Exception;
	Genere updateOfferta(long id, Offerta offerta) throws Exception;
	Genere deleteByName(String nome);
	Genere addGenereGiocoById(long id_genere, long id_gioco) throws Exception;
}

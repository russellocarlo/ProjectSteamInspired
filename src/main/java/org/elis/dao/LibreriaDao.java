package org.elis.dao;

import java.util.List;

import org.elis.model.Libreria;
import org.elis.model.Utente;

public interface LibreriaDao {
	Libreria add(String nome, Utente utente) throws Exception;
	List<Libreria> getAll();
	Libreria getByName(String nome);
	Libreria updateName(long id, String nome);
	//Libreria addGiocoLibreria(long id_utente, long id_gioco, String nome);
	Libreria addGiocoAITuoiGiochi(Utente utente, long id_gioco);
	Libreria deleteGiocoLibreria(long id_libreria, long id_gioco);
	Libreria deleteByName(String nome);
	List<Libreria> getLibrerieByUtente(long id_utente);
	Libreria getLibreriaByUtente(long id_utente);
	
}

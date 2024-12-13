package org.elis.dao;
import java.time.LocalDateTime;
import java.util.List;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Offerta;

public interface GiocoDao {
	Gioco add(Gioco gioco) throws Exception;
	List<Gioco> getAll();
	Gioco getByName(String nome);
	Gioco updateName(long id, String nome) throws Exception;
	Gioco deleteByName(String nome);
	Gioco getById(long id);
	Gioco deleteById(long id);
	Gioco updateDataRilascio(long id, LocalDateTime nData_rilascio) throws Exception;
	Gioco updateDescrizione(long id, String descrizione) throws Exception;
	Gioco updatePrezzo(long id, double prezzo) throws Exception;
	Gioco updateImmagine(long id, byte[] byteImmagine) throws Exception;
	Gioco updateOfferta(long id, Offerta offerta) throws Exception;
	List<Gioco> getGiochiByLibreria(long id_libreria);
	List<Gioco> getGiochiPubblicati(long id_utente);
	List<Gioco> getGiochiByGenere(long id_genere);
	List<Gioco> getGiochiByName(String name);
	Gioco updateGenereGioco(List<Genere> listaGeneri, long idGioco) throws Exception;
}

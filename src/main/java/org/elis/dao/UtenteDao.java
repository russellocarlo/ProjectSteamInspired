package org.elis.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.model.Ruolo;
import org.elis.model.Utente;

public interface UtenteDao {

	Utente add(String username, String email, String password, LocalDateTime data_nascita, Ruolo ruolo) throws Exception;
	List<Utente> getAll();
	Utente getByUsername(String username);
	Utente updateUsername(long id, String username) throws Exception;
	Utente deleteByUsername(String username);
	Utente deleteById(long id);
	Utente getById(long id);
	Utente updateEmail(long id, String email) throws Exception;
	Utente updatePassword(long id, String password);
	Utente loginUtente(String usernameLogin, String passwordLogin);
	boolean emailOrUsernameExists(String email, String username);
	boolean emailExists(String email);
	boolean usernameExists(String username);
}

package org.elis.model;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(	name= "Utente")
	
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_ultima_modifica;
	@Column(unique = true, nullable = false)
	private String username;	
	@Column(unique = true, nullable = false)
	private String email;	
	@Column(nullable = false)
	private String password;
	@Column(name="data_nascita")
	private LocalDateTime dataNascita;
	private Ruolo ruolo;
	@OneToMany(mappedBy = "publisher")
	private List<Gioco> giochi;
	@OneToMany(mappedBy = "utente")
	private List<Recensione> recensioni;
	@OneToMany(mappedBy = "utente")
	private List<Libreria> librerie;
	

	public Utente() {}

	public LocalDateTime getData_creazione() {
		return data_creazione;
	}




	public void setData_creazione(LocalDateTime data_creazione) {
		this.data_creazione = data_creazione;
	}




	public LocalDateTime getData_ultima_modifica() {
		return data_ultima_modifica;
	}




	public void setData_ultima_modifica(LocalDateTime data_ultima_modifica) {
		this.data_ultima_modifica = data_ultima_modifica;
	}




	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Ruolo getRuolo() {
		return ruolo;
	}


	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Gioco> getGiochi() {
		return giochi;
	}


	public void setGiochi(List<Gioco> giochiPubblicati) {
		this.giochi = giochiPubblicati;
	}


	public List<Libreria> getLibrerie() {
		return librerie;
	}


	public void setLibrerie(List<Libreria> librerie) {
		this.librerie = librerie;
	}


	public List<Recensione> getRecensioni() {
		return recensioni;
	}


	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	
	@Override
	public String toString() {
	    return "[Utente]:\n" +
	            " " + username +
	            ", " + email +
	            ", " + password +
	            ", " + ruolo +
	            "}\n";
	}


	public LocalDateTime getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(LocalDateTime dataNascita) {
		this.dataNascita = dataNascita;
	}	
}
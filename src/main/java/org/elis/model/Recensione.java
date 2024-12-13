package org.elis.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(name= "Recensione", uniqueConstraints={
		@UniqueConstraint(columnNames = {"id_gioco", "id_utente"})
		})
public class Recensione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_modifica;
	@Column(columnDefinition = "INT(11) UNSIGNED")
	private int voto;
	private String testo;
	@ManyToOne
	@JoinColumn(name = "id_gioco")
	private Gioco gioco;
	@ManyToOne
	@JoinColumn(name = "id_utente",nullable=true)
	private Utente utente;
	
	
	public Recensione() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public LocalDateTime getData_creazione() {
		return data_creazione;
	}


	public void setData_creazione(LocalDateTime data_creazione) {
		this.data_creazione = data_creazione;
	}


	public LocalDateTime getData_modifica() {
		return data_modifica;
	}


	public void setData_modifica(LocalDateTime data_modifica) {
		this.data_modifica = data_modifica;
	}


	public int getVoto() {
		return voto;
	}


	public void setVoto(int voto) {
		this.voto = voto;
	}


	public String getTesto() {
		return testo;
	}


	public void setTesto(String testo) {
		this.testo = testo;
	}


	public Gioco getGioco() {
		return gioco;
	}


	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}


	public Utente getUtente() {
		return utente;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
	@Override
	public String toString() {
	    String recensione = utente.getUsername() + "<br>Voto: " + voto + "<br>" 
	                    +'"' + testo + '"' + "<br>Pubblicato il: " + data_creazione 
	                    + "<br>Ultima modifica: " + data_modifica;

	    recensione = recensione.replace("[", "").replace("]", "");
	    return recensione;
	}
}
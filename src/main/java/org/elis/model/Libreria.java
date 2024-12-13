package org.elis.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(	name= "Libreria",
uniqueConstraints={
@UniqueConstraint(columnNames = {"nome", "id_utente"})
}) 
public class Libreria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_modifica;
	@Column(unique = true, nullable = false)
	private String nome;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "libreria_gioco",
	joinColumns = @JoinColumn(name = "id_libreria"),
	inverseJoinColumns = @JoinColumn(name = "id_gioco"))
	private List<Gioco> giochi;
	@ManyToOne
	@JoinColumn(name = "id_utente", nullable = true)
	private Utente utente;
	
	
	public Libreria() {
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


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<Gioco> getGiochi() {
		
		return giochi;
	}


	public void setGiochi(List<Gioco> giochi) {
		this.giochi = giochi;
	}


	public Utente getUtente() {
		return utente;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}

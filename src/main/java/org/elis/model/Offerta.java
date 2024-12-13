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
@Table(name= "offerta")
public class Offerta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_modifica;
	@Column(nullable = false, unique = true)
	private String nome;
	@Column(nullable = false)
	private double sconto;
	@Column(nullable = false)
	private LocalDateTime data_inizio;
	@Column(nullable = false)
	private LocalDateTime data_fine;
	@OneToMany(mappedBy = "offerta")
	private List <Gioco> giochi;
	@OneToMany(mappedBy = "offerta")
	private List<Genere> generi;
	
	
	public Offerta() {
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


	public double getSconto() {
		return sconto;
	}


	public void setSconto(double sconto) {
		this.sconto = sconto;
	}


	public LocalDateTime getData_inizio() {
		return data_inizio;
	}


	public void setData_inizio(LocalDateTime data_inizio) {
		this.data_inizio = data_inizio;
	}


	public LocalDateTime getData_fine() {
		return data_fine;
	}


	public void setData_fine(LocalDateTime data_fine) {
		this.data_fine = data_fine;
	}


	public List<Gioco> getGiochi() {
		return giochi;
	}


	public void setGiochi(List<Gioco> giochi) {
		this.giochi = giochi;
	}


	public List<Genere> getGeneri() {
		return generi;
	}


	public void setGeneri(List<Genere> generi) {
		this.generi = generi;
	}


	@Override
	public String toString() {
		return "Offerta [id=" + id + ", data_creazione=" + data_creazione + ", data_modifica=" + data_modifica
				+ ", nome=" + nome + ", sconto=" + sconto + ", data_inizio=" + data_inizio + ", data_fine=" + data_fine
				+ ", giochi=" + giochi + ", generi=" + generi + "]";
	}
}

package org.elis.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name= "Genere")
public class Genere {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data_modifica;
	@Column(unique=true)
	private String nome;
	@ManyToOne
	@JoinColumn(name = "id_offerta")
	private Offerta offerta;
	@ManyToMany(mappedBy = "generi")
	private List<Gioco> giochiCategoria;
	
	
	public Genere() {
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


	public Offerta getOfferta() {
		return offerta;
	}


	public void setOfferta(Offerta offerta) {
		this.offerta = offerta;
	}


	public List<Gioco> getGiochiCategoria() {
		return giochiCategoria;
	}


	public void setGiochiCategoria(List<Gioco> giochiCategoria) {
		this.giochiCategoria = giochiCategoria;
	}


	@Override
	public String toString() {
		return "Genere [id=" + id + ", data_creazione=" + data_creazione + ", data_modifica=" + data_modifica
				+ ", nome=" + nome + ", offerta=" + offerta + ", giochiCategoria=" + giochiCategoria + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genere other = (Genere) obj;
		return id == other.id && Objects.equals(nome, other.nome);
	}


		
}

package org.elis.model;
import java.time.LocalDateTime;
import java.util.Base64;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name= "Gioco")
public class Gioco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data__creazione;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime data__modifica;
	@Column(unique = true, nullable = false)
	private String nome;
	@Column(nullable = false)
	private LocalDateTime data_rilascio;
	@Column(nullable = false, columnDefinition="TEXT", length=2001)
	private String descrizione;
	@Column(nullable = false)
	private double prezzo;
	@Lob
	@Column(name = "byte_immagine", length=100000)
	private byte[] byteImmagine;
	@ManyToOne
	@JoinColumn(name = "id_offerta")
	private Offerta offerta;
	@ManyToOne
	@JoinColumn(name = "id_utente")
	private Utente publisher;
	@ManyToMany(mappedBy= "giochi")
	private List<Libreria> librerie;
	@OneToMany(mappedBy= "gioco")
	private List<Recensione> recensioni;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "genere_gioco",
				joinColumns = @JoinColumn(name = "id_gioco"),
				inverseJoinColumns = @JoinColumn(name = "id_genere"))
	private List<Genere> generi;
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gioco other = (Gioco) obj;
		return id == other.id;
	}


	private boolean eliminato;
	
	
	public Gioco() {
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Offerta getOfferta() {
		return offerta;
	}


	public void setOfferta(Offerta offerta) {
		this.offerta = offerta;
	}


	public List<Genere> getGeneri() {
		return generi;
	}


	public void setGeneri(List<Genere> generi) {
		this.generi = generi;
	}


	public Utente getUtente() {
		return publisher;
	}


	public void setUtente(Utente utente) {
		this.publisher = utente;
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


	public LocalDateTime getData__creazione() {
		return data__creazione;
	}


	public void setData__creazione(LocalDateTime data__creazione) {
		this.data__creazione = data__creazione;
	}


	public LocalDateTime getData__modifica() {
		return data__modifica;
	}


	public void setData__modifica(LocalDateTime data__modifica) {
		this.data__modifica = data__modifica;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public LocalDateTime getData_rilascio() {
		return data_rilascio;
	}


	public void setData_rilascio(LocalDateTime data_rilascio) {
		this.data_rilascio = data_rilascio;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public double getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(double prezzo) {
		this.prezzo=prezzo;
	}


	public byte[] getByteImmagine() {
		return byteImmagine;
	}


	public void setByteImmagine(byte[] byteImmagine) {
		this.byteImmagine = byteImmagine;
	}

	
	public String getImmagine() {
		return Base64.getEncoder().encodeToString(byteImmagine);
	}
	
	public void rimuoviGioco() {
		for(Genere genere : generi) {
			generi.remove(genere);
			genere.getGiochiCategoria().remove(this);
		}
		
		for(Libreria libreria : librerie) {
			librerie.remove(libreria);
			libreria.getGiochi().remove(this);
		}
		
	}
	
	

	@Override
	public String toString() {
		return "Gioco [id=" + id + ", data__creazione=" + data__creazione + ", data__modifica=" + data__modifica
				+ ", nome=" + nome + ", data_rilascio=" + data_rilascio + ", descrizione=" + descrizione + ", prezzo="
				+ prezzo + ", offerta=" + offerta.getNome() + ", publisher=" + publisher.getUsername()
				+ ", librerie=" + librerie + ", recensioni=" + recensioni + ", generi=" + generi + "]";
	}


	public boolean isEliminato() {
		return eliminato;
	}


	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}	
}
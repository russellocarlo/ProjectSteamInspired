package org.elis.businesslogic;
import java.time.LocalDateTime;
import java.util.List;
import org.elis.dao.DaoFactory;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Libreria;
import org.elis.model.Offerta;
import org.elis.model.Recensione;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class BusinessLogic {
	
	private static final String implementation = "JPA";
	
	public static boolean emailOrUsernameExists(String email, String username) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().emailOrUsernameExists(email, username);
	}
	
	public static boolean emailExists(String email) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().emailExists(email);
	}
	
	public static boolean usernameExists(String username) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().usernameExists(username);
	}

	public static List<Utente> getUtenti(){
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().getAll();
	}
	
	public static Utente getUtenteByNome(String username) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().getByUsername(username);
	}
	
	public static Utente getUtenteById(long id) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().getById(id);
	}
	
	public static Utente loginUtente(String usernameLogin, String passwordLogin) {		
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().loginUtente(usernameLogin, passwordLogin);
	}	
	
	public static Utente addUtente(String username, String email, String password, LocalDateTime data_nascita, Ruolo ruolo) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().add(username, email, password, data_nascita, ruolo);
	}
	
	public static Utente deleteUtenteByNome(String username) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().deleteByUsername(username);
	}
	
	public static Utente deleteUtenteById(long id) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().deleteById(id);
	}
	
	public static Utente updateUsernameUtente(long id, String username) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().updateUsername(id, username);
	}
	
	public static Utente updateEmailUtente(long id, String email) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().updateEmail(id, email);
	}
	
	public static Utente updatePasswordUtente(long id, String password) {
		return DaoFactory.getDaoFactory(implementation).getUtenteDao().updatePassword(id, password);
	}
	
	public static List<Gioco> getGiochi(){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getAll();
	}
	
	public static Gioco getGiocoByNome(String nome) {
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getByName(nome);
	}
	
	public static Gioco addGioco(Gioco gioco) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().add(gioco);
	}
	
	public static Gioco updateNomeGioco(long id, String nome) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateName(id, nome);
	}
	
	public static Gioco updateDataGioco(long id, LocalDateTime nData_rilascio) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateDataRilascio(id, nData_rilascio);
	}
	
	public static Gioco updateDescrizioneGioco(long id, String descrizione) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateDescrizione(id, descrizione);
	}
	
	public static Gioco updatePrezzoGioco(long id, double prezzo) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updatePrezzo(id, prezzo);
	}
	
	public static Gioco updateImmagine(long id, byte[] byteImmagine) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateImmagine(id, byteImmagine);
	}
	
	public static Gioco updateOfferta(long id, Offerta offerta) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateOfferta(id, offerta);
	}
	
	public static Gioco updateGenereDelGioco(List<Genere> listaGeneri, long id_gioco) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().updateGenereGioco(listaGeneri, id_gioco);
	}
	
	public static Gioco deleteGiocoByNome(String nome){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().deleteByName(nome);
	}
	
	public static Gioco deleteGiocoById(long id){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().deleteById(id);
	}
	
	public static Gioco getGiocoById(long id){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getById(id);
	}
	
	public static List<Gioco> getGiochiPubblicati(long id_utente){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getGiochiPubblicati(id_utente);
	}
	
	public static List<Gioco> getGiochiByGenere(long id_genere){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getGiochiByGenere(id_genere);
	}
	public static List<Gioco> getGiochiByName(String name){
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getGiochiByName(name);
	}
	
	public static Libreria addLibreria(String nome, Utente utente) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().add(nome, utente);
	}
	
	public static List<Libreria> getLibrerie() {
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().getAll();
	}
	
	public static Libreria getLibreriaByName(String nome){
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().getByName(nome);
	}
	
	public static Libreria updateNameLibreria(long id, String nome){
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().updateName(id, nome);
	}
	
	public static List<Gioco> getGiochiByLibreria(long id_libreria) {
		return DaoFactory.getDaoFactory(implementation).getGiocoDao().getGiochiByLibreria(id_libreria);
	}
	
//	public static Libreria addGiocoInLibreria(long id_utente, long id_gioco, String nome) {
//		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().addGiocoLibreria(id_utente, id_gioco, nome);
//	}
	
	public static Libreria addGiocoAITuoiGiochi(Utente utente, long id_gioco){
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().addGiocoAITuoiGiochi(utente, id_gioco);
	}
	
	public static Libreria deleteGiocoLibreria(long id_libreria, long id_gioco) {
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().deleteGiocoLibreria(id_libreria, id_gioco);
	}
	
	public static Libreria deleteLibreriaByName(String nome) {
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().deleteByName(nome);
	}
	
	public static List<Libreria> getLibrerieByUtente(long id_utente){
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().getLibrerieByUtente(id_utente);
	}
	
	public static Libreria getLibreriaByUtente(long id_utente){
		return DaoFactory.getDaoFactory(implementation).getLibreriaDao().getLibreriaByUtente(id_utente);
	}
	
	public static Genere addGenere(String nome, Offerta offerta) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGenereDao().add(nome, offerta);
	}
	
	public static List<Genere> getGeneri(){
		return DaoFactory.getDaoFactory(implementation).getGenereDao().getAll();
	}
	
	public static Genere getGenereByNome(String nome){
		return DaoFactory.getDaoFactory(implementation).getGenereDao().getByName(nome);
	}
	
	public static Genere getGenereById(long id){
		return DaoFactory.getDaoFactory(implementation).getGenereDao().getById(id);
	}
	
	public static Genere updateNomeGenere(long id, String nome) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGenereDao().updateName(id, nome);
	}
	
	public static Genere addGenereGiocoById(long id_genere, long id_gioco) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGenereDao().addGenereGiocoById(id_genere, id_gioco);
	}
	
	
	public static Genere updateOffertaGenere(long id, Offerta offerta) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getGenereDao().updateOfferta(id, offerta);
	}
	
	public static Genere deleteGenereByNome(String nome){
		return DaoFactory.getDaoFactory(implementation).getGenereDao().deleteByName(nome);
	}
	
	public static Recensione addRecensione(int voto, String testo,long id_utente, long id_gioco) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().add(voto, testo, id_utente, id_gioco);
	}
	
	public static List<Recensione> getRecensioni() {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().getAll();
	}
	
	public static Recensione getRecensioneById(long id) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().getById(id);
	}
	
	public static List<Recensione> getRecensioneByUtente(long id_utente) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().getByUtente(id_utente);
	}
	
	public static boolean giocoRecensito(long id_gioco, long id_utente) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().giocoRecensito(id_gioco, id_utente);
	}
	
	public static Recensione updateVotoRecensione(long id, int voto) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().updateVoto(id, voto);
	}
	
	public static Recensione updateTestoRecensione(long id, String testo) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().updateTesto(id, testo);
	}
	
	public static Recensione deleteRecensioneById(long id) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().deleteById(id);
	}
	
	public static List<Recensione> getRecensioneByGioco(long id_gioco) {
		return DaoFactory.getDaoFactory(implementation).getRecensioneDao().getRecensioniByGioco(id_gioco);
	}
	
	public static Offerta addOfferta(String nome, double sconto, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().add(nome, sconto, data_inizio, data_fine);
	}
	
	public static List<Offerta> getOfferte() {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().getAll();
	}
	
	public static Offerta updateNomeOfferta(long id, String nome) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().updateNome(id, nome);
	}
	
	public static Offerta updateScontoOfferta(long id, double sconto) {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().updateSconto(id, sconto);
	}
	
	public static Offerta updateDataOfferta(long id, LocalDateTime data_inizio, LocalDateTime data_fine) throws Exception{
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().updateData(id, data_inizio, data_fine);
	}
	
	public static Offerta deleteOffertaById(long id) {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().deleteById(id);
	}
	
	public static Offerta deleteOffertaByNome(String nome) {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().deleteByName(nome);
	}
	
	public static Offerta GetOffertaByNome(String nome) {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().getByName(nome);
	}
	
	public static Offerta getOffertaById(long id) {
		return DaoFactory.getDaoFactory(implementation).getOffertaDao().getById(id);
	}	
}

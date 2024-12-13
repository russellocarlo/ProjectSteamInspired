package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Offerta;
import org.elis.model.Utente;

@MultipartConfig(maxFileSize = 1024*1024*30, maxRequestSize = 1024*1024*50)
public class AggiornaGiocoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AggiornaGiocoServlet() {
        super();
      
    }
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		String id_gioco = request.getParameter("id_gioco");	
		String nuovo_nome = request.getParameter("nuovoGiocoInput");
		String id_offerta = request.getParameter("id_offertaInput");
		String dataRilascio = request.getParameter("nuovaDataInput");
		String nuova_descrizione = request.getParameter("nuovaDescrizioneInput");
		String prezzo = request.getParameter("nuovoPrezzoInput");
		String [] generi = request.getParameterValues("generi");
		
		
		
		
		
		try {
			if(id_gioco == null || id_gioco.isBlank()){
				request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);
				return;
			}
			
			long idGioco = Long.parseLong(id_gioco);
			
			if(!nuovo_nome.isBlank()) {
			BusinessLogic.updateNomeGioco(idGioco, nuovo_nome);
			}
			
			Offerta o = null;
			if(!id_offerta.isBlank()) {
				long idOfferta = Long.parseLong(id_offerta);
				o = BusinessLogic.getOffertaById(idOfferta);
				BusinessLogic.updateOfferta(idGioco, o);
			} else {
				BusinessLogic.updateOfferta(idGioco, o);
			}
			
			if(!dataRilascio.isBlank()) {
				LocalDate dataRilasciop = LocalDate.parse(dataRilascio);
				LocalDateTime dataRilascion = dataRilasciop.atStartOfDay();
				
				BusinessLogic.updateDataGioco(idGioco, dataRilascion);
			}
			
			if(!nuova_descrizione.isBlank()) {
				BusinessLogic.updateDescrizioneGioco(idGioco, nuova_descrizione);
			}
			
			
			if(prezzo != null) {
				double prezzop = Double.parseDouble(prezzo);
				BusinessLogic.updatePrezzoGioco(idGioco, prezzop);
			}
			
			
			Gioco g = BusinessLogic.getGiocoById(idGioco);
			List<Genere> listaGeneri = g.getGeneri();
			if(generi != null){
				
				g.getGeneri().removeAll(listaGeneri);
				
				
				for(String s : generi) {
					Genere genere = BusinessLogic.getGenereByNome(s);
					listaGeneri.add(genere);
					g.setGeneri(listaGeneri);
				}
				
				BusinessLogic.updateGenereDelGioco(listaGeneri, idGioco);
			}
			
			
			
			Part filePart = request.getPart("immagineGioco");
			
			
		    if (filePart != null && filePart.getSize() > 0) {
	            byte[] byteImmagine = filePart.getInputStream().readAllBytes();
	            BusinessLogic.updateImmagine(idGioco, byteImmagine);
	            System.out.println("Immagine aggiornata con successo");
	        }
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errormsg", "Errore durante la modifica del gioco.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
		}
		
		
		request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
		request.getRequestDispatcher("/ServletPerPaginaImpostazioni").forward(request, response);	
	}
}
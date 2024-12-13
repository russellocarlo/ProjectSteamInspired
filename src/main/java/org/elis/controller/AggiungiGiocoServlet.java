package org.elis.controller;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Offerta;
import org.elis.model.Utente;

@MultipartConfig(maxFileSize = 1024*1024*30, maxRequestSize = 1024*1024*50)
public class AggiungiGiocoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AggiungiGiocoServlet() {
        super();
      
    }

	
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
	
		String titolo = request.getParameter("nomeGioco");
        String descrizione = request.getParameter("descrizioneGioco");
        String dataRilascio = request.getParameter("dataRilascio");
        String prezzo = request.getParameter("prezzoGioco");
        String[] generiList =request.getParameterValues("generi");
        String offertaS = request.getParameter("id_offertaInput");
        
	
        if (titolo == null || descrizione == null || dataRilascio == null || prezzo == null || generiList == null) {
        	request.setAttribute("errormsg", "Assicurati di aver compilato i campi obbligatori.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
            return;
        }

        try {
            LocalDate dataRilasciop = LocalDate.parse(dataRilascio);
            LocalDateTime dataRilascion = dataRilasciop.atStartOfDay();
            double prezzop = Double.parseDouble(prezzo);

            Offerta offerta = null;
            if (offertaS != null && !offertaS.isBlank()) {
                long offertaId = Long.parseLong(offertaS);
                offerta = BusinessLogic.getOffertaById(offertaId);
            }

            List<Genere> listaGeneri = new ArrayList<>();
            for (String s : generiList) {
                Genere genere = BusinessLogic.getGenereByNome(s);
                listaGeneri.add(genere);
            }

            Part file = request.getPart("immagineGioco");
            String immagineString = null;
            if (file != null && file.getSize() > 0) {
                byte[] byteImmagine = file.getInputStream().readAllBytes();
                immagineString = Base64.getEncoder().encodeToString(byteImmagine);
            }

            Gioco gioco = new Gioco();
            gioco.setNome(titolo);
            gioco.setData_rilascio(dataRilascion);
            gioco.setDescrizione(descrizione);
            gioco.setPrezzo(prezzop);
            gioco.setByteImmagine(immagineString != null ? Base64.getDecoder().decode(immagineString) : null);
            gioco.setOfferta(offerta);
            gioco.setUtente(u);

            
            BusinessLogic.addGioco(gioco);

            for (Genere g : listaGeneri) {
                BusinessLogic.addGenereGiocoById(g.getId(), gioco.getId());
            }

            BusinessLogic.addGiocoAITuoiGiochi(u, gioco.getId());

            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);

        } catch (PersistenceException e) {
        	e.printStackTrace();
        	request.setAttribute("errormsg", "Errore durante l'inserimento del gioco.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
        } catch (Exception e) {
        	e.printStackTrace();
            request.setAttribute("errormsg", "Errore durante l'inserimento del gioco.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);	
        }
	}
}

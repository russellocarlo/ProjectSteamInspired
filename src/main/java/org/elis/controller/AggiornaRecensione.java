package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Recensione;
import org.elis.model.Utente;


public class AggiornaRecensione extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AggiornaRecensione() {
        super();
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_recensione = request.getParameter("id_recensione");
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");	
		String testo = request.getParameter("testoInput");
		String nuovoVotoS = request.getParameter("nuovoVotoInput");

		if(id_recensione == null){
			request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);	
			return;
		}
		
		long id = Long.parseLong(id_recensione);
		Recensione r = BusinessLogic.getRecensioneById(id);
		
		if(r.getUtente().getId() != u.getId()) {
			request.setAttribute("errormsg", "C'è un errore");
            request.getRequestDispatcher("/LogicaPaginaGioco").forward(request, response);
            return;
		}
		
		if(testo != null && r.getUtente().getId() == u.getId()) {
			BusinessLogic.updateTestoRecensione(id, testo);
		}
		
		if(nuovoVotoS != null && r.getUtente().getId() == u.getId()) {
			int nuovo_voto = Integer.parseInt(nuovoVotoS);
			BusinessLogic.updateVotoRecensione(id, nuovo_voto);
		}
		
		request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
		request.getRequestDispatcher("/LogicaPaginaGioco").forward(request, response);	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
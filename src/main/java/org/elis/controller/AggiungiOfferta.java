package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Utente;

public class AggiungiOfferta extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public AggiungiOfferta() {
        super();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		String nomeOfferta = request.getParameter("nomeOffertaInput");
		String sconto = request.getParameter("scontoInput");
		String data_inizio = request.getParameter("dataInizioInput");
		String data_fine = request.getParameter("dataFineInput");
		
		if(nomeOfferta == null || sconto == null || data_inizio == null || data_fine == null){
			request.getRequestDispatcher("WEB-INF/ImpostazioniAdmin.jsp").forward(request, response);
			return;
		}
		
		
		LocalDate dataIniziop = LocalDate.parse(data_inizio);
		LocalDateTime dataInizio = dataIniziop.atStartOfDay();
		LocalDate dataFinep = LocalDate.parse(data_fine);
		LocalDateTime dataFine = dataFinep.atStartOfDay();
		double scontop = Double.parseDouble(sconto);
		try {
			BusinessLogic.addOfferta(nomeOfferta, scontop, dataInizio, dataFine);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errormsg", "Errore durante l'inserimento dell'offerta.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
		}
		request.getRequestDispatcher("/ServletPerPaginaImpostazioni").forward(request, response);
		
		
	}

}

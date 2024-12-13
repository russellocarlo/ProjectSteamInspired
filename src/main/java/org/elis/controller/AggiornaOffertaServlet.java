package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.elis.businesslogic.BusinessLogic;


public class AggiornaOffertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public AggiornaOffertaServlet() {
        super();
      
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id_offerta = request.getParameter("id_offertaInput");		
		String data_inizio = request.getParameter("dataInizioInput");
		String data_fine = request.getParameter("dataFineInput");
		String nuova_offerta = request.getParameter("nuovaOffertaInput");
		long idOfferta = Long.parseLong(id_offerta);
		String nuovo_sconto = request.getParameter("nuovoScontoInput");
		
		
		if(id_offerta == null) {
			request.setAttribute("errormsg", "Non ho trovato l'offerta.");
			request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			return;
		}
		
		if(data_inizio!=null && data_fine!=null) {
			LocalDate dataIniziop = LocalDate.parse(data_inizio);
			LocalDateTime dataInizio = dataIniziop.atStartOfDay();
			LocalDate dataFinep = LocalDate.parse(data_fine);
			LocalDateTime dataFine = dataFinep.atStartOfDay();
			try {
				BusinessLogic.updateDataOfferta(idOfferta, dataInizio, dataFine);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Errore durante l'aggiornamento della data dell'offerta.");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
		}
		
		if(nuova_offerta != null) {
			try {
				BusinessLogic.updateNomeOfferta(idOfferta, nuova_offerta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Errore durante l'aggiornamento dell'offerta.");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
		}
		
		if(nuovo_sconto != null) {
			Double nuovoSconto = Double.parseDouble(nuovo_sconto);
			BusinessLogic.updateScontoOfferta(idOfferta, nuovoSconto);
		}
		
		request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
		request.getRequestDispatcher("/ServletPerPaginaImpostazioni").forward(request, response);	
	}
}
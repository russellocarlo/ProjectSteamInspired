package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Offerta;

public class AggiungiGenereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AggiungiGenereServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nomeGenere");
		String idOffertaS = request.getParameter("id_offertaInput");
		
		if(nome == null){
			request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);
			return;
		}
		
		Offerta offerta = null;
		
		if(!idOffertaS.isBlank()) {
			long offertaId = Long.parseLong(idOffertaS);
			offerta = BusinessLogic.getOffertaById(offertaId);
		}
		
		
		try {
			BusinessLogic.addGenere(nome, offerta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errormsg", "Errore durante l'aggiunta del genere.");
            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
		}
		request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
	}

}

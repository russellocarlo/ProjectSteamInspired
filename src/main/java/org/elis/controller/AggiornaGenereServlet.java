package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Offerta;


public class AggiornaGenereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AggiornaGenereServlet() {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idG = request.getParameter("id_genere");
		String nuovo_nome = request.getParameter("nomeGenereInput");
		String nuovaOfferta = request.getParameter("nuovaOffertaGenereInput");

		if(idG == null){
			request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);	
			return;
		}
		
		long id = Long.parseLong(idG);
		
		if(nuovo_nome != null) {
			try {
				BusinessLogic.updateNomeGenere(id, nuovo_nome);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Ricontrolla il nome!");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
		}
		
		if(nuovaOfferta != null) {
			Offerta o = BusinessLogic.GetOffertaByNome(nuovaOfferta);
			try {
				BusinessLogic.updateOffertaGenere(id, o);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Ricontrolla l'offerta!");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
		}
		
		request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
		request.getRequestDispatcher("/ServletPerPaginaImpostazioni").forward(request, response);		
	}
}
package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;


public class AggiornaLibreriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AggiornaLibreriaServlet() {
        super();
        
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    	
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_libreria = request.getParameter("id_libreriaInput");
		String nuova_libreria = request.getParameter("nuovaLibreriaInput");
		
		if(id_libreria == null) {
			request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);
			return;
		}
		
		long idLibreria = Long.parseLong(id_libreria);
		BusinessLogic.updateNameLibreria(idLibreria, nuova_libreria);
		request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
		request.getRequestDispatcher("/ServletPerPaginaImpostazioni").forward(request, response);	
	}
}

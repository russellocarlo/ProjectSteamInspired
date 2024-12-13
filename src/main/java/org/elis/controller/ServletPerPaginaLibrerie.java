package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Libreria;
import org.elis.model.Offerta;
import org.elis.model.Utente;


public class ServletPerPaginaLibrerie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ServletPerPaginaLibrerie() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utente = (Utente)request.getSession().getAttribute("utenteLoggato");
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		List<Offerta> listaOfferte = BusinessLogic.getOfferte();
		request.setAttribute("listaGeneri", listaGeneri);
		request.setAttribute("listaOfferte", listaOfferte);
		
		
		try {
			if(utente==null) {
				request.getRequestDispatcher("ServletPerPaginaLogin").forward(request, response);
			}
			
			
			Libreria libreria = BusinessLogic.getLibreriaByUtente(utente.getId());
			System.out.println(libreria.getId());
			
			List<Gioco> giocoList = BusinessLogic.getGiochiByLibreria(libreria.getId());
			if(giocoList== null) {
				request.setAttribute("errormsg", "Non ci sono giochi nella libreria");
		        request.getRequestDispatcher("WEB-INF/LibreriaUtente.jsp").forward(request, response);
			}
			
			request.setAttribute("listaGiochi", giocoList);
			
			request.getRequestDispatcher("WEB-INF/LibreriaUtente.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

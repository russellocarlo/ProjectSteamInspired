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
import org.elis.model.Recensione;
import org.elis.model.Utente;

public class LogicaPaginaGioco extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	
	public LogicaPaginaGioco() {
	    super();
	   
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    String stringa_idgioco = request.getParameter("id_gioco");
	    Utente utente = (Utente) request.getSession().getAttribute("utenteLoggato");
	    
		
	    if (stringa_idgioco == null) {
	        request.getRequestDispatcher("/ServletPerHomePage").forward(request, response);
	        return;
	    }
		
	    long id_gioco = Long.parseLong(stringa_idgioco);		
	    Gioco gioco = BusinessLogic.getGiocoById(id_gioco);
	    List<Genere> listaGeneri = BusinessLogic.getGeneri();
	    List<Offerta> listaOfferte = BusinessLogic.getOfferte();
	    List<Recensione> listaRecensioni = BusinessLogic.getRecensioneByGioco(id_gioco);
		
	    // Verifico se utente è null prima di recuperare le librerie
	    List<Libreria> librerieUtente = null;
	    if (utente != null) {
	        librerieUtente = BusinessLogic.getLibrerieByUtente(utente.getId());
	    }
	
	    request.setAttribute("gioco", gioco);
	    request.setAttribute("listaOfferte", listaOfferte);
	    request.setAttribute("listaGeneri", listaGeneri);
	    request.setAttribute("listaRecensioni", listaRecensioni);
	    request.setAttribute("librerieUtente", librerieUtente); // Sarà null se l'utente non è loggato
	    request.getRequestDispatcher("WEB-INF/PaginaGioco.jsp").forward(request, response);
	}
}

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
import org.elis.model.Offerta;

public class LogicaPaginaGenere extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LogicaPaginaGenere() {
        super();
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String stringa_idgenere= request.getParameter("id_genere");
		
		if(stringa_idgenere == null) {
			request.getRequestDispatcher("/ServletPerHomePage").forward(request, response);
			return;
		}
		
		long id_genere = Long.parseLong(stringa_idgenere);
		Genere genere = BusinessLogic.getGenereById(id_genere);
		
		if (genere != null) {
			List<Genere> listaGeneri = BusinessLogic.getGeneri();
			List<Offerta> listaOfferte = BusinessLogic.getOfferte();
			List<Gioco> listaGiochi = BusinessLogic.getGiochiByGenere(id_genere);			
			request.setAttribute("listaGiochi", listaGiochi);
			request.setAttribute("listaGeneri", listaGeneri);
			request.setAttribute("listaOfferte", listaOfferte);
			request.getRequestDispatcher("WEB-INF/PaginaGenere.jsp").forward(request, response);
		}		
    }
}
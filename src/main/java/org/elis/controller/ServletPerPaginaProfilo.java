package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Genere;
import org.elis.model.Utente;

public class ServletPerPaginaProfilo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ServletPerPaginaProfilo() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utente = (Utente)request.getSession().getAttribute("utenteLoggato");
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		request.setAttribute("listaGeneri", listaGeneri);
		
		
		if(utente==null) {
			request.getRequestDispatcher("/ServletPerPaginaLogin").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("WEB-INF/PaginaProfilo.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

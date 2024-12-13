package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;


public class EliminaUtenteByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EliminaUtenteByUsername() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameUtente = request.getParameter("username");
		
		if(usernameUtente == null || usernameUtente.isBlank()){
			request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			return;
		}
		
		BusinessLogic.deleteUtenteByNome(usernameUtente);
		request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
	}

}

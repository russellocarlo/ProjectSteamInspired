package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;

public class UtenteByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public UtenteByUsername() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameUtente = request.getParameter("usernameInput");
		
		if(usernameUtente == null || usernameUtente.isBlank()){
			request.getRequestDispatcher("WEB-INF/ImpostazioniAdmin.jsp").forward(request, response);
			return;
		}
		
		BusinessLogic.getUtenteByNome(usernameUtente);
		request.getRequestDispatcher("").forward(request, response);
		
	}

}

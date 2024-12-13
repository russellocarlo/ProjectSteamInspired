package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Utente;


public class EliminaUtenteById extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EliminaUtenteById() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		
		BusinessLogic.deleteUtenteById(u.getId());
	}

}

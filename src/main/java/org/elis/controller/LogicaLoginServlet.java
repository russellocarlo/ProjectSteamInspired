package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class LogicaLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogicaLoginServlet() {
        super();
      
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameLogin = request.getParameter("usernameLogin");
		String passwordLogin = request.getParameter("passwordLogin");
		
		if(usernameLogin == null || passwordLogin == null) {
			response.sendRedirect("public-jsp/PaginaLogin.jsp");
			return;
		}
		
		Utente u = BusinessLogic.loginUtente(usernameLogin, passwordLogin);
		if(u == null) {
			request.setAttribute("errormsg", "Credenziali errate...forse devi prima registrarti");
			request.getRequestDispatcher("public-jsp/PaginaLogin.jsp").forward(request, response);
			return;
		}
			
		
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(2 * 60 * 60);   // La sessione scade dopo 2 ore
			session.setAttribute("utenteLoggato", u);
			request.setAttribute("listaGeneri", BusinessLogic.getGeneri());
			request.setAttribute("listaGiochi", BusinessLogic.getGiochi());
		
			if(u.getRuolo() == Ruolo.UTENTE_BASE) {								
				request.getRequestDispatcher("WEB-INF/HomepageUtentiBase.jsp").forward(request, response);
				return;
			} 
			
			if(u.getRuolo() == Ruolo.ADMIN || u.getRuolo() == Ruolo.PUBLISHER) {
				request.getRequestDispatcher("WEB-INF/HomePageAdmin.jsp").forward(request, response);
			}
	}
}
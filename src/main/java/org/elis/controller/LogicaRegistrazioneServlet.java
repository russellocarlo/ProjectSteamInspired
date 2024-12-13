package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Ruolo;

public class LogicaRegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogicaRegistrazioneServlet() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("emailFormInput");
		String username = request.getParameter("usernameFormInput");
		String password = request.getParameter("passwordFormInput");
		String dataNascita = request.getParameter("dataNascitaFormInput");
		String codicePublisher = request.getParameter("publisherCodeFormInput");
		
		if (username == null || username.trim().isEmpty() || email == null || email.isEmpty() || password == null || password.trim().isEmpty()) {
			    request.setAttribute("messaggioDiErrore", "Completa tutti i campi");
			    request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
			    return;
		}
		
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
		    request.setAttribute("messaggioDiErrore", "Formato email non valido.");
		    request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
		    return;
		}
		
		if (BusinessLogic.emailOrUsernameExists(email, username)) {
	        request.setAttribute("messaggioDiErrore", "Username o email già registrati.");
	        request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
	        return;
	    }
		
		if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$")) {
		    request.setAttribute("messaggioDiErrore", "La password deve contenere almeno 8 caratteri, includere una lettera maiuscola, un numero e un carattere speciale(@$!%?&).");
		    request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
		    return;
		}
		
		LocalDate dataNascitap = LocalDate.parse(dataNascita);
		LocalDateTime data = dataNascitap.atStartOfDay();
		LocalDate dataCorrente = LocalDate.now();
	    Period eta = Period.between(dataNascitap, dataCorrente);
	    if (eta.getYears() < 14) {
	        request.setAttribute("messaggioDiErrore", "Devi avere almeno 14 anni per registrarti.");
	        request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
	        return;
	    }

	    if(codicePublisher == null || codicePublisher.isBlank()) {
	    	try {
				BusinessLogic.addUtente(username, email, password, data, Ruolo.UTENTE_BASE);
			} catch (Exception e) {
				e.printStackTrace();
			}	
	    }
	    
	    if(codicePublisher != null && codicePublisher.equals("IDueEJolly50%")){
	    	try {
				BusinessLogic.addUtente(username, email, password, data, Ruolo.PUBLISHER);
			} catch (Exception e) {
				e.printStackTrace();
			}	
	    }
	    
	    if(codicePublisher != null && codicePublisher.equals("AdminSupremiProject")){
	    	try {
				BusinessLogic.addUtente(username, email, password, data, Ruolo.ADMIN);
			} catch (Exception e) {
				e.printStackTrace();
			}	
	    }
	    
	    request.getRequestDispatcher("public-jsp/PaginaLogin.jsp").forward(request, response);
	
	}
}
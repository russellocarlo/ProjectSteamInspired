package org.elis.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Utente;


public class AggiornaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public AggiornaUtenteServlet() {
        super();
        
    }
    
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		String nuovoUsername = request.getParameter("nuovoUsernameInput");
		String nuovaEmail = request.getParameter("nuovaEmailInput");
		String vecchiaPassword = request.getParameter("vecchiaPasswordinput");
		String nuovaPassword = request.getParameter("nuovaPasswordInput");
		String ripetiPassword = request.getParameter("ripetiPasswordInput");

		
		
		if(nuovoUsername != null) {
			if(BusinessLogic.usernameExists(nuovoUsername)) {
				request.setAttribute("messaggioDiErrore", "Username non disponibile");
				request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
				return;
			}
			
			try {
				BusinessLogic.updateUsernameUtente(u.getId(), nuovoUsername);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Errore durante l'aggiornamento dell'utente.");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
			u.setUsername(nuovoUsername);
		}
		
		if(nuovaEmail != null) {
			if(BusinessLogic.emailExists(nuovaEmail)) {
				request.setAttribute("errormsg", "Esiste un altro utente è registrato con questa email");
				request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
				return;
			}
			
			if (!nuovaEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			    request.setAttribute("messaggioDiErrore", "Formato email non valido.");
			    request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
				return;
			}
			
			try {
				BusinessLogic.updateEmailUtente(u.getId(), nuovaEmail);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("errormsg", "Errore durante l'aggiornamento dell'offerta.");
	            request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
			}
			u.setEmail(nuovaEmail);
		}
		
		if(vecchiaPassword != null && nuovaPassword != null && ripetiPassword != null) {
			if(!vecchiaPassword.equals(u.getPassword())) {
				request.setAttribute("errormsg", "La password vecchia inserita non è corretta");
				request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
				return;
			}
			
			if(!ripetiPassword.equals(nuovaPassword)) {
				request.setAttribute("errormsg", "La nuova password e la password ripetuta non coincidono");
				request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
				return;
			}
			
			if (!nuovaPassword.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$")) {
			    request.setAttribute("errormsg", "La password deve contenere almeno 8 caratteri, includere una lettera maiuscola, un numero e un carattere speciale(@$!%?&).");
			    request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
			    return;
			}
			
			BusinessLogic.updatePasswordUtente(u.getId(), nuovaPassword);
		}

			request.setAttribute("messaggioDiConferma", "Informazioni aggiornate con successo!");
			request.getRequestDispatcher("ServletPerPaginaProfilo").forward(request, response);
	}
}

package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Recensione;
import org.elis.model.Ruolo;
import org.elis.model.Utente;


public class EliminaRecensioneById extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EliminaRecensioneById() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		String idS = request.getParameter("id_recensione");
		//String idG = request.getParameter("id_gioco");
		
		
		if(idS == null){
			request.getRequestDispatcher("/LogicaPaginaGioco").forward(request, response);
			return;
		}
		
		long id = Long.parseLong(idS);
		Recensione r = BusinessLogic.getRecensioneById(id);
		
		if(u.getRuolo() != Ruolo.ADMIN && r.getUtente().getId() != u.getId()) {
			request.setAttribute("errormsg", "C'è un errore");
            request.getRequestDispatcher("/LogicaPaginaGioco").forward(request, response);
            return;
		}
		
		
		BusinessLogic.deleteRecensioneById(id);
		//long idGioco = Long.parseLong(idG);
		//request.setAttribute("id_gioco", idGioco);
		request.getRequestDispatcher("/LogicaPaginaGioco").forward(request, response);
    }
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

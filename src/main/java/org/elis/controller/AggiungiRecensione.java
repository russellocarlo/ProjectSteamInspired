package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Utente;


public class AggiungiRecensione extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public AggiungiRecensione() {
        super();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String votoS = request.getParameter("votoInput");
        String testo = request.getParameter("testoInput");
        Utente u = (Utente) request.getSession().getAttribute("utenteLoggato");
        String stringa_idgioco = request.getParameter("id_gioco");
        

        
        if (votoS == null || testo == null || u == null || stringa_idgioco == null) {
            request.setAttribute("errormsg", "Errore durante l'inserimento della recensione. Ricontrolla tutti i campi");
            request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
            return;
        }

        int voto;
        long idGioco;
        
        
        try {
            voto = Integer.parseInt(votoS);
            idGioco = Long.parseLong(stringa_idgioco);
        } catch (NumberFormatException e) {
            request.setAttribute("errormsg", "Voto o ID gioco non validi.");
            request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
            return;
        }

        boolean giocoRecensito = BusinessLogic.giocoRecensito(idGioco, u.getId());
        
        try {
        	if(!giocoRecensito) {
        		 BusinessLogic.addRecensione(voto, testo, u.getId(), idGioco);
        	} else {
        		request.setAttribute("errormsg", "Hai già recensito questo gioco");
        		request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
                return;
        	}
        } 
        
        catch(SQLIntegrityConstraintViolationException e) {
        	e.printStackTrace();
            request.setAttribute("errormsg", "Hai già recensito questo gioco");
            request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errormsg", "Errore durante l'inserimento della recensione.");
            request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
            return;
        }

        
        request.getRequestDispatcher("LogicaPaginaGioco").forward(request, response);
    }


}

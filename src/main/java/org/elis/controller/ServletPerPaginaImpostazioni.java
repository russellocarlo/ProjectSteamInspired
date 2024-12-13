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
import org.elis.model.Ruolo;
import org.elis.model.Utente;

public class ServletPerPaginaImpostazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ServletPerPaginaImpostazioni() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		List<Offerta> listaOfferte = BusinessLogic.getOfferte();
		request.setAttribute("listaGeneri", listaGeneri);
		request.setAttribute("listaOfferte", listaOfferte);
		String id_gioco= request.getParameter("id_gioco");
		
		if(u == null) {
			request.getRequestDispatcher("ServletPerHomePage").forward(request, response);
			return;
		}
		
		
		if(u.getRuolo() == Ruolo.UTENTE_BASE) {
			request.getRequestDispatcher("WEB-INF/ERROR.jsp").forward(request, response);
			return;
		}
		
		if(id_gioco == null && (u.getRuolo() == Ruolo.ADMIN || u.getRuolo() == Ruolo.PUBLISHER)) {
			String m = (String)request.getAttribute("errormsg");
			request.getRequestDispatcher("WEB-INF/ImpostazioniAvanzate.jsp").forward(request, response);
			return;
		}
		
		
		if(id_gioco != null && (u.getRuolo() == Ruolo.ADMIN || u.getRuolo() == Ruolo.PUBLISHER)) {
			
			long idGioco= Long.parseLong(id_gioco);
			Gioco g = BusinessLogic.getGiocoById(idGioco);
			request.setAttribute("gioco", g);
			
			request.getRequestDispatcher("WEB-INF/ImpostazioniAvanzate.jsp").forward(request, response);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}

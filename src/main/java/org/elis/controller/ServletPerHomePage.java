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
import org.elis.model.Utente;

public class ServletPerHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ServletPerHomePage() {
        super();
  
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Gioco> giocoList = BusinessLogic.getGiochi();
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		List<Offerta> listaOfferte = BusinessLogic.getOfferte();
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		request.setAttribute("listaGiochi", giocoList);
		request.setAttribute("listaGeneri", listaGeneri);
		request.setAttribute("listaOfferte", listaOfferte);
		
		if(u!=null)
		{
			switch(u.getRuolo())
			{
				case UTENTE_BASE:										
					request.getRequestDispatcher("WEB-INF/HomepageUtentiBase.jsp").forward(request, response);
					return;
				case ADMIN:
					request.getRequestDispatcher("WEB-INF/HomePageAdmin.jsp").forward(request, response);
					return;
				case PUBLISHER:
					request.getRequestDispatcher("WEB-INF/HomePageAdmin.jsp").forward(request, response);
					return;
				default:
					request.getRequestDispatcher("WEB-INF/ERROR.jsp").forward(request, response);
					return;
			}
		}
		
		request.getRequestDispatcher("public-jsp/Homepage.jsp").forward(request, response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}


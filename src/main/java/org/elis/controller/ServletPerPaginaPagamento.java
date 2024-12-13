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


public class ServletPerPaginaPagamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletPerPaginaPagamento() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_gioco = request.getParameter("id_gioco");
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		request.setAttribute("listaGeneri", listaGeneri);
		
		if(id_gioco == null) {
			request.setAttribute("errormsg", "Ops...non trovo il gioco");
            request.getRequestDispatcher("/ServletPerHomePage").forward(request, response);
            return;
		}
		
		long idGioco= Long.parseLong(id_gioco);
		Gioco g = BusinessLogic.getGiocoById(idGioco);
		request.setAttribute("gioco", g);
		request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

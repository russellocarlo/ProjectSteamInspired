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

public class ServletPerRicercaGiochi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletPerRicercaGiochi() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome_gioco = request.getParameter("nome_gioco");
        List<Gioco> giocoList = BusinessLogic.getGiochiByName(nome_gioco);
        List<Genere> listaGeneri = BusinessLogic.getGeneri();
        List<Offerta> listaOfferte = BusinessLogic.getOfferte();
        
        request.setAttribute("listaGeneri", listaGeneri);
        request.setAttribute("listaOfferte", listaOfferte);
        
        
        if (nome_gioco.isBlank() || giocoList.isEmpty()) {
            request.setAttribute("errormsg", "Nessun gioco trovato");
            request.getRequestDispatcher("WEB-INF/ListaRicercaGiochi.jsp").forward(request, response);
        } else {
        	request.setAttribute("listaGiochi", giocoList);
            request.getRequestDispatcher("WEB-INF/ListaRicercaGiochi.jsp").forward(request, response);
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

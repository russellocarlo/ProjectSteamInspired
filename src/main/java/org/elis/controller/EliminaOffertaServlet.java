package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.elis.businesslogic.BusinessLogic;



public class EliminaOffertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminaOffertaServlet() {
        super();
       
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nomeOfferta = request.getParameter("nomeOffertaInput");
		
		
		if(nomeOfferta == null){
			request.getRequestDispatcher("WEB-INF/ERROR.jsp").forward(request, response);
			return;
		}
				
			BusinessLogic.deleteOffertaByNome(nomeOfferta);
			request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);	
		}				
	}
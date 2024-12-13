package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.elis.businesslogic.BusinessLogic;


public class EliminaGenere extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public EliminaGenere() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String genereS = request.getParameter("nome_genereInput");
		
		if(genereS == null) {
			request.getRequestDispatcher("WEB-INF/ERROR.jsp").forward(request, response);
		}
		
		BusinessLogic.deleteGenereByNome(genereS);
		request.getRequestDispatcher("ServletPerPaginaImpostazioni").forward(request, response);
	}

}

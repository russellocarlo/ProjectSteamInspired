package org.elis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletPerPaginaRegistrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletPerPaginaRegistrazione() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("public-jsp/PaginaRegistrazione.jsp").forward(request, response);
	}
}

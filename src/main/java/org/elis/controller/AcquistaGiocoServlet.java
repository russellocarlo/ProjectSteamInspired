package org.elis.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.elis.businesslogic.BusinessLogic;
import org.elis.model.Genere;
import org.elis.model.Gioco;
import org.elis.model.Utente;


public class AcquistaGiocoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public AcquistaGiocoServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idGioco = request.getParameter("id_gioco");
		long id_gioco = Long.parseLong(idGioco);
		List<Genere> listaGeneri = BusinessLogic.getGeneri();
		request.setAttribute("listaGeneri", listaGeneri);
		Gioco g = BusinessLogic.getGiocoById(id_gioco);
		request.setAttribute("gioco", g);
		
		Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
		String numeroCarta = request.getParameter("numeroCartaInput");
		String dataDiScadenza = request.getParameter("dataDiScadenzaInput");
		String cvvS = request.getParameter("cvvInput");
		String nome = request.getParameter("nome");
		
		try {
			//Faccio i controlli per verificare se la carta inserita ha 16 cifre e che non sia scaduta
			if(idGioco == null) {
				request.setAttribute("errormsg", "Non ho trovato l'id del gioco");
				request.getRequestDispatcher("ServletPerPaginaErrore").forward(request, response);
				return;
			}
			
			if (u == null) {
				System.out.println("sono nell'if UTENTE");
	            request.setAttribute("errormsg", "Devi essere loggato per procedere con l'acquisto.");
	            request.getRequestDispatcher("/ServletPerPaginaLogin").forward(request, response);
	            return;
	        }
			
			String numeroCartaPulito = numeroCarta.replaceAll("\\s", "");
			if (numeroCartaPulito == null || !numeroCartaPulito.matches("\\d{16}")) {
			    System.out.println("sono nell'if NUMERO CARTA");
			    request.setAttribute("errormsg", "Il numero di carta deve contenere esattamente 16 cifre.");
			    request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
			    return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
	        Date scadenzaCarta = null;
	        try {
	            scadenzaCarta = sdf.parse(dataDiScadenza);
	            if (scadenzaCarta.before(new Date())) {
	            	System.out.println("sono nell'if DATA");
	                request.setAttribute("errormsg", "La carta è scaduta.");
	                request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
	                return;
	            }
	        } catch (ParseException e) {
	            request.setAttribute("errormsg", "Data di scadenza non valida.");
	            request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
	            return;
	        }
	        
	        if (cvvS == null || !cvvS.matches("\\d{3}")) {
	        	System.out.println("sono nell'if CVV");
	            request.setAttribute("errormsg", "Il CVV deve avere esattamente 3 cifre.");
	            request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
	            return;
	        }
	        
	        //consentiamo solo lettere e spazi al nome
	        if (nome == null || !nome.matches("[a-zA-Z\\s]+")) {
	        	System.out.println("sono nell'if NOME");
	            request.setAttribute("errormsg", "Il nome non può contenere numeri o caratteri speciali.");
	            request.getRequestDispatcher("WEB-INF/PaginaPagamento.jsp").forward(request, response);
	            return;
	        }
	        
	        BusinessLogic.addGiocoAITuoiGiochi(u, id_gioco);
	        request.getRequestDispatcher("ServletPerPaginaLibrerie").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.businesslogic.BusinessLogic"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.model.Genere"%>
<%@page import="org.elis.model.Offerta"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.model.Ruolo"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Libreria</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/PaginaGenereStyle.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/NavbarStyle.css">
<link rel="icon" href= "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png"  type="image/x-icon" />
</head>
<body>

	<%Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
	List<Gioco> listaGiochi = (List<Gioco>) request.getAttribute("listaGiochi");
	List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
	List<Offerta> listaOfferte = (List<Offerta>) request.getAttribute("listaOfferte");%>
	
 	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	    <div class="container-fluid">
	      <!-- Logo -->
	      <a class="navbar-brand" href="<%=request.getContextPath()%>/ServletPerHomePage">
	        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/2048px-Steam_icon_logo.svg.png" alt="Steam Logo" style="width: 40px; height: 40px;">
	        Steam
	      </a>
	
	      <!-- Tendina Toggle da cellulare -->
	      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="navbar-toggler-icon"></span>
	      </button>
	
	      <div class="collapse navbar-collapse" id="navbarNav">
	    
	        <ul class="navbar-nav me-auto">
	          
	          <!-- Ancora profilo -->
	          <li class="nav-item">
	            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a>
	          </li>
	          
	          <!-- Tasto logout -->
	           <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
	
	        </ul>
	
	       
	        <ul class="navbar-nav ms-auto">
	          <!-- Barra di ricerca -->
	          <form class="d-flex" role="search" action="<%=request.getContextPath()%>/ServletPerRicercaGiochi" method="GET">
	            <input class="form-control me-2" type="search" name="nome_gioco" placeholder="Cerca giochi..." aria-label="Search">
	            <button class="btn btn-outline-light" type="submit">Cerca</button>
	          </form>
	
	          
	        </ul>
	      </div>
	    </div>
	  </nav>
	  
	  
		 <div class="container mt-5">
		    <div class="row g-4">
		    	<%String messaggio = (String)request.getAttribute("errormsg"); 
		         if(messaggio != null){%>
			        <h3 style="color: brown; text-align: center; margin-top: 3vh;"> <%=messaggio %> </h3>
     			<%} %>
		        <% if (listaGiochi != null) { 
		            for (Gioco g : listaGiochi) { %>
		            <div class="col-md-4 d-flex align-items-stretch">
		                <div class="card mb-4 bg-dark text-white card-equal-height">
		                    <img src="data:image/jpeg;base64,<%= g.getImmagine() %>" class="card-img-top" alt="Immagine gioco">
		                    <div class="card-body d-flex flex-column">
		                        <h5 class="card-title"><%= g.getNome() %></h5>
		                        <% if (g.getOfferta() != null) { 
		                            double prezzoScontato = g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100);
		                            String risultato = String.format("%.2f", prezzoScontato); %>
		                            <% double prezzo = g.getPrezzo();
					       			String risultato2 = String.format("%.2f", prezzo);%>
		                            <p class="text-muted"><del><%= risultato2 %> €</del></p>
		                            <h5 class="text-warning"><%= risultato %> €</h5>
		                        <% }else{%>	
				       				<% double prezzo = g.getPrezzo();
					       			String risultato2 = String.format("%.2f", prezzo);%>
					       			<p><em><%=risultato2%>€</em></p>
					       	  	<%}%>	
		                        <!-- Il bottone allineato in basso -->
		                        <div class="row mt-auto">
		                        
		                        	<div class="col-6">
		                        		<a href="<%=request.getContextPath()%>/LogicaPaginaGioco?id_gioco=<%= g.getId() %>" class="btn btn-primary">Vai al gioco</a>
		                        	</div>
		                            
	                            <%if(u.getRuolo() == Ruolo.ADMIN || g.getUtente().getId() == u.getId()){ %> 
									  
								 	 	<div class="col-6" >
								 	 		 <a href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni?id_gioco=<%= g.getId()%>" class="btn btn-warning" style=" width: 100%;">Modifica</a>
								 	 	</div> 
								 	 	<%}%>
		                            
		                        </div>
		                    </div>
		                </div>
		            </div>
		        	<% } 
	            } 
	             %>
		    </div>
		</div>
	  
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
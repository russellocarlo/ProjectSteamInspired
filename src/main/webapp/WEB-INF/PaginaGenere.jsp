<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.elis.model.Utente, org.elis.model.Gioco, org.elis.model.Genere, org.elis.model.Offerta, org.elis.model.Ruolo"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Steam</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/PaginaGenereStyle.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" type="image/x-icon" />
</head>
<body>

<% 
    Utente u = (Utente) request.getSession().getAttribute("utenteLoggato");
    List<Gioco> listaGiochi = (List<Gioco>) request.getAttribute("listaGiochi");
    List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
%>


	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	    <div class="container-fluid">
	        <a class="navbar-brand" href="<%=request.getContextPath()%>/ServletPerHomePage">
	            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" alt="Steam Logo" style="max-width: 45px;">
	        </a>
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="navbarSupportedContent">
	            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	            
	            	<li class="nav-item dropdown">
	                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Generi</a>
	                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	                        <% for (Genere genere : listaGeneri) { %>
	                            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/LogicaPaginaGenere?id_genere=<%= genere.getId() %>"><%= genere.getNome() %></a></li>
	                        <% } %>
	                    </ul>
	                </li>
	            
	            	<%if(u == null){ %>
		                       <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaRegistrazione">Registrati</a>
		                        </li>
		                        
		                        <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLogin">Login</a>
		                        </li>
	                       
	                        <%} else{ %>
	            	
	            
	                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
			        
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a></li>
		        	
		        	<%if(u.getRuolo() != Ruolo.UTENTE_BASE){ %>
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni"><%if(u.getRuolo() == Ruolo.ADMIN){ %>Impostazioni Admin <%} else{%> Pubblica un gioco <%}%></a></li>
		        	<%} %>
		        	
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
	                <%} %>
	                
	            </ul>
	            
	            <form class="d-flex" role="search" action="<%=request.getContextPath()%>/ServletPerRicercaGiochi" method="GET">
		            <input class="form-control me-2" type="search" name="nome_gioco" placeholder="Cerca giochi..." aria-label="Search">
		            <button class="btn btn-outline-light" type="submit">Cerca</button>
		        </form>
	        </div>
	    </div>
	</nav>


	<div class="container mt-5">
	    <div class="row g-4">
	        <% if (listaGiochi != null) { 
	            for (Gioco g : listaGiochi) { %>
	            <div class="col-md-4 d-flex align-items-stretch">
	                <div class="card mb-4 bg-dark text-white card-equal-height">
	                    <img src="data:image/jpeg;base64,<%= g.getImmagine() %>" class="card-img-top" alt="Immagine gioco">
	                    <div class="card-body d-flex flex-column">
	                        <h5 class="card-title"><%= g.getNome() %></h5>
	                        
	                         <%if(g.getOfferta()!= null){%>
									       <div class="row">
									       	<% double prezzo = g.getPrezzo();
									       	String risultato2 = String.format("%.2f", prezzo);%>
										       	<div class="col-md-3">
									       			<p style="color: #FF640A"><em><del><%=risultato2%> €</del></em></p>
									       		</div>
									       		
									       		<div class="col-md-9">
									       		<% double prezzoScontato= g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100); 
									       			String risultato = String.format("%.2f", prezzoScontato);%>
									       		<h5 style = "color: #FEC006"> <%=risultato%> € </h5>
									       		</div>
								       		</div>
								       		<%} else{%>	
								       				<% double prezzo = g.getPrezzo();
									       			String risultato2 = String.format("%.2f", prezzo);%>
									       			<p><em><%=risultato2%>€</em></p>
									       	  <%}%>	
									       	  
	                        <!-- Il bottone allineato in basso -->
	                        <div class="mt-auto">
	                            <a href="<%=request.getContextPath()%>/LogicaPaginaGioco?id_gioco=<%= g.getId() %>" class="btn btn-primary">Vai al gioco</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        <% } } %>
	    </div>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.businesslogic.BusinessLogic"%>
<%@page import="org.elis.model.Genere"%>
<%@page import="org.elis.model.Ruolo"%>
<%@page import="org.elis.model.Offerta"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Benvenuto su STEAM</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/HomepageStyle.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="icon" href="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" type="image/x-icon" />
</head>
<body>	
	<% Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
	List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
	List<Gioco> listaGiochi = (List<Gioco>) request.getAttribute("listaGiochi");
	List<Offerta> listaOfferte = (List<Offerta>) request.getAttribute("listaOfferte"); %>
	<div class="header">
		<nav class="my-nav navbar navbar-expand-lg bg-body-tertiary">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="<%=request.getContextPath()%>/ServletPerHomePage"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" alt="logo steam navbar"  style="max-width: 45px; height: auto;"></a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"><svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" class="bi bi-list" viewBox="0 0 16 16">
	  					<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
						</svg>
				</span>
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
	            
	            	<% if (u == null) { %>
		                       <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaRegistrazione">Registrati</a>
		                        </li>
		                        
		                        <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLogin">Login</a>
		                        </li>
	                       
	                        <% } else { %>
	            	
	                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
	                
	                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a></li>
	    
	                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
	                <% } %>
	                
	            </ul>
	            
	        </div>
		    
		  </div>
		  
		  <div class="collapse navbar-collapse" id="navbarSupportedContent" style="margin-right: 2vh;">
		      <form class="d-flex" role="search" action="<%=request.getContextPath()%>/ServletPerRicercaGiochi" method="GET">
	            <input class="form-control me-2" type="search" name="nome_gioco" placeholder="Cerca giochi..." aria-label="Search">
	            <button class="btn btn-outline-light" type="submit">Cerca</button>
	          </form>
		    </div>
		  
		</nav>
			
			<%String messaggio = (String)request.getAttribute("errormsg"); 
	         if(messaggio != null){%>
		        <h1 style="color: red; text-align: center; margin-top: 3vh;"> <%=messaggio %> </h1>
     		<%} %>
				
	<div class="container justify-content-center" id="cont-fluido">
		<div class='row justify-content-center !important'>
		
	    <% 
	    
		
		if (listaGiochi != null) { 
				for (Gioco g : listaGiochi) { %>
							<div class="col-md-4" id="contenitore-carte" style= "margin: auto;">
							    <div class="card" style="max-width: 50vh;">
								     
								     <img src="data:image/jpeg;base64,<%= g.getImmagine() %>"  class="card-img-top" alt="Copertina" id="immagini-carte">
								     <div class="card-body" id="corpo-carte">
								       <h5 class="card-title"> <strong><%= g.getNome()%> </strong></h5>
								       
								         <% if (g.getOfferta() != null) { %>
								       <div class="row">
								       	<% double prezzo = g.getPrezzo();
								       	String risultato2 = String.format("%.2f", prezzo); %>
									       	<div class="col-md-3">
								       			<p style="color: #FF640A"><em><del><%=risultato2%> €</del></em></p>
								       		</div>
								       		
								       		<div class="col-md-9">
								       		<% double prezzoScontato = g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100); 
								       			String risultato = String.format("%.2f", prezzoScontato); %>
								       		<h5 style = "color: #FEC006"> <%=risultato%> € </h5>
								       		</div>
							       		</div>
							       		<% } else { %>	
							       				<% double prezzo = g.getPrezzo();
								       			String risultato2 = String.format("%.2f", prezzo); %>
								       			<p><em><%=risultato2%>€</em></p>
								       	  <% } %>								  
								 	
								 	 <div class="row" style="margin-top: 5vh;">
								 	 
								 	 	<div class="col-4">
								 	 		 <a href="<%=request.getContextPath()%>/LogicaPaginaGioco?id_gioco=<%= g.getId() %>" class="btn btn-primary" id="bottone" style=" width: 100%;">Vai al gioco</a>
								 	 	</div>
									     <% if (u != null && (u.getRuolo() == Ruolo.ADMIN || g.getUtente().getId() == u.getId())) { %> 
									    <div class="col-4">
								 	 		 <a href="<%=request.getContextPath()%>/EliminaGiocoServlet?id_gioco=<%= g.getId() %>" class="btn btn-danger" style=" width: 100%;">Elimina</a>
								 	 	</div>
								 	 	
								 	 	<div class="col-4">
								 	 		 <a href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni?id_gioco=<%= g.getId() %>" class="btn btn-warning" style=" width: 100%;">Modifica</a>
								 	 	</div>
									     <% } %>
								 	 	</div>
								     </div>
							    </div>
							</div>
		<% 
		} 
	} %>
	</div>
	</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gyb3hY2E5HzgFC0ANjP9HA50th8/xpNjW9w0VbrwA0fQNdHJkU" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-6A0aL2rrYbFw4k1jbgFypAxFqFjauF4zBlE8t6yO28ipKN48uVR9Q0/mZCpl9U2z" crossorigin="anonymous"></script>
</body>
</html>
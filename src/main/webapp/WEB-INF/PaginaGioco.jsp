<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.businesslogic.*"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.model.Genere"%>
<%@page import="org.elis.model.Libreria"%>
<%@page import="org.elis.model.Recensione"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.model.Ruolo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Steam</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/RecensioniGiocoStyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/PaginaGiocoStyle.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="icon" href= "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png"  type="image/x-icon" />
</head>
<body>
	<%Utente utente = (Utente) request.getSession().getAttribute("utenteLoggato");
	  List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
	  List<Libreria> librerieUtente = (List<Libreria>) request.getAttribute("librerieUtente");
	  List<Recensione> listaRecensioni = (List<Recensione>) request.getAttribute("listaRecensioni");
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");%>
		<nav class="my-nav navbar navbar-expand-lg bg-body-tertiary">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="<%=request.getContextPath()%>/ServletPerHomePage">
		    	<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" alt="logo steam navbar"  style="max-width: 45px; height: auto;">
		    </a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon">
		      	<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" class="bi bi-list" viewBox="0 0 16 16">
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
	            
	            	<%if(utente == null){ %>
		                       <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaRegistrazione">Registrati</a>
		                        </li>
		                        
		                        <li class="nav-item">
		                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLogin">Login</a>
		                        </li>
	                       
	                        <%} else{ %>
	            	
	            
	               <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
			        
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a></li>
		        	
		        	<%if(utente.getRuolo() != Ruolo.UTENTE_BASE){ %>
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni"><%if(utente.getRuolo() == Ruolo.ADMIN){ %>Impostazioni Admin <%} else{%> Pubblica un gioco <%}%></a></li>
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
		
		<%String messaggio = (String)request.getAttribute("errormsg"); 
	         if(messaggio != null){%>
		        <h3 style="color: brown; text-align: center; margin-top: 3vh;"> <%=messaggio %> </h3>
     	<%} %>
		
		<div class="container-fluid" id="cont-fluido">
		<div class='row gy-2' style="justify-content: center; align-items: center;">
	    <% 
	    	Gioco gioco = (Gioco)request.getAttribute("gioco");
	    	if (gioco != null) { 
	    %>
		    <div class="col-md-6" id="contenitore-carte">
		        <div class="card h-100">
		            <img src="data:image/jpeg;base64,<%= gioco.getImmagine() %>" class="card-img-top" alt="Copertina" id="immagini-carte">
		            <div class="card-body" id="corpo-carte">
		                <h5 class="card-title"><strong><%= gioco.getNome() %></strong></h5>
		                
		                 <%if(gioco.getOfferta()!= null){%>
					       <div class="row">
					       	<% double prezzo = gioco.getPrezzo();
					       	String risultato2 = String.format("%.2f", prezzo);%>
						       	<div class="col-md-3">
					       			<p style="color: #FF640A"><em><del><%=risultato2%> €</del></em></p>
					       		</div>
					       		
					       		<div class="col-md-9">
					       		<% double prezzoScontato= gioco.getPrezzo() * (1 - gioco.getOfferta().getSconto() / 100); 
					       			String risultato = String.format("%.2f", prezzoScontato);%>
					       		<h5 style = "color: #FEC006"> <%=risultato%> € </h5>
					       		</div>
				       		</div>
				       		<%} else{%>	
				       				<% double prezzo = gioco.getPrezzo();
					       			String risultato2 = String.format("%.2f", prezzo);%>
					       			<p><em><%=risultato2%>€</em></p>
					       	  <%}%>	
		                
		                <div class= "row">
		                	
		                	<div class="col-8">
		                		<h5>Data rilascio:</h5>
	       						<p><%= dtf.format(gioco.getData_rilascio()) %></p>
		                	</div>
		                	
		                	
		                		<%if(utente != null){
		                			for(Libreria l : librerieUtente){
			        					if(!l.getGiochi().contains(gioco)){%>
			        				<div class="col-4">	
		                		<a href="<%=request.getContextPath()%>/ServletPerPaginaPagamento?id_gioco=<%= gioco.getId()%>" class="btn btn-outline-info" style="padding-bottom: 2vh; padding-top: 2vh; width: 25vh; height: auto; ">Acquista</a>
		                		</div>
		                		<%}}} else{ %>
		                		<div class="col-4">	
		                		<a href="<%=request.getContextPath()%>/ServletPerPaginaLogin" class="btn btn-outline-info" style="padding-bottom: 2vh; padding-top: 2vh; width: 25vh; height: auto; ">Fai il login per acquistare</a>
		                		</div>
		                		<%} %>
		                	

		                </div>
		                
	       									              				          
		            </div>
		        </div>
		    </div>
		    
			    <div class="col-md-12" id="contenitore-carte" style="margin-top: -5vh; margin-bottom: 5vh;">
			        <div class="card h-100 recensione-card">
			        	
			        	<div class="row container-fluid recensione-card" style ="text-align: center; align-self: center; width: 100%; background-color:#212529">
			        		<div class = "col-md-12">
			        			<h2>Descrizione:</h2>
	       						<p><%= gioco.getDescrizione() %></p>
			        		</div>
				        	
			        	</div>	
			        	
			        	<div class="container-fluid" id="barra-dividi-recensioni" style="background-color: whitesmoke;">
			        	
			        	</div>
			        	
			        	<%String messaggio2 = (String)request.getAttribute("messaggioDiConferma"); 
				         if(messaggio2 != null){%>
					        <p style="color: green; text-align: center; margin-top: 3vh;"> <%=messaggio2 %> </p>
			    		 <%} %>
			        	
			        	<%if(utente != null){
			        		for(Libreria l : librerieUtente){
			        			if(l.getGiochi().contains(gioco)){%>
			        	<div class="container-fluid">
			        	
			        		<!-- box per aggiungere recensioni -->
							<div class="row recensione-card">
							    <div class="col-md-12">
							        <h2>Aggiungi una recensione:</h2>
							        <div class="w-70 mt-3" style="margin: 0 5vh 0 5vh;">
							            <form action="<%=request.getContextPath()%>/AggiungiRecensione" method="post">
							                <input type="hidden" name="id_gioco" value="<%= gioco.getId() %>">
							
							                <div class="mb-3">
							                    <label for="votoInput" class="form-label">Voto:</label>
							                    <input type="number" id="votoInput" name="votoInput" placeholder="Da 0 a 10" min="0" max="10" class="form-control" required>
							                </div>
							
							                <div class="mb-3">
							                    <label for="testoInput" class="form-label">Testo:</label>
							                    <textarea id="testoInput" name="testoInput" class="form-control" required></textarea>
							                </div>
							
							                <div class="d-flex justify-content-end">
							                    <button type="submit" class="btn btn-success" style="margin: 0 0 2vh 0">Invia</button>
							                </div>
							            </form>
							        </div>
							    </div>
							</div>			
			        	</div>
				        <%			}
			        			}
			        		} %>		           					                
	      
						<div class="container-fluid">
						    <% 
						       
						        if (listaRecensioni != null && !listaRecensioni.isEmpty()) {
						            for (Recensione r : listaRecensioni) { 
						    %>
						                <div class="row recensione-card">
						                    <div class="col-md-10">
						                        <% if (r.getUtente() == null) { %>
						                            <h5>Utente Eliminato</h5>
						                        <% } else { %>
						                            <h5><%= r.getUtente().getUsername() %></h5>
						                        <% } %>
						                        <p>Voto: <%= r.getVoto() %></p>
						                        <p><%= r.getTesto() %></p>
						                    </div>
						
						                    <div class="col-md-2 d-flex justify-content-end align-items-center">
										    <% 
										        // Controllo se l'utente è loggato e se la recensione è associata ad un utente
										        if (utente != null && r.getUtente() != null) {
										            if (r.getUtente().getId() == utente.getId()) { 
										    %> 
										                <a href="<%=request.getContextPath()%>/EliminaRecensioneById?id_recensione=<%= r.getId()%>&id_gioco=<%= gioco.getId()%>">
										                    <button type="submit" class="btn btn-danger btn-sm me-2">Elimina</button>
										                </a>
										                
										                  <button type="button" id="aggiorna-recensione-btn" class="btn btn-warning btn-sm me-2" onclick="toggleForm('form-aggiorna-recensione')">Modifica</button>
										    <% 
										            }
										        } 
										    %>
											</div>
						                </div>
						
						                <% 
						                    if (utente != null && r.getUtente() != null && r.getUtente().getId() == utente.getId()) { 
						                %> 
						                <div class="row">
						                    <div class="col-md-12">
						                        <div class="w-70 mt-3" id="form-aggiorna-recensione" style="display: none; margin: 0 5vh 0 5vh;">
						                            <form action="<%= request.getContextPath() %>/AggiornaRecensione" method="post">
						                                <input type="hidden" name="id_recensione" value="<%= r.getId() %>">
						                                <input type="hidden" name="id_gioco" value="<%= gioco.getId() %>">
						                                <div class="mb-3">
						                                    <label for="voto" class="form-label">Voto:</label>
						                                    <input type="number" id="voto" name="nuovoVotoInput" placeholder="Da 0 a 10" value="<%= r.getVoto() %>" min="0" max="10" class="form-control" required>
						                                </div>
						                                <div class="mb-3">
						                                    <label for="testo" class="form-label">Testo:</label>
						                                    <textarea id="testo" name="testoInput" class="form-control" rows="3" required><%= r.getTesto() %></textarea>
						                                </div>
						                                <div class="d-flex justify-content-end">
						                                    <button type="submit" class="btn btn-warning btn-sm me-2" style="margin: 0 0 2vh 0">Aggiorna</button>
						                                    <button type="button" id="annulla-aggiorna-recensione" class="btn btn-secondary btn-sm" onclick="toggleForm('form-aggiorna-recensione')" style="margin: 0 0 2vh 0">Indietro</button>
						                                </div>
						                            </form>
						                        </div>
						                    </div>
						                </div>
						                <% 
						                    }
						                %>  
						    <% 
						            } 
						        }
						    %>
						</div>
   												          
			        </div>
			    </div>
			<% 
				} else { 
			%>
				<p>Gioco non trovato.</p>
			<% 
				} 
			%>						 
		</div>
	</div>
<script src="<%=request.getContextPath()%>/js/PaginaGiocoJS.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

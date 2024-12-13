<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.model.*"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Benvenuto su STEAM</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/HomepageStyle.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="icon" href= "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png"  type="image/x-icon" />
</head>
<body>
	<%Utente utente = (Utente)request.getAttribute("utenteLoggato");%>	
	
		<nav class="my-nav navbar navbar-expand-lg bg-body-tertiary">
		  <div class="container-fluid">
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon">
		        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" class="bi bi-list" viewBox="0 0 16 16">
		          <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
		        </svg>
		      </span>
		    </button>
		    
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		          
		          <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" alt="logo steam navbar" style="max-width: 4.5vh; max-height:4.5vh; margin-top:-1.5vh;">
		          </a>
		          
		         <li class="nav-item dropdown">
				          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="margin-right:5px;">
				            Generi
				          </a>
				          <ul class="dropdown-menu">
		          			<%List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
		         				 if (listaGeneri != null) { 	
		          					for(Genere genere: listaGeneri){%>
		          					<li><a class="dropdown-item" href="<%=request.getContextPath()%>/LogicaPaginaGenere?id_genere=<%= genere.getId()%>"><%=genere.getNome()%></a></li>
						          	<%}
						          	} else {%>	
		          	
									<p> Non ci sono generi disponibili</p>
						
									<%}%>	
		          			        
	          			</ul>
			        </li>
			        
			        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
			        
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a></li>
		        	
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
		       </ul>
		
			      <form class="d-flex" role="search" action="<%=request.getContextPath()%>/ServletPerRicercaGiochi" method="GET">
		            <input class="form-control me-2" type="search" name="nome_gioco" placeholder="Cerca giochi..." aria-label="Search">
		            <button class="btn btn-outline-light" type="submit">Cerca</button>
           		  </form>
		    </div>
		  </div>
		</nav>

			

		<div id="carouselExample" class="my-carousel carousel slide">
	  		<div class="carousel-inner">
			    <div class="carousel-item active">
			      <img src="https://wallpapersmug.com/download/2560x1024/7308b7/god-of-war-ps4-2018.jpg" class="d-block w-100" alt="...">
				    </div>
			    <div class="carousel-item">
			      <img src="https://wallpapersmug.com/download/2560x1024/75c391/game-titanfall-2.jpg" class="d-block w-100" alt="...">
			    </div>
			    <div class="carousel-item">
			      <img src="https://wallpapersmug.com/download/2560x1024/9cef5a/dark-elden-ring-4k.jpg" class="d-block w-100" alt="...">
			    </div>
		 	 </div>
			  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Previous</span>
			  </button>
			  <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Next</span>
			  </button>
		</div>
	
	
	<div class="container justify-content-center" id="cont-fluido"">
		<div class='row justify-content-center !important'>
	
	    <% 
		List<Gioco> listaGiochi = (List<Gioco>) request.getAttribute("listaGiochi");
		
		if (listaGiochi != null) { 
				for (Gioco g : listaGiochi) { %>
							<div class="col-md-4" id="contenitore-carte" style= "margin: auto;">
								    <div class="card" style="max-widht: 50vh;">
								     
								     <img src="data:image/jpeg;base64,<%= g.getImmagine() %>" class="card-img-top" alt="Copertina" id="immagini-carte">
								     <div class="card-body" id="corpo-carte">
								       <h5 class="card-title"> <strong><%= g.getNome()%> </strong></h5>
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
								 
								      <a href="<%=request.getContextPath()%>/LogicaPaginaGioco?id_gioco=<%= g.getId() %>" class="btn btn-primary" id="bottone">Vai al gioco</a>

								     </div>
								 
								 	</div>
							 </div>
				     
	<%
			 	}
		}
	%>			 
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
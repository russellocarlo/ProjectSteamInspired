<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.businesslogic.*"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.model.Genere"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.model.Ruolo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Utente Steam</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/PaginaUtente.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="icon" href= "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png"  type="image/x-icon" />
</head>
<body>
  <%Utente utente = (Utente)request.getSession().getAttribute("utenteLoggato");
  List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");%>
		<nav class="my-nav navbar navbar-expand-lg bg-body-tertiary ">
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
		      	
		        <li class="nav-item">
		          <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a>
		        </li>
		        
		        <%if(utente.getRuolo() != Ruolo.UTENTE_BASE){ %>
		        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni"><%if(utente.getRuolo() == Ruolo.ADMIN){ %>Impostazioni Admin <%} else{%> Pubblica un gioco <%}%></a></li>
	        	<%} %>
		        
		        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
		        
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
				         
         <%String messaggio2 = (String)request.getAttribute("messaggioDiConferma"); 
         if(messaggio2 != null){%>
	        <h3 style="color: green; text-align: center; margin-top: 3vh;"> <%=messaggio2 %> </h3>
   		 <%} %>
		
		
        <section class="h-100 gradient-custom-2">
            <div class="container py-5 h-100">
              <div class="row d-flex justify-content-center">
                <div class="col col-lg-9 col-xl-8">
                  <div class="card">
                    <div class="rounded-top text-white d-flex flex-row" style="background-color: #000; height:200px;">
                      <div class="ms-4 mt-5 d-flex flex-column" style="width: 150px;">
                        <img src="https://www.trashitaliano.it/cdn-cgi/image/f=auto,fit=crop,h=633,q=90,w=768/https://media.trashitaliano.it/2024/05/Gianni-Morandi-Temo-il-morire-la-malattia-e-la-sofferenza-jpg.webp"
                          alt="Generic placeholder image" class="img-fluid img-thumbnail mt-4 mb-2"
                          style="width: 150px; z-index: 1">
                      </div>
                      
                      <div class="ms-3" style="margin-top: 130px;">
                        <h5><%=utente.getUsername() %></h5>
                        <p><%=utente.getRuolo() %></p>
                      </div>
                    </div>
                   
                    <div class="card-body p-4 text-black">
                      <div class="mb-5  text-body">
                        <p class="lead fw-normal mb-1" style="margin-left: 3vh;">Informazioni</p>
                        <div class="p-4 bg-body-tertiary">
                          <p class="font-italic mb-1"><%=utente.getUsername() %></p>
                          <p class="font-italic mb-1"><%=dtf.format(utente.getDataNascita())%></p>
                          <p class="font-italic mb-0"><%=utente.getEmail() %></p>
                        </div>
                      </div>
                      <div class="d-flex justify-content-between align-items-center mb-4 text-body">
                        <p class="lead fw-normal mb-0">Modifica Profilo</p>
                      </div>
                      
                    <div class="row g-2" style="margin-bottom: 2vh;">
	                    <div class="col mb-2">
	                        <form action="<%=request.getContextPath()%>/AggiornaUtenteServlet" method="post">
	                            
	                            <div class="mb-3">
	                                <label for="username" class="form-label" style="color: aliceblue;">Nuovo Username</label>
	                                <input type="text" class="form-control" id="username" name="nuovoUsernameInput" value="<%=utente.getUsername() %>" style=" background-color: #000000; 
	                                color: #F5F5F6; 
	                                border: 1px solid #F5F5F6; ">
	                            </div>
	                            <button data-mdb-ripple-init data-mdb-ripple-color="light" type="submit" class="btn btn-dark" style="z-index: 1; border: 1px solid #F5F5F6;">
	                                Aggiorna Username
	                              </button>
	                        </form>
	                        
	                    </div>
                   	</div>
                    
                    
                    <div class="row" style="margin-bottom: 2vh;">
                  
                        <form action="<%=request.getContextPath()%>/AggiornaUtenteServlet" method="post" class="mt-3" >
                          
                           <div class="row">
	                           	<div class="mb-3"style="margin-top: -2.1vh;">
	                                <label for="password" class="form-label" style="color: aliceblue;">Vecchia Password</label>
	                                <input type="password" class="form-control" id="password" name="vecchiaPasswordinput" placeholder="Inserisci vecchia password" style=" background-color: #000000; 
	                                color: #F5F5F6; 
	                                border: 1px solid #F5F5F6; ">
	                            </div>
                           </div>
                            
                           <div class="row">
                           
	                           	<div class="col-6 mb-3">
	                                <label for="password" class="form-label" style="color: aliceblue;">Nuova Password</label>
	                                <input type="password" class="form-control" id="password" name="nuovaPasswordInput" placeholder="Inserisci nuova password" style=" background-color: #000000; 
	                                color: #F5F5F6; 
	                                border: 1px solid #F5F5F6; ">
	                            </div>
	                            
	                             <div class="col-6 mb-3">
	                                <label for="password" class="form-label" style="color: aliceblue;">Ripeti Password</label>
	                                <input type="password" class="form-control" id="password" name="ripetiPasswordInput" placeholder="Inserisci nuova password" style=" background-color: #000000; 
	                                color: #F5F5F6; 
	                                border: 1px solid #F5F5F6; ">
	                            </div>

                           </div>
                            
                            
                            <button data-mdb-ripple-init data-mdb-ripple-color="light" type="submit" class="btn btn-dark" style="z-index: 1;border: 1px solid #F5F5F6;">
                                Aggiorna Password
                            </button>
                            
                        </form>
                    
	                 </div> 
		                 
                     
                      
                      <div class="row g-2">
                      
                        
                        <div class="col">
                            <form action="<%=request.getContextPath()%>/AggiornaUtenteServlet" method="post" class="mt-3">
                               
                                <div class="mb-3">
                                    <label for="dataNascita" class="form-label" style="color: aliceblue;">Nuova Email</label>
                                    <input type="email" class="form-control" id="email" name="nuovaEmailInput" style=" background-color: #000000; 
                                    color: #F5F5F6; 
                                    border: 1px solid #F5F5F6; "value="<%=utente.getEmail() %>">
                                </div>
                                <button data-mdb-ripple-init data-mdb-ripple-color="light" type="submit" class="btn btn-dark" style="z-index: 1;border: 1px solid #F5F5F6;">
                                    Aggiorna Email
                                 </button>
                            </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
		
		
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.model.Ruolo"%>
<%@page import="java.util.List"%>
<%@page import="org.elis.model.Genere"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagina Pagamento</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>\css\PaginaPagamento.css">
   
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" 
          crossorigin="anonymous">
    <link rel="icon" href="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" 
          type="image/x-icon" />
</head>
<body>
	<%Utente u = (Utente) request.getSession().getAttribute("utenteLoggato");
	List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
	Gioco g = (Gioco)request.getAttribute("gioco");
	%>
    <div class="header">

        <nav class="my-nav navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand" href="<%=request.getContextPath()%>/ServletPerHomePage">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png" 
                         alt="logo steam navbar" style="max-width: 45px; height: auto;">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="white" 
                             class="bi bi-list" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" 
                                  d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
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
                    
                        <%if(u != null){ %>
	                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
					        
				        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLibrerie">Libreria</a></li>
				        	
				        	<%if(u.getRuolo() != Ruolo.UTENTE_BASE){ %>
				        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaImpostazioni"><%if(u.getRuolo() == Ruolo.ADMIN){ %>Impostazioni Admin <%} else{%> Pubblica un gioco <%}%></a></li>
				        	<%} %>
				        	
				        	<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
                        <%} else{ %>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaRegistrazione">Registrati</a>
                        </li>
                        
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/ServletPerPaginaLogin">Login</a>
                        </li>
                        <%} %>
                        
                    </ul>
                </div>
            </div>
        </nav>

    </div>
    
</div>
	<%try{ %>
    <div class="container d-md-flex align-items-center"> 
        <div class="card box1 shadow-sm p-md-5 p-md-5 p-4"> 
          <div class="fw-bolder mb-4">
                <span class="fas fa-dollar-sign">

            </span>
            
            
            <span class="ps-1"><%=g.getNome()%></span>
            
            <%if(g.getOfferta() != null){%>
            <span class="ps-1">
	       		<% double prezzoScontato= g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100); 
	       			String risultato = String.format("%.2f", prezzoScontato);%>
	       		<%=risultato%>€
       		</span>
       		<%} else{%>	
       			  <p><em><%=g.getPrezzo() %> €</em></p>
   	 	       <%}%>	
          
        </div>
         <div class="d-flex flex-column"> 
                <div class="d-flex align-items-center justify-content-between text"> 
                    <span class="">Commissione</span> 
                    <span class="fas fa-dollar-sign">
                        <span class="ps-1">0.00</span>
                    </span> 
                </div> 
                    <div class="d-flex align-items-center justify-content-between text mb-4"> 
                        <span>Totale</span> 
                         <%if(g.getOfferta() != null){%>
			            <span class="fas da-dollar-sign">
				       		<% double prezzoScontato= g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100); 
				       			String risultato = String.format("%.2f", prezzoScontato);%>
				       		<%=risultato%>€
			       		</span>
			       		<%} else{%>	
			       			  <p><em><%=g.getPrezzo() %> €</em></p>
			   	 	       <%}%>
                    </div>
                     <div class="border-bottom mb-4">

                        </div> 
                        <div class="d-flex flex-column mb-4"> 
                            <span class="far fa-file-alt text">
                                <span class="ps-2">ID Pagamento:</span>
                            </span> 
                            <span class="ps-3"><%=g.getId()%></span> 
                        </div> 
                        <div class="d-flex align-items-center justify-content-between text mt-5"> 
                            <div class="d-flex flex-column text"> 
                                <span>Assistenza Clienti:</span>
                                <span>chat 24/7</span> 
                            </div> 
                            <div class="btn btn-primary rounded-circle" id="show-phone-btn">
                                <span class="fas fa-comment-alt"></span>
                            </div> 
                        </div>
                            <div id="phone-number" style="display:none; margin-top: 10px;">
                                <span>Numero di telefono dell'assistenza: +39 123 456 7890</span>
                            </div>
                         </div>
                         </div>
                          <div class="card box2 shadow-sm"> 
                            <div class="d-flex align-items-center justify-content-between p-md-5 p-4">
                                 <span class="h5 fw-bold m-0" style="color: white;">Metodi di Pagamento</span> 
                                 <div class="btn btn-primary bar">
                                    <span class="fas fa-bars">

                                 </span>
                                </div> 
                            </div> 
                            <ul class="nav nav-tabs mb-3 px-md-4 px-2">
                                     <li class="nav-item"> 
                                        <a class="nav-link px-2 active" aria-current="page">Carta di Credito</a>
                                     </li> 
                                            </ul>
                                             
                                                
                                               
                     <form action="<%=request.getContextPath()%>/AcquistaGiocoServlet" method="post">
                     	<input type="hidden" name="id_gioco" value="<%= g.getId() %>"> 
                         <div class="row">
                              <div class="col-12"> 
                                 <div class="d-flex flex-column px-md-5 px-4 mb-4" style="color: white;"> 
                                     <span>Inserire Codice Carta</span> 
                                     <div class="inputWithIcon">
                                          <input class="form-control" type="text" name= "numeroCartaInput" placeholder="0000 0000 0000 0000"  maxlength="19" oninput="formatCardNumber(this)" required> 
                                          <span class="mastercard-Box">
                                            
                                             <img  class="mastercard-png" src="https://www.freepnglogos.com/uploads/mastercard-png/mastercard-logo-logok-15.png" alt="">
                                             
                                         </span>
                                      </div>
                               	 </div>
                               </div> 
                                <div class="col-md-6">
                                
                                    <div class="d-flex flex-column ps-md-5 px-md-0 px-4 mb-4" style="color: white;">
									    <span>Data<span class="ps-1">Scadenza</span></span> 
									    <div class="inputWithIcon">
									        <input type="text" class="form-control" name="dataDiScadenzaInput" placeholder="MM/YY" 
									               pattern="(0[1-9]|1[0-2])\/\d{2}" maxlength="5" 
									               oninput="formatExpiryDate(this)" required title="Inserisci la data nel formato MM/YY"> 
									        <span class="fas fa-calendar-alt"></span> 
									    </div> 
									</div>
 
                               </div> 
                               <div class="col-md-6"> 
                                  <div class="d-flex flex-column pe-md-5 px-md-0 px-4 mb-4" style="color: white;">
                                       <span>Codice CVV</span> 
                                       <div class="inputWithIcon">
                                           <input type="password" class="form-control" name="cvvInput" minlength="3" maxlength="3" placeholder="123">
                                            <span class="fas fa-lock">

                                            </span> 
                                      </div>
                                  </div> 
                                </div> 
                               <div class="col-12">
                                    <div class="d-flex flex-column px-md-5 px-4 mb-4" style="color: white;"> 
                                       <span>Nome e Cognome</span>
                                        <div class="inputWithIcon">
                                            <input class="form-control text-uppercase" type="text" name = "nome" placeholder="Mario Rossi" required> 
                                            <span class="far fa-user"></span> 
                                        </div> 
                                    </div> 
                               </div> 
                               
                               
                                 <%String messaggio = (String)request.getAttribute("errormsg"); 
						         if(messaggio != null){%>
							        <h6 style="color: red; text-align: center"> <%=messaggio %> </h6>
						         <%} %>
						         
                             <div class="col-12 px-md-5 px-4 mt-3">
                             
                               <button class="btn btn-primary w-100 h-100" type="submit">
                               Paga €
                               <%if(g.getOfferta() != null){%>
					            <span class="fas da-dollar-sign">
						       		<% double prezzoScontato= g.getPrezzo() * (1 - g.getOfferta().getSconto() / 100); 
						       			String risultato = String.format("%.2f", prezzoScontato);%>
						       		<%=risultato%>
					       		</span>
					       		<%} else{%>	
					       			  <p><em><%=g.getPrezzo() %></em></p>
					   	 	       <%}%>
                              </button> 
                             </div>
                          </div> 
                       </form> 
               		 </div> 
                  </div>
               <%} catch(Exception e){e.printStackTrace();
               } %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>\js\PaginaPagamento.js"></script>
</body>
</html>
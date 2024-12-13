<%@page import="java.time.LocalDate"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.elis.businesslogic.BusinessLogic"%>
<%@page import="org.elis.model.Genere"%>
<%@page import="org.elis.model.Offerta"%>
<%@page import="org.elis.model.Gioco"%>
<%@page import="org.elis.model.Utente"%>
<%@page import="org.elis.model.Ruolo"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiungi Gioco</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/FormAggiungiStyle.css">
</head>
<body>
	<%Utente u = (Utente)request.getSession().getAttribute("utenteLoggato");
	List<Genere> listaGeneri = (List<Genere>) request.getAttribute("listaGeneri");
	Gioco g = (Gioco)request.getAttribute("gioco");
	List<Offerta> listaOfferte = (List<Offerta>) request.getAttribute("listaOfferte");%>
	
	 <nav class="navbar">
        <div class="navbar-logo">
            <a href="<%=request.getContextPath()%>/ServletPerHomePage">
                <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/2048px-Steam_icon_logo.svg.png" alt="Steam Logo">
            </a>
        </div>
        <ul class="navbar-links">
            <li><a href="<%=request.getContextPath()%>/ServletPerPaginaProfilo">Profilo</a></li>
            <li><a href="#" onclick="showSection('generali')">Pubblica gioco</a></li>
            
            <%if (u.getRuolo() == Ruolo.ADMIN){ %>
            <li><a href="#" onclick="showSection('impostazioniUtenti')">Utenti</a></li>
            <li><a href="#" onclick="showSection('impostazioniGeneri')">Generi</a></li>
            <li><a href="#" onclick="showSection('impostazioniOfferte')">Offerte</a></li>
            <li><a href="<%=request.getContextPath()%>/LogicaLogoutServlet">Logout</a></li>
            <%}%>
        </ul>
    </nav>
    
     <%String messaggio = (String)request.getAttribute("errormsg"); 
	         if(messaggio != null){%>
		        <h3 style="color: brown; text-align: center; margin-top: 3vh;"> <%=messaggio %> </h3>
     <%} %>
     
     <%String messaggio2 = (String)request.getAttribute("messaggioDiConferma"); 
	         if(messaggio2 != null){%>
		        <h3 style="color: green; text-align: center; margin-top: 3vh;"> <%=messaggio2 %> </h3>
     <%} %>
    
  
    <section id="impostazioniUtenti" class="hidden">
		<div class="form-container">
		    <h2>Gestione Utente</h2>
		    <form action ="<%=request.getContextPath()%>/EliminaUtenteByUsername" method="post">
		        <div class="form-group">
		            <label for="username">Username</label>
		            <input type="text" id="username" name="username" placeholder="Inserisci Username">
		        </div>
		        <div class="form-group">
		            <button type="submit" class="ban-button">Banna Utente</button>
		        </div>
		    </form>
		</div>
	</section>	
	
	<section id="impostazioniOfferte" class="hidden">
		<div class="form-container">
	        <h1>Aggiungi Offerta</h1>
	        <form action="<%=request.getContextPath()%>/AggiungiOfferta" method="post">
	            <div class="form-group">
	                <label for="nomeOfferta">Nome:</label>
	                <input type="text" id="nomeOffertaInput" name="nomeOffertaInput" required>
	            </div>
	            <div class="form-group">
	                <label for="scontoOfferta">Sconto:</label>
	                <input type="number" id="scontoInput" name="scontoInput" step="0.01" required>
	            </div>
	            <div class="form-group">
	                <label for="dataInizio">Data Inizio:</label>
	                <input type="date" id="dataInizioInput" name="dataInizioInput" required>
	            </div>
	            <div class="form-group">
	                <label for="dataFine">Data Fine:</label>
	                <input type="date" id="dataFineInput" name="dataFineInput" required>
	            </div>
	            <button type="submit">Aggiungi Offerta</button>
	        </form>
	    </div>
	    
	     <div class="form-container">
	        <h1>Rimuovi Offerta</h1>
	        <form action="<%=request.getContextPath()%>/EliminaOffertaServlet" method="post">
	            <div class="form-group">
	               
	                <div class="checkbox-group">
	                    <select id="offerta" name="nomeOffertaInput" class="form-select mb-2" >
	                    <option value="">Nessuna Offerta</option>
	                       <%
	                           if (listaOfferte != null && !listaOfferte.isEmpty()){
	                               for(Offerta offerta : listaOfferte){%>
	                                   <option value="<%=offerta.getNome()%>"><%=offerta.getNome()%></option>
	                       <%}%>
	                       <% } else { %> 
	                           <option disabled>Nessuna offerta disponibile</option>
	                       <% } %>
						</select>
	                </div>
	            </div>
	            <button type="submit">Rimuovi Offerta</button>
	        </form>
	    </div>
	    
    </section>
	
	<section id="impostazioniGeneri" class="hidden">
		<div class="form-container">
	        <h1>Aggiungi Genere</h1>
	        <form action="<%=request.getContextPath()%>/AggiungiGenereServlet" method="post">
	            <div class="form-group">
	                <label for="nomeGenere">Nome:</label>
	                <input type="text" id="nomeGenere" name="nomeGenere" required>
	            </div>
	            <div class="form-group">
	                <label>Offerta (Opzionale):</label>
	                <div class="checkbox-group">
	                    <select id="offerta" name="id_offertaInput" class="form-select mb-2" >
	                    <option value="">Nessuna Offerta</option>
	                       <%
	                           if (listaOfferte != null && !listaOfferte.isEmpty()){
	                               for(Offerta o : listaOfferte){%>
	                                   <option value="<%=o.getId()%>"><%=o.getNome()%></option>
	                       <%}%>
	                       <% } else { %> 
	                           <option disabled>Nessuna offerta disponibile</option>
	                       <% } %>
						</select>
	                </div>
	            </div>
	            <button type="submit">Aggiungi Genere</button>
	        </form>
	    </div>
	    
	    <div class="form-container">
	        <h1>Rimuovi Genere</h1>
	        <form action="<%=request.getContextPath()%>/EliminaGenere" method="post">
	            <div class="form-group">
	               
	                <div class="checkbox-group">
	                    <select id="genere" name="nome_genereInput" class="form-select mb-2" >
	                    <option value="">Nessun Genere</option>
	                       <%
	                           if (listaGeneri != null && !listaGeneri.isEmpty()){
	                               for(Genere genere : listaGeneri){%>
	                                   <option value="<%=genere.getNome()%>"><%=genere.getNome()%></option>
	                       <%}%>
	                       <% } else { %> 
	                           <option disabled>Nessun genere disponibile</option>
	                       <% } %>
						</select>
	                </div>
	            </div>
	            <button type="submit">Rimuovi Genere</button>
	        </form>
	    </div>
    </section>
    
	<section id="generali" class="active">
	
		<%if( g!= null){ %>
		<div class = "form-container">
			<h1>Modifica Gioco:</h1>
	            <form action="<%=request.getContextPath()%>/AggiornaGiocoServlet" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="id_gioco" value="<%= g.getId() %>">
						
	                <div class="form-group">
	                
                    <label for="nome" style="color: white">Titolo: </label>
					<input type="text" id="titolo" name="nuovoGiocoInput" value="<%=g.getNome()%>" class="form-control mb-2">
                      								                               										
	                </div>
	                
	                <div class="form-group">
	                    <label for="descrizione" style="color: white">Descrizione: </label>
						<textarea id="testo" name="nuovaDescrizioneInput" class="form-control mb-2" rows="3" ><%=g.getDescrizione()%></textarea> 
	                </div>
	
	                <div class="form-group">
	                    <label for="dataRilascio" style="color: white">Data Rilascio: </label>
                		<input type="date" value='<%=LocalDate.from(g.getData_rilascio())%>' id="dataRilascio" name="nuovaDataInput" class="form-control mb-2">
	                </div>
	                
	                <div class="form-group">
	                    <label for="immagineGioco" style="color: white">Scegli un immagine: </label>
                              <input type="file" id="immagineGioco" name="immagineGioco" accept="image/jpeg,image/png,image/jpg" 
                                     class="form-control mb-2">
	                </div>
	                
	                 <div class="form-group">
	                    <label for="prezzo" style="color: white">Prezzo:</label>
						<input type="number" step=".01" id="prezzo" name="nuovoPrezzoInput" value="<%=g.getPrezzo()%>" class="form-control mb-2">
	                </div>
	                
	                <div class="row mb-3">
		              
	                <div class="checkbox-group">
	                	<label for="id_genereNuovo" style="color: white">Generi:</label>
	                
	                	<%
	                	if(listaGeneri != null && !listaGeneri.isEmpty()){
	                		for (Genere genere : new ArrayList<>(listaGeneri)){                	
	                	%>
	                    <label><input type="checkbox" <%if(g.getGeneri().contains(genere)){ %>checked<%} %> name="generi" value="<%=genere.getNome()%>"><%=genere.getNome()%></label>
	                    <%}
	                	}%>	
	                </div>
                  
               </div>
	                
                <div class="form-group">
                    	<label for="offerta" style="color: white">Offerta: </label>
                    	
                             <select id="offerta" name="id_offertaInput" class="form-select mb-2" >
                             	<option value="">Nessuna Offerta</option>
                                 <%if (listaOfferte != null && !listaOfferte.isEmpty()){
                                         for(Offerta o : listaOfferte){%>
                                             <option value="<%=o.getId()%>" <%if(g.getOfferta() != null && g.getOfferta().getNome().equals(o.getNome())) {%>selected<%} %>><%=o.getNome()%>  </option>

                                 <%}%>
                                 <% } else { %> 
                                     <option disabled>Nessuna offerta disponibile</option>
                                 <% } %>
                             </select>
                </div>
	              
	         
                <div class="d-flex justify-content-end">
                    <button type="submit">Aggiorna</button>
                </div>
       		</form>
    	</div>
    	<%} %>
	
	
	    <div class="form-container">
	        <h1>Aggiungi Gioco</h1>
	        <form action="<%=request.getContextPath()%>/AggiungiGiocoServlet" method="post" enctype="multipart/form-data">
	            <div class="form-group">
	                <label for="nomeGioco">Nome*:</label>
	                <input type="text" id="nomeGioco" name="nomeGioco" required>
	            </div>
	            <div class="form-group">
	                <label for="dataRilascio">Data di Rilascio*:</label>
	                <input type="date" id="dataRilascio" name="dataRilascio" required>
	            </div>
	            <div class="form-group">
	                <label for="descrizioneGioco">Descrizione*:</label>
	                <textarea id="descrizioneGioco" name="descrizioneGioco" maxlength="1999" required></textarea>
	            </div>
	            <div class="form-group">
	                <label for="prezzoGioco">Prezzo*:</label>
	                <input type="number" id="prezzoGioco" name="prezzoGioco" step="0.01" required>
	            </div>
	            <div class="form-group">
	                <label>Generi*:</label>
	                <div class="checkbox-group">
	                	<%
	                	if(listaGeneri != null && !listaGeneri.isEmpty()){
	                		for (Genere genere : listaGeneri){                	
	                	%>
	                    <label><input type="checkbox" name="generi" value="<%=genere.getNome()%>"><%=genere.getNome()%></label>
	                    <%}
	                	}%>	           
	                </div>
	            </div>
	            <div class="form-group">
	                <label>Offerta:</label>
	                <div class="checkbox-group">
	                    <select id="offerta" name="id_offertaInput" class="form-select mb-2" >
	                    <option value="">Nessuna Offerta</option>
	                       <%
	                           if (listaOfferte != null && !listaOfferte.isEmpty()){
	                               for(Offerta o : listaOfferte){%>
	                                   <option value="<%=o.getId()%>"><%=o.getNome()%></option>
	                       <%}%>
	                       <% } else { %> 
	                           <option disabled>Nessuna offerta disponibile</option>
	                       <% } %>
						</select>
	                </div>
	            </div>
	            <div class="form-group">
	                <label for="immagineGioco">Immagine del Gioco*:</label>
	                <input type="file" id="immagineGioco" name="immagineGioco" accept="image/*" required>
	            </div>
	            <button type="submit">Aggiungi Gioco</button>
	        </form>
	    </div>
    </section>
	<script src="<%=request.getContextPath()%>/js/impostazioniAdminJS.js"></script>    
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Registrazione.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
	<div class = "wrapper">
		<span class="bg-animate"></span>
        <span class="bg-animate2"></span>
        <div class="form-box register">
        	<h2 style="margin-top: 6vh; margin-bottom: -3vh">Registrati</h2>
			<form action="<%=request.getContextPath()%>/LogicaRegistrazioneServlet" method="post">
			
				<div class = "input-box">
					<input type="email" name="emailFormInput" required>
					<label for="">Email*</label>
	                <i class='bx bxs-envelope' ></i>
				</div>
	            <div class = "input-box">
	            	 <input type="text" name="usernameFormInput" required>
	            	 <label for="">Username*</label>
	                 <i class='bx bxs-user' ></i>
	            </div>
	            <div class="input-box">
	                 <input type="date" name="dataNascitaFormInput" id="data" required>
	                 <label for="">Data di Nascita*</label>
	                 <i class='bx bxs-calendar-star' ></i>
	            </div>
	            <div class= "input-box">
	            	 <input type="password" name="passwordFormInput" required>
	            	 <label for="">Password*</label>
	                 <i class='bx bxs-lock-alt' ></i>
	            </div>
	            
	        	 <div class="input-box">
			        <input type="password" name="publisherCodeFormInput">
			        <label for="chiave" style="color: #2592B8">Passkey: </label>
			        <i class='bx bxs-key'></i>
			    </div>
	        	
	            <button type="submit" class= "btn" value="Registrati" style="margin-bottom: 6vh">Registrati</button>
	            
	        </form>
		</div>
	        <div class="info-text register">
	            <h2>Benvenuto</h2>
	            <div class="logreg-link">
	                <p>Hai già un account?  <a class="login-link" href="<%=request.getContextPath()%>/ServletPerPaginaLogin" style="color: #2592B8">Login</a></p>
	            </div>
	  			<%String messaggio = (String)request.getAttribute("messaggioDiErrore"); 
		        if(messaggio != null){%>
		        <p style="color: brown;"> <%=messaggio %> </p>
		        <%} %>
	        </div>
    </div>    
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Login</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/PaginaLoginStyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<body>
	<div class="wrapper">
		<span class="bg-animate"></span>
		<div class="form-box login">
			<h2>Login</h2>
			<form class="px-4 py-3" action="<%=request.getContextPath()%>/LogicaLoginServlet" method="post">
				<div class = "input-box">
					<input type="text" class="form-control" name="usernameLogin" required>
					<label for="">User/Email</label>
	                <i class='bx bxs-user' ></i>
				</div>
	            <div class="input-box">
	            	<input type="password" class="form-control"name="passwordLogin" required>
	            	<label for="">Password</label>
	                <i class='bx bxs-lock-alt' ></i>
	            </div>
	            
	            <button type="submit" class="btn" value ="Login">Login</button>
	         
	            
	         </form>        
	 	</div>
		<div class="info-text login">
	         <h2>Bentornato</h2>
	         <div class="logreg-link">
	                 <p>Non hai un account?  <a class="register-link" href="<%=request.getContextPath()%>/ServletPerPaginaRegistrazione" style="color: #2592B8">Registrati</a></p>
	         </div>
	         
	         <%String messaggio = (String)request.getAttribute("errormsg"); 
	         if(messaggio != null){%>
		        <p style="color: brown;"> <%=messaggio %> </p>
	         <%} %>
	    </div>
   </div>  
</body>
</html>
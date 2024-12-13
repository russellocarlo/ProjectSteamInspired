<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Errore - Steam</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/error.css">
<link rel="icon" href= "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1200px-Steam_icon_logo.svg.png"  type="image/x-icon" />   
</head>
<body>
		<div class="error-container">
	        <h1 class="error-code">Errore</h1>
	        <p class="error-message">
		        <%String messaggio = (String)request.getAttribute("errormsg"); 
		         if(messaggio != null){%>
			        <p style="color: brown; text-align: center; margin-top: 3vh;"> <%=messaggio %> </p>
	     		<%}else{ %>
	     		<p>Qualcosa è andato storto...</p>
	     		<%}%>
     		</p>
	        <a href="<%=request.getContextPath()%>/ServletPerHomePage" class="home-button">Torna alla Home</a>
    	</div>
    	
</body>
</html>
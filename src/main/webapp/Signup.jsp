<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
</head>
<body>
<jsp:include page="/Header.jsp"></jsp:include>
<% String error = (String) request.getAttribute("error"); 
	if (error != null){ %>
		<span style="color:red;"> <%= error %></span>
<% } %>
	<h1>Sign up</h1>
	<form action="/Projet/Signup" method="post">
		<label for="name">name</label>
		<input type="text" value="<%= (request.getParameter("name") != null)?request.getParameter("name"):""%>" name="name"><br>
		<label for="surname">surname</label>
		<input type="text" value="<%= (request.getParameter("surname") != null)?request.getParameter("surname"):""%>" name="surname"><br>
		<label for="email" >email</label>
		<input type="email" value="<%= (request.getParameter("email") != null)?request.getParameter("email"):""%>" name="email"><br>
		<label for="password">password</label>
		<input type="password" value="<%= (request.getParameter("password") != null)?request.getParameter("password"):""%>" name="password"><br>
		<label for="birthdate">birth date</label>
		<input type="date" value="<%= (request.getParameter("birthdate") != null)?request.getParameter("birthdate"):"1970-01-01"%>" name="birthdate" min="1900-01-01" max="2022-12-31"><br>
		<button type="submit">Sign up</button>
	</form>
	
	<p>you already have an account? <a href="/Projet/Login">Log in</a> instead!</p>
</body>
</html>
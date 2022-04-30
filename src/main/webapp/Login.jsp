<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log in</title>
</head>
<body>
<jsp:include page="/Header.jsp"></jsp:include>
	<h1>Log in</h1>
	<form action="/Projet/Login" method="post">
		<label for="email">email : </label>
		<input type="email" value="<%= (request.getParameter("email") != null)?request.getParameter("email"):""%>" name="email" /><br>
		<label for="password">password : </label>
		<input type="password" value="<%= (request.getParameter("password") != null)?request.getParameter("password"):""%>" name="password" /><br>
		<% String error = (String) request.getAttribute("error"); %>
		<% if (error != null){ %>
			<span style="color: red;"> <%= error %></span>
		<%} %>
		<br>
		<button type="submit">Log in</button>
	</form>
	<p>you don't have an account? <a href="/Projet/Signup">Sign up</a> instead!</p>
</body>
</html>
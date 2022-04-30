<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="tn.ridha.Beans.UserBean" %>
    <% UserBean userBean = (UserBean) session.getAttribute("userData"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/Header.jsp"></jsp:include>
	<h1>Profile</h1>
	<% String error = (String) request.getAttribute("error"); 
		if (error != null){ %>
			<span style="color:red;"><%= error %></span>
		<%}
	%>
	<form action="/Projet/Update" method="post">
		<label for="name">name</label>
		<input type="text" value="<%=userBean.getName()%>" name="name"><br>
		<label for="surname">surname</label>
		<input type="text" value="<%=userBean.getSurname() %>" name="surname"><br>
		<label for="email">email</label>
		<input type="email" value="<%=userBean.getEmail() %>" name="email"><br>
		<label for="password">password</label>
		<input type="password" name="password"><br>
		<label for="birthdate">birth date</label>
		<input type="date" value="<%=userBean.getBirthdate() %>" name="birthdate"><br>
		<button type="submit">Update</button>
	</form>
</body>
</html>
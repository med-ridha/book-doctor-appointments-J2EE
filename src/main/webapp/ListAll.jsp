<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.HashMap" %>
<%@ page import="tn.ridha.Beans.UserBean" %>
<%@ page import="tn.ridha.Dao.UserDao" %>
<html>
<head>
<meta charset="UTF-8">
<title>List all</title>
</head>
<style>
	table {
		width: 100%;
	}
</style>
<body>
<jsp:useBean id="userDao" scope="page" class="tn.ridha.Dao.UserDao" ></jsp:useBean>
<jsp:include page="/Header.jsp"></jsp:include>
<h1> List all users</h1>
<% String message = (String) request.getAttribute("message");
	if (message != null){ %>
		<span style="color:red;"> <%= message %></span>
	<% } %>
	
<h4> Filter By: </h4> 
<nav>
	<ul>
		<li><a href="/Projet/List">all</a>
		<li><a href="/Projet/List?filter=user">user</a>
		<li><a href="/Projet/List?filter=doctor">doctor</a>
		<li><a href="/Projet/List?filter=admin">admin</a>
	</ul>
</nav>
		<table border="1">
			<tr>
				<th>Name		</th>
				<th>Surname		</th>
				<th>email		</th>
				<th>Birth date	</th>
				<th>rank		</th>
				<th>		</th>
			</tr>
<%
	String filter = (String) request.getAttribute("filter");
	HashMap<Integer, UserBean> allUsers = userDao.getAll();
	if (filter == null){
		for (UserBean userData : allUsers.values()){
		%>
			<tr>
				<td><%= userData.getName() 		%></td>
				<td><%= userData.getSurname() 	%></td>
				<td><%= userData.getEmail() 	%></td>
				<td><%= userData.getBirthdate() %> </td>
				<td><%= userData.getLevel() 	%></td>
				<td><a onClick="return areYouSure();" href="/Projet/List?deleteId=<%= userData.get_id()%>" >Delete</a></td>
			</tr>
		<%
		}
	}else{
		for (UserBean userData : allUsers.values()){
			if (userData.getLevel().equals(filter)){
		%>
			<tr>
				<td><%= userData.getName() 		%></td>
				<td><%= userData.getSurname() 	%></td>
				<td><%= userData.getEmail() 	%></td>
				<td><%= userData.getBirthdate() %> </td>
				<td><%= userData.getLevel() 	%></td>
				<td><a onClick="return areYouSure();" href="/Projet/List?deleteId=<%= userData.get_id()%>" >Delete</a></td>
			</tr>
		<%
			}
		}
	}
%>
		</table>
</body>
<script>
	function areYouSure(){
		return (confirm("are you sure you want to delete this user?"))?true:false;
	}
</script>
</html>
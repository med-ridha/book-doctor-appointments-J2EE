<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="tn.ridha.Beans.DoctorBean" %>
<%@ page import="tn.ridha.Beans.UserBean" %>
<%@ page import="tn.ridha.Dao.UserDao" %>
<%@ page import="tn.ridha.Beans.DoctorBean" %>
<%@ page import="tn.ridha.Dao.DoctorDao" %>
<html>
<head>
<meta charset="UTF-8">
<title>Booking</title>
</head>
<style>
	table{
		width:100%;
	}
</style>
<body>
<jsp:useBean id="doctorDao" scope="page" class="tn.ridha.Dao.DoctorDao" ></jsp:useBean>
<jsp:include page="/Header.jsp"></jsp:include>
<h1> Booking </h1>
<%
	HashMap<Integer, DoctorBean> allDoctors = doctorDao.getAll();
	if (allDoctors.size() > 0){
		%>
<h4> Filter By specialty: </h4> 
<nav>
	<ul>
			<li><a href="/Projet/Booking">all</a>
	<% 
	String filter = (String) request.getAttribute("filter");
			HashSet<String> specialties = new HashSet<String>();
		for (DoctorBean doctorBean : allDoctors.values()){
			specialties.add(doctorBean.getSpecialty());
		}
		Iterator<String> values = specialties.iterator();
		while (values.hasNext()){
			String value = values.next();
		%>
			<li><a href="/Projet/Booking?filter=<%= value %>"><%= value %></a>
		<%} %>
	</ul>
</nav>
		<table border="1">
			<tr>
				<th>Name		</th>
				<th>Surname		</th>
				<th>Hospital	</th>
				<th>Specialty	</th>
				<th>		</th>
			</tr>
<%
	if (filter == null){
		for (DoctorBean doctorData : allDoctors.values()){
			UserDao userDao = new UserDao();
			String id = doctorData.getUser_id()+"";
			UserBean userData = userDao.get("_id", id);
		%>
			<tr>
				<td><%= userData.getName() 		%></td>
				<td><%= userData.getSurname() 	%></td>
				<td><%= doctorData.getHospital() 	%></td>
				<td><%= doctorData.getSpecialty() %> </td>
				<td><a href="/Projet/Booking?id=<%= doctorData.get_id()%>" >Book</a></td>
			</tr>
		<%
		}
	}else{
		for (DoctorBean doctorData : allDoctors.values()){
			if (doctorData.getSpecialty().equals(filter)){
			UserDao userDao = new UserDao();
			String id = doctorData.getUser_id()+"";
			UserBean userData = userDao.get("_id", id);
		%>
			<tr>
				<td><%= userData.getName() 		%></td>
				<td><%= userData.getSurname() 	%></td>
				<td><%= doctorData.getHospital() 	%></td>
				<td><%= doctorData.getSpecialty() %> </td>
				<td><a href="/Projet/Booking?id=<%= doctorData.get_id()%>" >Book</a></td>
			</tr>
		<%
			}
		}
	}

%>
		</table>
<%
	}else{
	%>	
		<span>Sorry we have no doctors at the moment</span>
		<%
	}
	%>
</body>
</html>
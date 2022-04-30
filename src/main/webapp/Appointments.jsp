<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="tn.ridha.Dao.AppointmentDao" %>
    <%@ page import="tn.ridha.Beans.AppointmentBean" %>
    <%@ page import="tn.ridha.Beans.DoctorBean" %>
    <%@ page import="tn.ridha.Beans.UserBean" %>
    <%@ page import="java.util.HashMap" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointments</title>
</head>
<style>
	table {
		width: 100%;
	}
</style>
<body>
<jsp:useBean id= "appDao"  scope="page" class="tn.ridha.Dao.AppointmentDao"></jsp:useBean>
<jsp:useBean id="doctorDao"  scope="page" class="tn.ridha.Dao.DoctorDao"></jsp:useBean>
<jsp:useBean id="userDao"  scope="page" class="tn.ridha.Dao.UserDao"></jsp:useBean>
<jsp:useBean id="userData"  scope="session" type="tn.ridha.Beans.UserBean"></jsp:useBean>
<jsp:include page="/Header.jsp"/>
	<h1>Appointments</h1>
<% String message = (String) request.getAttribute("message");
	boolean found = false;
	if (message != null){ %>
		<span> <%= message %></span>
	<% } %>
	
<%
	HashMap<Integer, AppointmentBean> listApp = appDao.getAll();
	HashMap<Integer, DoctorBean> allDoctors = doctorDao.getAll();
	HashMap<Integer, UserBean> allUsers = userDao.getAll();
%>
<%  if (listApp.size() > 0) { %>
	<table border="1">
		<tr>
			<% if (userData.getLevel().equals("admin")){ out.print("<th>name</th>");} %>
			<th>date</th>
			<th>date created</th>
			<th>doctor</th>
			<th>hospital</th>
		</tr>
		<% for (AppointmentBean appBean : listApp.values()) { %>
		<% if(userData.getLevel().equals("admin")){ 
				String doctorName = allUsers.get(allDoctors.get(appBean.getDoctor_id()).getUser_id()).getName();
				doctorName += " "+ allUsers.get(allDoctors.get(appBean.getDoctor_id()).getUser_id()).getSurname();
				String hospitalName = allDoctors.get(appBean.getDoctor_id()).getHospital();
				String userName = allUsers.get(appBean.getUser_id()).getName();
				userName += " " + allUsers.get(appBean.getUser_id()).getSurname();
		%>
			
			<tr>
				<td><%= userName %></td>
				<td><%= appBean.getDate() %></td>
				<td><%= appBean.getDatecreated() %></td>
				<td><%= doctorName %></td>
				<td><%= hospitalName %></td>
				<td style="width:5%;"><a onClick="return areYouSure()" href="?deleteId=<%= appBean.get_id() %>">Delete</a></td>
			</tr>
			
		<%}else{
			if (userData.get_id() == appBean.getUser_id()){ 
				String doctorName = allUsers.get(allDoctors.get(appBean.getDoctor_id()).getUser_id()).getName();
				found = true;
				doctorName += " "+ allUsers.get(allDoctors.get(appBean.getDoctor_id()).getUser_id()).getSurname();
				String hospitalName = allDoctors.get(appBean.getDoctor_id()).getHospital();
			%>
			<tr>
				<td><%= appBean.getDate() %></td>
				<td><%= appBean.getDatecreated() %></td>
				<td><%= doctorName %></td>
				<td><%= hospitalName %></td>
				<td style="width:5%;"><a onClick="return areYouSure()" href="?deleteId=<%= appBean.get_id() %>">done</a></td>
				<td style="width:5%;"><a onClick="return areYouSure()" href="?deleteId=<%= appBean.get_id() %>">Cancel</a></td>
			</tr>
			<% }
		}
		} %>
	</table>
	<%
			if (!found && !userData.getLevel().equals("admin")){
				%>
				<p> you don't have any Appointments</p>
				<%
			}
}else{ %>
	<p> you don't have any Appointments</p>
	<%} %>
	
	<% 
		if (userData.getLevel().equals("doctor")){
		%>
		<h1> your appointments </h1>
			<table border="1">
				<tr>
					<th>date</th>
					<th>date created</th>
					<th>name</th>
				</tr>
			<%
				for (AppointmentBean appBean: listApp.values()){
					if (allDoctors.get(appBean.getDoctor_id()).getUser_id() == userData.get_id()){
						%>
						
			<tr>
				<td><%= appBean.getDate() %></td>
				<td><%= appBean.getDatecreated() %></td>
				<td><%= allUsers.get(appBean.getUser_id()).getName()+" "+ allUsers.get(appBean.getUser_id()).getSurname()%></td>
				<td style="width:5%;"><a onClick="return areYouSure()" href="?deleteId=<%= appBean.get_id() %>">Delete</a></td>
			</tr>
						<%				
					}
				}
			%>
		
		
		
			</table>
		<%		
		}
	%>
</body>
<script>
	function areYouSure(){
		return (confirm("are you sure you want to remove this Appointment?"))?true:false;
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id = "userData" scope = "session" class="tn.ridha.Beans.UserBean" type="tn.ridha.Beans.UserBean"></jsp:useBean>
    <% if (userData.getName() != null){ %>
    	<h1 > Welcome <%= userData.getName() %>
    	<span>(<%= userData.getLevel() %>)</span>
    	 </h1>
    <%} %>
<header>
	<nav>
		<ul>
			<li><a href="/Projet/">Home</a></li>
			<% if ( userData.getName() != null ){ %>
				<li><a href="/Projet/Update">Profile</a></li>
				<li><a href="/Projet/Booking"> book an appointments</a></li>
				<% if (userData.getLevel().equals("admin")){ %>
					<li><a href="/Projet/Appointments">  See all appointments</a> </li>
					<li><a href="/Projet/List"> List all users</a> </li>
					<li><a href="/Projet/?level=user">Get user status (become user for this session)</a></li>
				<%}else{ %>
					<li><a href="/Projet/Appointments">Appointments</a> </li>
				<%} %>
				<li><a href="/Projet/?Logout=true">Log out</a></li>
				<% if ( userData.getLevel().equals("user") ) {%>
					<li><a href="/Projet/Becomeadoctor">Become a doctor</a></li>
					<li><a href="/Projet/?level=admin">Get admin status (become admin for this session)</a></li>
				<% }%>
			<%} %>
		</ul>
	</nav>
</header>
<hr>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Become a doctor</title>
</head>
<body>
<jsp:include page="/Header.jsp"></jsp:include>
<h1> Become a doctor </h1>
<form action="/Projet/Becomeadoctor" method="post">
	<label for="specialty">Specialty </label>
	<input type="text" name="specialty"/>
	<label for="hospital">Hospital </label>
	<input type="text" name="hospital"/>
	<label for="degree">Degree </label>
	<input type="file" name="degree"/>
	<button type="submit">Submit</button>
</form>
</body>
</html>
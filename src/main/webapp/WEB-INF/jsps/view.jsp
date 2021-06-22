<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User List</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	integrity="undefined" crossorigin="anonymous">
</head>
<body>
	<div class="container mx-auto">
		<div>
			<c:if test="${msg ne null}">
				<p>
					Hello from the
					<c:out value="${msg}"></c:out>
					<c:set var="now" value="<%=new Date()%>"></c:set>
				</p>
				<p>
					<b></>Time: </b>
					<fmt:formatDate type="both" value="${now}" />
				</p>
			</c:if>
		</div>
		<%
		int i = 1;
		%>
		<div class="mx-auto">
			<table class="table table-hover border-warning">
				<thead class="bg-dark text-white">
					<tr>
						<th>Id</th>
						<th>Username</th>
						<th>Email</th>
						<th>PhoneNo</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<%-- <td><c:out value="${user.id}"></c:out></td> --%>
							<td><%=i++%></td>
							<td><c:out value="${user.username}"></c:out></td>
							<td><c:out value="${user.email}"></c:out></td>
							<td><c:out value="${user.phoneNo}"></c:out></td>
							<td><a href="/mvc/delete?id=${user.id}"><button
										class="btn btn-danger">Delete</button></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${requiredPages>0}">
				<c:set var="totalPages" value="${requiredPages}" scope="session" />
				<c:forEach begin="1" end="${totalPages}" var="i">
					<a href="/mvc/hello?pNo=${i-1}"><button class="btn btn-primary pr-1"><c:out value="${i}" /></button></a>
				</c:forEach>
			</c:if>

			<div class="text-center">
				<a href="/mvc/addUser"><button class="btn btn-primary">Add</button></a>
			</div>
		</div>
	</div>
</body>
</html>
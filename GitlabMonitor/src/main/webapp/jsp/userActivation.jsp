<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/semantic.min.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
<title>User Activate</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
<h3>Unactivated Users</h3>

<table class="table table-striped table-bordered">
	<thead>
	<tr>
	<th>name</th>
	<th>email</th>
	<th>role</th>
	<th>emailActivated</th>
	<th>adminActivated</th>
	<th>activate</th>
	</tr>
	</thead>
	<tbody id="activatebody">
	<c:forEach items="${users}" var="user">
		<tr>
			<th><a href="/GitlabMonitor/admin/user?name=${user.name}">${user.name}</a></th>
			<th>${user.email}</th>
			<th>${user.role}</th>
			<th>
				<c:if test="${user.status == 0 }">no</c:if>
				<c:if test="${user.status == 1 }">yes</c:if>
			</th>
			<th>no</th>
			<th><a href="/GitlabMonitor/admin/activation/${user.name }/userActivation">activate</a></th>
		</tr>
	</c:forEach>	
	</tbody>
</table>
<footer>
<p>&copy; ise 2016</p>
</footer>
</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/semantic.min.js"></script>
  <script src="/GitlabMonitor/static/js/bootstrap-datepicker.min.js"></script>
  <script src="/GitlabMonitor/static/js/summary.js"></script> 
</html>
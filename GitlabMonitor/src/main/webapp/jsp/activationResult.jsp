<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Activate Result</title>
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/semantic.min.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
</head>
<body>
<div class="container">
	<h3>
	<c:if test="${emailSend != null}">
		${emailSend }
	</c:if>
	<c:if test="${noName != null}">
		no such user.
	</c:if>
	<c:if test="${emailActivationFail != null}">Sorry, email activate fail.
		<a type="button" href="/GitlabMonitor/activation/${user.name}">Resend activate email</a>
	</c:if>
	<c:if test="${emailActivationSuccess != null and  user !=null}">
		Congratulations ${user.name }, your email is activated.<br/>
		<c:if test="${user.status == 1 }">
		However, you have to wait for the administrator activating you.
		</c:if>
		<c:if test="${user.status == 3}">
		And you have been activated by administrator.<br/>
		Now, you can using GitlabMonitor now.<br/>
		Please <a href="/GitlabMonitor/login">login</a> now.
		</c:if>
	</c:if>
	</h3>
	<c:if test="${user != null}">
	<h3>Activate status</h3>
	<table class="table table-striped table-bordered">
		<thead>
		<tr>
		<th>name</th>
		<th>email</th>
		<th>role</th>
		<th>emailActivated</th>
		<th>adminActivated</th>
		</tr>
		</thead>
		<tbody id="activatebody">
		<tr>
			<!-- <th><a href="/GitlabMonitor/admin/user?name=${user.name}">${user.name}</a></th> -->
			<th>${user.name}</th>
			<th>${user.email}</th>
			<th>${user.role}</th>
			<th>
				<c:if test="${user.status == 0 or user.status == 2}">no</c:if>
				<c:if test="${user.status == 1 or user.status == 3}">yes</c:if>
			</th>
			<th>
				<c:if test="${user.status == 0 or user.status == 1}">no</c:if>
				<c:if test="${user.status == 2 or user.status == 3}">yes</c:if>
			</th>
			</tr>	
		</tbody>
	</table>
	</c:if>
	
<footer>
<p>&copy; ise 2016</p>
</footer>
</div>

</body>
</html>
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
	
</body>
</html>
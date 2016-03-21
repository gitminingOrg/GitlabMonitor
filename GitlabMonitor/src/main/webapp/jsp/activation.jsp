<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
		Now, you can using GitlabMonitor now.
		<a href="/GitlabMonitor/login">login</a>
		</c:if>
	</c:if>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet"
	href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css"
	type="text/css" charset="utf-8">
<title>Gitlab Monitor Login</title>
</head>
<body>
	<div class="container">
	<div class="row">
	
	<div class="col-md-4">
	</div>
	
	<div class="col-md-4">
		<s:url var="authUrl" value="/login/check"></s:url>
		<form class="form-signin" method="post" action="${authUrl}">
			<h2 class="form-signin-heading">Please sign in</h2>
			<label for="inputEmail" class="sr-only" >Email address</label> 
			<input type="text" id="inputEmail" class="form-control" name="j_username" placeholder="login name"> 
			<label for="inputPassword" class="sr-only">Password</label> 
			<input type="password" id="inputPassword" class="form-control" name="j_password" placeholder="Password" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">Remember me</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Signin</button>
		</form>
	</div>
	
	<div class="col-md-4">
	</div>
	</div>
	</div>
</body>
</html>
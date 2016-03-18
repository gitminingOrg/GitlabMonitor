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
<title>Gitlab Monitor Register</title>
</head>
<body>
	<div class="container">
	<div class="row">
	
	<div class="col-md-4">
	</div>
	
	<div class="col-md-4">
		<form class="form-signin" method="post" action="#">
			<h2 class="form-signin-heading">Please Register</h2>
			
			<label for="name" class="sr-only">Name</label> 
			<input type="text" id="name" class="form-control" placeholder="name" required>
			<label for="inputEmail" class="sr-only" >Email address</label> 
			<input type="text" id="inputEmail" class="form-control" placeholder="login name" required autofocus> 
			<label for="inputPassword" class="sr-only">Password</label> 
			<input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
			
			<button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
		</form>
	</div>
	
	<div class="col-md-4">
	</div>
	</div>
	</div>
</body>
</html>
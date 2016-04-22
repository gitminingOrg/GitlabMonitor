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
<title>Gitlab Monitor Password Reset</title>
</head>
<body>
	<div class="container">
	<div class="row">
	
	<div class="col-md-4">
	</div>
	
	<div class="col-md-4">
		
			<h4 class="form-signin-heading">Email: ${ email } . Type your password here:</h4>
			<label for="password" class="sr-only" >New Password</label> 
			<input type="password" id="password" class="form-control" name="password" placeholder="password"> 
			<input type="hidden" id="email" value="${email}">
			<input type="hidden" id="token" value="${token}">  
			<button class="btn btn-info btn-block" onclick="passwordReset(document.getElementById('email').value,document.getElementById('token').value,document.getElementById('password').value)">Reset</button>
		
			<p id="success" style="display:none">modify success, try login again.<a href="/GitlabMonitor/login"></a></p>
			<p id="fail" style="display:none">modify failed, try again.</p>
	</div>
	
	<div class="col-md-4">
	</div>
	</div>
	</div>
</body>
<script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
<script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
<script src="/GitlabMonitor/static/js/reset.js"></script> 
</html>
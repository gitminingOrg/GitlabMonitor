<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Gitlab Monitor Login</title>
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<script src="/GitlabMonitor/static/js/common.js"></script>
<script src="/GitlabMonitor/static/js/supersized.3.2.7.min.js"></script>
<script src="/GitlabMonitor/static/js/supersized-init.js"></script>
<script src="/GitlabMonitor/static/js/jquery.validate.min.js?var1.14.0"></script>
<link rel="stylesheet" href="/GitlabMonitor/static/css/style.css">
</head>
<body>
<div class="login-container">
	<h1>ShareLink</h1>
	
	<div class="connect">
		<p>Link the world. Share to world.</p>
	</div>
	
	<form action="/login/check" method="post" class="form-signin">
		<div>
			<input type="text" name="username" class="username" placeholder="用户名" autocomplete="off"/>
		</div>
		<div>
			<input type="password" name="password" class="password" placeholder="密码" oncontextmenu="return false" onpaste="return false" />
		</div>
		<button id="submit" type="submit">登 陆</button>
	</form>

	<a href="register.html">
		<button type="button" class="register-tis">还有没有账号？</button>
	</a>

</div>
</body>
</html>
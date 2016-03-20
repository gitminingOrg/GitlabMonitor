<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<script type="text/javascript" src="/GitlabMonitor/static/js/force.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/d3.min.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/jquery-ui.min.js" ></script>
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">

</head>
<body>
<div id="force">
	<script>
		$.post("/GitlabMonitor/relation",function(data){
			force("#force",800,800,data);
		})
	</script>
</div>
</body>
</html>
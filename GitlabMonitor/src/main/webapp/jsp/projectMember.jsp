<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
<h2>Team Member</h2>
	<form id="commitRange" class="form-inline">
		<input type="text" id="team" class="form-control" value="${team}" placeholder="team name"/>
		<input type="text" id="dayStart" class="form-control" placeholder="start day"/>
		<input type="text" id="dayEnd" class="form-control" placeholder="end day"/>
		<input type="button" class="btn btn-primary" value="search" onclick="projectMember(document.getElementById('team').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value);">
	</form>
	
	<div id="chart"></div>
</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/sand-signika.js"></script>
 <script src="/GitlabMonitor/static/js/teamMember.js"></script> 
 <script src="/GitlabMonitor/static/js/library/angular.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
  <script src="/GitlabMonitor/static/js/bootstrap-datepicker.min.js"></script>
 <script type="text/javascript">
 $('#dayStart').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
 });
 $('#dayEnd').datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true
});
 </script>
</html>

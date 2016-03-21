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

<div id="content" class="container">
<h2>Student Detail</h2>
<div class="btn-group" style="float:right;" role="group" aria-label="...">
	  <button type="button" class="btn btn-default">Personal Commit</button>
	  <div class="btn-group" role="group">
	    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      Time Range
	      <span class="caret"></span>
	    </button>
	    <ul class="dropdown-menu">
	      <li><a href="#" onclick="studentCommit2(document.getElementById('student').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'year');">Recent Year</a></li>
	      <li><a href="#" onclick="studentCommit2(document.getElementById('student').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'month');">Recent Month</a></li>
	      <li><a href="#" onclick="studentCommit2(document.getElementById('student').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'week');">Recent Week</a></li>
	    </ul>
	  </div>
	</div>
	<form id="commitRange" class="form-inline" action="/GitlabMonitor/student/commit">
		<input type="text" id="student" name="student" class="form-control" value="${student}" placeholder="student name"/>
		<input type="text" class="form-control" id="dayStart" name="dayStart" readonly="readonly" value="${dayStart}" placeholder="start day"/>
		<input type="text" class="form-control" id="dayEnd" name="dayEnd" readonly="readonly" value="${dayEnd}" placeholder="end day"/>
		<input type="submit" class="btn btn-primary" value="search">
	</form>
	<br />
	<div id="user"></div>
</div>
<br/>
<div class="container">
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Student Introduction</h3>
  </div>
  <div class="panel-body">
	<p>Student: ${studentInfo.name}</p>
	<p>Email: ${studentInfo.email}</p>
	<p>Bio: ${studentInfo.bio}</p>
	<p>Page: <a href="${studentInfo.web_url}" target="view_window">${studentInfo.web_url}</a></p>
	<p>Student No.: ${studentInfo.studentno}</p>
  </div>
</div>
</div>

<div id="board" class="container">
<h2>Spit Out</h2>
<form class="form-inline">
<input type="text" class="form-control" id="sen" placeholder="say something"/>
<input type="text" class="form-control" id="token" placeholder="token"/>
<input type="button" class="btn btn-primary" value="commit" onclick="studentComment(document.getElementById('student').value,document.getElementById('token').value,document.getElementById('sen').value);">
</form>
<div id="words">
<c:forEach items="${comments}" var="comment">
	<p>${comment.words} -----comment at: ${comment.time}</p>
</c:forEach>
</div>
</div>
	<hr />
	<footer>
        <p>&copy; ise 2016</p>
    </footer>
</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/sand-signika.js"></script>
 <script src="/GitlabMonitor/static/js/student.js"></script> 
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

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
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Gitlab Monitor</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home <span class="sr-only">(current)</span></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Student <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/GitlabMonitor/student/summary">Student Summary</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/student/commit">Student Commit</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/student/event">Student Event</a></li>
          </ul>
        </li>
        
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Team <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/GitlabMonitor/project/summary">Team Summary</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/project/commit">Team Commit</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/project/event">Team Event</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/project/team">Team Member</a></li>
          </ul>
        </li>
      </ul>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="container">
<div id="content" class="container">
	<h2>Project Detail</h2>
	<div class="btn-group" style="float:right;" role="group" aria-label="...">
	  <button type="button" class="btn btn-default" onclick="showDailyChart();">Project Commit</button>
	  <button type="button" class="btn btn-default" onclick="showMemberChart();">Member Commit</button>
	  <button type="button" class="btn btn-default" onclick="showInfoChart();">Member Relationship</button>
	  <div class="btn-group" role="group">
	    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      Time Range
	      <span class="caret"></span>
	    </button>
	    <ul class="dropdown-menu">
	      <li><a href="#" onclick="projectCommit(document.getElementById('team').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'year');">Recent Year</a></li>
	      <li><a href="#" onclick="projectCommit(document.getElementById('team').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'month');">Recent Month</a></li>
	      <li><a href="#" onclick="projectCommit(document.getElementById('team').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'week');">Recent Week</a></li>
	    </ul>
	  </div>
	</div>
	<form id="commitRange" class="form-inline">
		<input type="text" class="form-control" id="team" placeholder="team name" value="${team}"/>
		<input type="text" class="form-control" id="dayStart" value="${dayStart}" placeholder="start day"/>
		<input type="text" class="form-control" id="dayEnd" value="${dayEnd}" placeholder="end day"/>
		<input type="button" class="btn btn-primary" value="search" onclick="projectCommit(document.getElementById('team').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value);">
	<%-- 	<a href="/GitlabMonitor/project/team?team=${team}" class="btn btn-success">Member Detail</a> --%>
	</form>

	<br />
	<div id="dailyChart"></div>
	<div id="memberChart" style="display:none"></div>
	<div id="infoChart" style="display:none"></div>
</div>
<br />
<div id="team introduction" class="row container">
	<div class="col-md-5">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">Team Introduction</h3>
		  </div>
		  <div class="panel-body">
			<p>Team: ${teaminfo.name}</p>
			<p>Description: ${teaminfo.description}</p>
			<p>Page: <a href="${teaminfo.web_url}" target="view_window">${teaminfo.web_url}</a></p>
		  </div>
		</div>
	</div>
	
	<div class="col-md-3">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">Team Member</h3>
		  </div>
		  <div class="panel-body">
			<c:forEach items="${students}" var="student">	
				<p>Member name : <a href="/GitlabMonitor/student/commit?student=${student.name}">${student.name}</a><p/>
			</c:forEach>
		  </div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">Team Projects</h3>
		  </div>
		  <div class="panel-body">
		    <p>
			<c:forEach items="${projects}" var="project">
				<a href="/GitlabMonitor/project/commit?team=${teaminfo.name}">${project.name}</a>
			</c:forEach>
			<p/>
		  </div>
		</div>	
	</div>
</div>

<!-- <div id="board"> -->
<!-- <h2>Spit out</h2> -->
<!-- <form class="form-inline"> -->
<!-- <input type="text" class="form-control" id="sen" placeholder="say something"/> -->
<!-- <input type="text" class="form-control" id="token" placeholder="token"/> -->
<!-- <input type="button" class="btn btn-primary" value="commit" onclick="projectComment(document.getElementById('team').value,document.getElementById('token').value,document.getElementById('sen').value);"> -->
<!-- </form> -->

<!-- <div id="words"> -->
<%-- <c:forEach items="${comments}" var="comment"> --%>
<%-- 	<p>${comment.words} -----comment at: ${comment.time}</p> --%>
<%-- </c:forEach> --%>
<!-- </div> -->
<!-- </div> -->


</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/sand-signika.js"></script>
 <script src="/GitlabMonitor/static/js/team.js"></script> 
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

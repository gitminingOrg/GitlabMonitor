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
<h2>Student Summary</h2>
<form id="commitRange" class="form-inline" action="/GitlabMonitor/student/summary" method="POST" >
	<input type="text" id="dayStart" class="form-control" name="dayStart" value="${dayStart}" placeholder="start day"/>
	<input type="text" id="dayEnd" class="form-control" name="dayEnd" value="${dayEnd}" placeholder="end day"/>
	<select class="form-control" name="commitOrder">
		  <option value ="commit_count">commit_count</option>
		  <option value="add_line">add_line</option>
		  <option value="delete_line">delete_line</option>
		  <option value ="java_file">java_file</option>
		  <option value="total_add">total_add</option>
		  <option value="autotal_deletedi">total_delete</option>
	</select>
	<select class="form-control" name="eventOrder">
		  <option value ="total">total</option>
		  <option value="push">push</option>
		  <option value="issue">issue</option>
		  <option value ="comment">comment</option>
		  <option value="create">create</option>
	</select>
	<select class="form-control" name="method">
		  <option value ="desc">desc</option>
		  <option value ="asc">asc</option>
	</select>
	<input type="submit" class="btn btn-primary" value="filter">
</form>

<h3>Student Commit</h3>
<table class="table table-striped table-bordered">
	<thead><tr><th>name</th> <th>commit_count</th> <th>add_line</th> <th>delete_line</th> <th>java_file</th> <th>total_add</th> <th>total_delete</th></tr></thead>
	<tbody>
	<c:forEach items="${commits}" var="commit">
		<tr><th><a href="/GitlabMonitor/student/commit?student=${commit.student}">${commit.student}</a></th><th>${commit.commit_count}</th><th>${commit.add_line}</th><th>${commit.delete_line}</th>
		<th>${commit.java_file}</th><th>${commit.total_add}</th><th>${commit.total_delete}</th></tr>
	</c:forEach>	
	</tbody>
</table>

<h3>Student Event</h3>
<table class="table table-striped table-bordered">
	<thead><tr><th>name</th> <th>push</th> <th>issue</th> <th>comment</th> <th>create</th> <th>total</th></tr></thead>
	<tbody>
	<c:forEach items="${events}" var="event">
		<tr><th><a href="/GitlabMonitor/student/event?student=${event.student}">${event.student}</a></th><th>${event.push}</th><th>${event.issue}</th><th>${event.comment}</th>
		<th>${event.create}</th><th>${event.total}</th></tr>
	</c:forEach>	
	</tbody>
</table>
</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/sand-signika.js"></script>
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

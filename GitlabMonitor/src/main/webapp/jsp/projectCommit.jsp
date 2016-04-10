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
<script type="text/javascript" src="/GitlabMonitor/static/js/force.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/heatchart.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/d3.min.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/jquery-ui.min.js" ></script>

</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
<div id="content" class="container">
	<h2>Project Detail</h2>
	<div class="btn-group" style="float:right;" role="group" aria-label="...">
	  <button type="button" class="btn btn-default" onclick="showDailyChart();">Commits Info</button>
	  <button type="button" class="btn btn-default" onclick="showMemberChart();">Member Commits</button>
	  <button type="button" class="btn btn-default" onclick="showInfoChart();">Relationship</button>
	  <button type="button" class="btn btn-default" onclick="showHeatChart();">Work Time</button>
	  <div class="btn-group" role="group">
	    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      Time Range
	      <span class="caret"></span>
	    </button>
	    <ul class="dropdown-menu">
	      <li><a href="#" onclick="projectCommit(document.getElementById('projectId').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'year');">Recent Year</a></li>
	      <li><a href="#" onclick="projectCommit(document.getElementById('projectId').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'month');">Recent Month</a></li>
	      <li><a href="#" onclick="projectCommit(document.getElementById('projectId').value,document.getElementById('dayStart').value,document.getElementById('dayEnd').value, 'week');">Recent Week</a></li>
	    </ul>
	  </div>
	</div>
	<form id="commitRange" class="form-inline" method="POST" action="/GitlabMonitor/project/commit">
		<input type="hidden" id="projectId" name="projectId" value="${projectId}" />
		<input type="text" class="form-control" readonly="readonly" id="team" name="team" placeholder="team name" value="${team}" onclick="showChooseTeam()"/>
		<input type="text" class="form-control" id="dayStart" name="dayStart" readonly="readonly" value="${dayStart}" placeholder="start day"/>
		<input type="text" class="form-control" id="dayEnd" name="dayEnd" readonly="readonly" value="${dayEnd}" placeholder="end day"/>
		<input type="submit" class="btn btn-primary" value="search">
	<%-- 	<a href="/GitlabMonitor/project/team?team=${team}" class="btn btn-success">Member Detail</a> --%>
	</form>

	<br />
	<div id="chooseTeam" style="display: none"> 
		<h3>Satisfied Teams</h3> 
		<form id="chooseTeamForm" class="form-inline">
			<input type="text" id="possible_name" class="form-control" style="width:300px;" placeholder="Type a substring your team name contains" id="teamlike">
			<input type="button" class="btn btn-primary" value="Search Team" onclick="searchTeams(document.getElementById('possible_name').value)" >
			<input type="button" class="btn btn-default" value="Cancel" onclick="cancelChooseTeam()">
		</form>
		
		<div id="satisfieldTeams">
		</div>
		
	</div>
	<div id="dailyChart"></div>
	<div id="memberChart" style="display:none"></div>
	<div id="infoChart" style="display:none">
		<script>
			var endDay = document.getElementById('dayEnd').value;
			var projectID = "${projectId}";
			var sdata = {endDay:endDay,projectID:projectID}
			$.post("/GitlabMonitor/relation",sdata,function(data){
				force("#infoChart",1150,400,data);
			})
		</script>
	</div>
	<div id="heatChart" style="display:none">
		<script>
			var projectID = "${projectId}";
			var sdata = {projectID:projectID}
			$.post("/GitlabMonitor/dayhour",sdata,function(data){
				heatchart(data);
			})
		</script>
	</div>
</div>
<br />
<div class="container">
	<div id="team introduction" class="row container">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">Team Introduction</h3>
		  </div>
		  <div class="panel-body">
			<p>Team: ${teaminfo.name}</p>
			<p>Description: ${teaminfo.description}</p>
			<p>Page: <a href="${teaminfo.web_url}" target="view_window">${teaminfo.web_url}</a></p>
			<p>Member name :<c:forEach items="${students}" var="student">	
			 <a href="/GitlabMonitor/student/commit?student=${student.name}">${student.name}</a>
			</c:forEach></p>
			
			<p>Team Projects:</p>
			<p>
			<c:forEach items="${projects}" var="project">
				<a href="/GitlabMonitor/project/commit?team=${teaminfo.name}&id=${project.id}">${project.name}</a>
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
 <script src="/GitlabMonitor/static/js/team.js"></script> 
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

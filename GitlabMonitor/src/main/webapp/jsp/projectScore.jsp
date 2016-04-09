<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/semantic.min.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
<title>Project Score</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
<div id="course introduction" class="row container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Course Introduction</h3>
	  </div>
	  <div class="panel-body">
		<p>Course Name: ${course.name}</p>
		<p>Start Time: ${course.starttime}</p>
		<p>Start Time: ${course.endtime}</p>
		<p>Teachers: ${course.teachers}</p>	
	  </div>
	</div>
</div>
</div>

<div class="container">
<div class="btn-group" style="float:right;" role="group" aria-label="...">
	  <button type="button" class="btn btn-primary">Course</button>
	  <div class="btn-group" role="group">
	    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Choose&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      <span class="caret"></span>
	    </button>
	    <ul class="dropdown-menu">
	      <c:forEach items="${courseNames}" var="name">
	      	<li><a href="/GitlabMonitor/project/score?course_name=${name}">${name}</a></li>
	      </c:forEach>

	    </ul>
	  </div>
</div>
<form id="addColumn" class="form-inline" action="/GitlabMonitor/project/score/add" method="POST" >
	<input type="text" id="column" name="column" class="form-control" placeholder="new column name" onkeydown="if(event.keyCode==13)return false;"  required>
	<input type="hidden" id="course_id" name="course_id" value="${course.id}">
	<input type="hidden" id="course_name" name="course_name" value="${course.name}">
	<button class="btn btn-primary" type="button" class="form-control" onclick="checkColumnName(document.getElementById('column').value)">add a column</button>
	<button id="showButton" class="btn btn-primary" type="button" class="form-control" onclick="showStatistics('${course.name}')">show statistics</button>
	<button id="scoreButton" style="display: none" class="btn btn-primary" type="button" class="form-control" onclick="showScoreTable()">show scores</button>
</form>
<div id="mentionwords" style="display: none;font-weight: bold">Only suppor digits, letters and Chinese characters&nbsp;&nbsp;<button class="btn btn-warning" type="button" class="form-control" onclick="cancelDelete();">&nbsp;OK&nbsp;</button></div>
<form id="deleteColumn" class="form-inline" style="display: none" action="/GitlabMonitor/project/score/delete" method="POST" >
	<span id="warning_words" style="font-weight: bold">Are you sure to remove item named 'test1'?</span>
	<input type="hidden" id="delete_item" name="delete_item">
	<button class="btn btn-danger" type="submit" class="form-control" >YES</button>
	<button class="btn btn-default" type="button" class="form-control" onclick="cancelDelete();">Cancel</button>
</form>
<!-- <div class="row"> -->
<!-- 	<div class="col-md-9"> -->
		<br />
		<div id="scoreTable">
		<table class="table table-striped table-bordered">
		<thead><tr> 
		<th>project</th>
		<c:forEach items="${items}" var="item"><th>${item.name}&nbsp;<i class="angle left icon" onmouseover="javascript:overLeft(this);" onmouseout="javascript:outLeft(this);"></i>
		<i class="remove icon" onmouseover="javascript:overRemove(this);" onmouseout="javascript:outRemove(this);" onclick="show_remove('${item.name}','${item.id}');"></i></th></c:forEach>
		<th>result</th>
		</tr>
		</thead>
		<tbody id="commit_body">
<%-- 		<c:forEach items="${projects}" var="project" varStatus="outloop"> --%>
<%-- 			<tr><th>${project.name}</th> --%>
<%-- 			<c:forEach items="${itemScores}" var="item"> --%>
<%-- 				<th onclick="showText('${item.id}','${project.id}');"><div id="ID${item.id}L${project.id}">${item.scores[outloop.index].score}</div><input onblur="changeScore('${item.id}','${project.id}',document.getElementById('ID${item.id}T${project.id}').value);" id="ID${item.id}T${project.id}" style="display: none" type="text" value="${item.scores[outloop.index].score}"></th> --%>
<%-- 			</c:forEach> --%>
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
		
		<c:forEach items="${teamScores}" var="teamScore" varStatus="outloop">
			<tr><th>${teamScore.project_name}</th>
			<c:forEach items="${teamScore.scores}" var="teamscore">
				<th onclick="showText('${teamscore.item_id}','${teamscore.project_id}');"><div id="ID${teamscore.item_id}L${teamscore.project_id}">${teamscore.score}</div><input onblur="changeScore('${teamscore.item_id}','${teamscore.project_id}',document.getElementById('ID${teamscore.item_id}T${teamscore.project_id}').value);" id="ID${teamscore.item_id}T${teamscore.project_id}" style="display: none" type="text" value="${teamscore.score}"></th>
			</c:forEach>
			<th>0</th>
			</tr>
		</c:forEach>
		</tbody>
		</table>
		</div>
		<div id="statistics" style="display: none">
			<div id="statisticsTable"></div>
			<div id="statisticsChart"></div>
		</div>
<!-- 	</div> -->	
<!-- 	<div class="col-md-3" id="grid" style="display:none"> -->
<!-- 		<table class="table table-striped table-bordered"> -->
<!-- 		<thead><tr> <th><input type="text" class="form-control" placeholder="item name"/></th></tr></thead> -->
<!-- 		<tbody id="commit_body"> -->
<%-- 			<c:forEach items="${itemScores[0].scores}" var="score" varStatus="outloop"> --%>
<%-- 			<tr><th><input type="text" class="form-control" placeholder="${score.project_name}"/></th></tr> --%>
<%-- 		</c:forEach> --%>
<!-- 		</tbody> -->
<!-- 		</table> -->
<!-- 		<button class="btn btn-primary" type="button" onclick="showButton();">submit</button> -->
<!-- 	</div> -->
	<hr />
	<footer>
        <p>&copy; ise 2016</p>
    </footer>
</div>
</body>
  <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts.js"></script>
 <script src="/GitlabMonitor/static/js/library/highcharts-3d.js"></script>
 <script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/semantic.min.js"></script>
 <script src="/GitlabMonitor/static/js/score.js"></script> 
</html>
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
<title>Project Score</title>
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
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Project <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/GitlabMonitor/project/summary">Project Summary</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/project/commit">Project Detail</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/project/score">Project Score</a></li>
          </ul>
        </li>
      </ul>

    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
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
<form id="addColumn" class="form-inline" action="/GitlabMonitor/project/score/add" method="POST" >
	<input type="text" id="column" name="column" class="form-control" placeholder="new column name" required>
	<input type="hidden" id="course_id" name="course_id" value="${course.id}">
	<input type="hidden" id="course_name" name="course_name" value="${course.name}">
	<button class="btn btn-primary" type="submit" class="form-control" >add a column</button>
</form>

<!-- <div class="row"> -->
<!-- 	<div class="col-md-9"> -->
		<table class="table table-striped table-bordered">
		<thead><tr> 
		<th>project</th>
		<c:forEach items="${itemScores}" var="item"><th>${item.name}</th></c:forEach>
		</tr></thead>
		<tbody id="commit_body">
			<c:forEach items="${itemScores[0].scores}" var="score" varStatus="outloop">
			<tr><th>${score.project_name}</th>
			<c:forEach items="${itemScores}" var="item">
				<th onclick="showText('${item.id}','${score.project_id}');"><div id="ID${item.id}L${score.project_id}">${item.scores[outloop.index].score}</div><input onblur="changeScore('${item.id}','${score.project_id}',document.getElementById('ID${item.id}T${score.project_id}').value);" id="ID${item.id}T${score.project_id}" style="display: none" type="text" value="${item.scores[outloop.index].score}"></th>
			</c:forEach>
			</tr>
		</c:forEach>
		</tbody>
		</table>
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
<!-- </div> -->

</div>
</body>
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
 <script src="/GitlabMonitor/static/js/score.js"></script> 
</html>
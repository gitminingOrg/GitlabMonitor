<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
 <script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/semantic.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/less.js"></script>
 <script src="/GitlabMonitor/static/js/library/easing.min.js"></script>
 <script src="/GitlabMonitor/static/js/library/highlight.min.js"></script>
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/semantic.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<title>GitlabMonitor</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">

<button type="button" class="btn btn-warning btn-sm" onclick="show()">Add new course</button>
<c:forEach items="${courses}" var="course">
<div id="course introduction" class="row container">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <a href=""><h3 class="panel-title">Course Introduction : ${course.name}</h3></a> 
	  </div>
	  <div class="panel-body">
		<p>Course Name: ${course.name}</p>
		<p>Start Time: ${course.starttime}</p>
		<p>Start Time: ${course.endtime}</p>
		<p>Teachers: ${course.teachers}</p>	
		<button type="button" class="btn btn-primary btn-sm" onclick="show()">Delete</button>
	  </div>
	</div>
</div>
</c:forEach>

<div class="ui modal">
  <i class="close icon"></i>
  <div class="header">
    <h3>Add course</h3>
  </div>
  <div class="content">
  	<center>
	<input type="text" class="form-control" style="width:500px;height:50px;" id="courseName" name="courseName" placeholder="course name"/>
    <input type="text" class="form-control" style="width:500px;height:50px;" id="dayStart" name="starttime" placeholder="start time"/>
    <input type="text" class="form-control" style="width:500px;height:50px;" id="dayEnd" name="endtime" placeholder="end time"/>
    <input type="text" class="form-control" style="width:500px;height:50px;" id="teachers" name="teachers" placeholder="teachers"/>
    After adding a new course, you can make some projects choose this course.
    </center>
  </div>
  <div class="actions">
    <div class="ui black deny button">
      Cancel
    </div>
    <div class="ui positive right labeled icon button" onclick="addCourse()">
      Okay, just add.
      <i class="checkmark icon"></i>
    </div>
  </div>
</div>

</div>



</body>

 <script src="/GitlabMonitor/static/js/course.js"></script>
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
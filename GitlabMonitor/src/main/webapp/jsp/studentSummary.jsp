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
</head>
<body>
<%@ include file="nav.jsp" %>
<div class="container">
<h2>Student Summary</h2>
<div class="btn-group" style="float:right;" role="group" aria-label="...">
	  <button type="button" class="btn btn-primary" onclick="showInfoChart();">Time Range</button>
	  <div class="btn-group" role="group">
	    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	      Choose
	      <span class="caret"></span>
	    </button>
	    <ul class="dropdown-menu">
	      <li><a href="#" onclick="studentSummary(document.getElementById('formula').value,document.getElementById('filter').value,'year');">Recent Year</a></li>
	      <li><a href="#" onclick="studentSummary(document.getElementById('formula').value,document.getElementById('filter').value,'month');">Recent Month</a></li>
	      <li><a href="#" onclick="studentSummary(document.getElementById('formula').value,document.getElementById('filter').value,'week');">Recent Week</a></li>
	    </ul>
	  </div>
</div>
<form id="commitRange" class="form-inline" action="/GitlabMonitor/student/summary" method="POST" >
	<input type="text" id="dayStart" class="form-control" name="dayStart" value="${dayStart}" placeholder="start day"/>
	<input type="text" id="dayEnd" class="form-control" name="dayEnd" value="${dayEnd}" placeholder="end day"/>
		<input type="submit" class="btn btn-primary" value="&nbsp;&nbsp;GO !&nbsp;&nbsp;">
		<br />
		<div class="input-group">
  			<span class="input-group-addon" id="basic-addon1">FORMULA</span>
  			<input type="text" id="formula" class="form-control" style="width:500px;" name="formula" value="${formula}" placeholder="support: +-*/%^() commit_count add_line delete_line java_file total_add total_delete and regular numbers"/>
		</div>
		<br />
		<div class="input-group">
			<span class="input-group-addon" id="basic-addon2">FILTER&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<input type="text" id="filter" class="form-control" style="width:500px;" name="filter" value="${filter}" placeholder="commit_count>3;add_line<10;delete_line>100"/>
		</div>
</form>

<h3>Student Commit</h3>
<table class="table table-striped table-bordered">
	<thead><tr>
	<th>rank</th>
	
	<th onmouseover="showIcon('name');" onmouseout="hideIcon('name');">
	name
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','student');" id="iconname" class="pointing down icon" style="display: none"></i></th>

	<th onmouseover="showIcon('commitcount');" onmouseout="hideIcon('commitcount');">
	commit_count
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','commit_count');" id="iconcommitcount" class="pointing down icon" style="display: none"></i></th> 
 
	<th onmouseover="showIcon('addline');" onmouseout="hideIcon('addline');">
	add_line
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','add_line');" id="iconaddline" class="pointing down icon" style="display: none"></i></th> 
 
	<th onmouseover="showIcon('deleteline');" onmouseout="hideIcon('deleteline');">
	delete_line
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','delete_line');" id="icondeleteline" class="pointing down icon" style="display: none"></i></th> 
 
	<th onmouseover="showIcon('javafile');" onmouseout="hideIcon('javafile');">
	modified_file
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','java_file');" id="iconjavafile" class="pointing down icon" style="display: none"></i></th> 
 
	<th onmouseover="showIcon('totaladd');" onmouseout="hideIcon('totaladd');">
	total_add
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','total_add');" id="icontotaladd" class="pointing down icon" style="display: none"></i></th> 

	<th onmouseover="showIcon('totaldelete');" onmouseout="hideIcon('totaldelete');">
	total_delete
	<i onclick="refreshStudentTable(document.getElementById('dayStart').value,document.getElementById('dayEnd').value,'${formula}','${filter}','total_delete');" id="icontotaldelete" class="pointing down icon" style="display: none"></i></th>
 
	<th>formula</th>
	</tr></thead>
	<tbody id="commit_body">
	<c:forEach items="${commits}" var="commit" varStatus="status">
		<tr><th>${status.index + 1}</th><th><a href="/GitlabMonitor/student/commit?student=${commit.student}">${commit.student}</a></th><th>${commit.commit_count}</th><th>${commit.add_line}</th><th>${commit.delete_line}</th>
		<th>${commit.java_file}</th><th>${commit.total_add}</th><th>${commit.total_delete}</th><th>${commit.formula}</th></tr>
	</c:forEach>	
	</tbody>
</table>
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
 <script src="/GitlabMonitor/static/js/bootstrap-datepicker.min.js"></script>
 <script src="/GitlabMonitor/static/js/summary.js"></script> 
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/semantic.min.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap-datepicker3.standalone.min.css">
<title>User Activate</title>
</head>
<body>
<%@ include file="nav.jsp" %>
<h3>Unactivated Users</h3>
<table class="table table-striped table-bordered">
	<thead><tr>
	<th>rank</th>
	
	<th>name</th>
	
	<th>email</th>
	
	<th>role</th>
	
	<th>emailActivated</th>
	
	<th>adminActivated</th>

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
	java_file
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
</body>
</html>
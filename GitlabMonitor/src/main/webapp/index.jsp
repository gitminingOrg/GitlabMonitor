<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/GitlabMonitor/static/css/bootstrap.css">
<link rel="stylesheet" href="/GitlabMonitor/static/js/jquery/css/ui-lightness/jquery-ui-1.9.1.custom.css" type="text/css" charset="utf-8">
<style type="text/css">
#button1 {
  position: relative;
  width: 600px;
  height: 60px;
  background-position: 0px 4px;
}

#button2 {
  position: relative;
  width: 600px;
  height: 60px;
  background-position: 0px 4px;
}

#button3 {
  position: relative;
  width: 700px;
  height: 60px;
  background-position: 0px 4px;
}

</style>
</head>
<body>

<nav class="navbar navbar-inverse" style="margin-bottom:0">
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
        <li class="active"><a href="/GitlabMonitor/">Home <span class="sr-only">(current)</span></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Student <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/GitlabMonitor/student/summary">Student Summary</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/GitlabMonitor/student/commit">Student Detail</a></li>
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
<div class="jumbotron">
<div class="container">
<h1>Gitlab Monitor<small>&nbsp;&nbsp;let us help you</small></h1>
</div>
</div>
<div class="container">
<div id="demo">
  <div id="button1">
   <h2>Cannot wait to use Gitlab Monitor! &nbsp;&nbsp;&nbsp;<a href="/GitlabMonitor/login" class="btn btn-primary" role="button">Login</a></h2>
  </div> 
  
  <div id="button2">
   <h2>I want to have a Gitlab account! &nbsp;&nbsp;&nbsp;<a href="/GitlabMonitor/register" class="btn btn-primary" role="button">Rigister</a></h2>
  </div>
  
  <div id="button3">
   <h2>I want to donate for this amazing website! &nbsp;&nbsp;&nbsp;<a href="/GitlabMonitor/login" class="btn btn-primary" role="button">Donate</a></h2>
  </div>
</div>
<!-- <div class="row"> -->

<!--   <div class="col-sm-6 col-md-4"> -->
<!--     <div class="thumbnail"> -->
<!--       <img src="/GitlabMonitor/static/img/a.png" alt="..."> -->
<!--       <div class="caption"> -->
<!--         <h3>Shadow Hunter</h3> -->
<!--         <p>Be happy, don't worry.</p> -->
<!--         <p><a href="/GitlabMonitor/login" class="btn btn-primary" role="button">Login</a> <label class="btn btn-default" role="button">I wanna in</label></p> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
  
<!--   <div class="col-sm-6 col-md-4"> -->
<!--     <div class="thumbnail"> -->
<!--       <img src="/GitlabMonitor/static/img/b.png" alt="not found"> -->
<!--       <div class="caption"> -->
<!--         <h3>SOR</h3> -->
<!--         <p>Help me, help you.</p> -->
<!--         <p><a href="/GitlabMonitor/register" class="btn btn-primary" role="button">Rigister</a> <label class="btn btn-default" role="button">I wanna try</label></p> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
  
<!--   <div class="col-sm-6 col-md-4"> -->
<!--     <div class="thumbnail"> -->
<!--       <img src="/GitlabMonitor/static/img/c.png" alt="..."> -->
<!--       <div class="caption"> -->
<!--         <h3>Spell Breaker</h3> -->
<!--         <p>Your magic is mine.</p> -->
<!--         <p><a href="/GitlabMonitor/try" class="btn btn-primary" role="button">Donate</a> <a href="#" class="btn btn-default" role="button">I wanna pee</a></p> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
  
<!-- </div> -->
	<hr />
	<footer>
        <p>&copy; ise 2016</p>
    </footer>
</div>

</body>
<script src="/GitlabMonitor/static/js/library/jquery-1.11.3.js"></script>
<script src="/GitlabMonitor/static/js/library/bootstrap.min.js"></script>
<script src="/GitlabMonitor/static/js/TweenLite.min.js"></script>
<script type="text/javascript" src="/GitlabMonitor/static/js/CSSPlugin.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
	  var button1 = document.getElementById("button1");
	  TweenLite.to(button1, 2, {left:"350px"});
	  
	  var button2 = document.getElementById("button2");
	  TweenLite.to(button2, 2, {left:"150px"});
	  
	  var button3 = document.getElementById("button3");
	  TweenLite.to(button3, 2, {left:"450px"});
	}
</script>
</html>
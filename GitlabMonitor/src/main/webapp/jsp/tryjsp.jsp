<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
/*弹出层的STYLE*/
html,body {height:100%; margin:0px; font-size:12px;}

.mydiv {
border: 1px solid #f90;
text-align: center;
line-height: 40px;
font-size: 12px;
font-weight: bold;
z-index:99;
width: 800px;
height: 600px;
left:50%;/*FF IE7*/
top: 50%;/*FF IE7*/

margin-left:-400px!important;/*FF IE7 该值为本身宽的一半 */
margin-top:-300px!important;/*FF IE7 该值为本身高的一半*/

margin-top:0px;

position:fixed!important;/*FF IE7*/
position:absolute;/*IE6*/

_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/

}


.bg {
width: 100%;
height: 100%;
left:0;
top:0;/*FF IE7*/
filter:alpha(opacity=50);/*IE*/
opacity:0.5;/*FF*/
z-index:1;

position:fixed!important;/*FF IE7*/
position:absolute;/*IE6*/

_top:       expression(eval(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/

}
/*The END*/

</style>
<script type="text/javascript">
function showDiv(){
document.getElementById('popDiv').style.display='block';
document.getElementById('bg').style.display='block';
}

function closeDiv(){
document.getElementById('popDiv').style.display='none';
document.getElementById('bg').style.display='none';
}

</script>
</head>

<body>

<div id="popDiv" class="mydiv" style="display:none;">恭喜你！<br/>你的成绩为:60分<br/>
<a href="javascript:closeDiv()">关闭窗口</a></div>
<div id="bg" class="bg" style="display:none;"></div>

 

<div style="padding-top: 20px;">
<input type="Submit" name="" value="显示层" onclick="javascript:showDiv()" />
</div>
</body>
</html>
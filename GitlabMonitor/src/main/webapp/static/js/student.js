var student = $("#student").val();
var startDay = $("#startDay").val();
var endDay = $("#endDay").val();

$(document).ready(studentCommit(student,startDay,endDay));

function studentComment(student,token,sen){
	var url = "/GitlabMonitor/student/comment/submit"
		$.ajax(url, {
			type : 'POST',
			data : {
				"student" : student,
				"token" : token,
				"sen" : sen,
			},
			success : function(data, textStatus) {
				if(data.status == 0){
					alert(data.reason);
				}else{
						var url1 = "/GitlabMonitor/student/comment"
						$.ajax(url1, {
							type : 'POST',
							data : {
								"student" : student,
							},
							success : function(data, textStatus) {
								var value = data;
								var result = "";
								for(var i in value){
									result=result+"<p>"+value[i].words+"--"+value[i].time+"</p>";
								}
								$('#words').html(
									result
								)
							}
						});
				}
			}
		});

}
function studentCommit(student,startDay,endDay){
	//repositories per user
	var url1 = "/GitlabMonitor/student/comment"
		$.ajax(url1, {
			type : 'POST',
			data : {
				"student" : student,
			},
			success : function(data, textStatus) {
				var value = data;
				var result = "";
				for(var i in value){
					result=result+"<p>"+value[i].words+"--"+value[i].time+"</p>";
				}
				$('#words').html(
					result
				)
			}
		});
	var url = "/GitlabMonitor/student/commit/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"student" : student,
				"dayStart": startDay,
				"dayEnd": endDay,
			},
			success : function(data, textStatus) {
				$('#user').highcharts({
			        title: {
			            text: 'User Commit',
			            x: -20 //center
			        },
			        xAxis: {
			            categories: data.day
			        },
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: ''
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'commit_count',
			            data: data.commit_count
			        },{
			            name: 'add_line',
			            data: data.add_line
			        },{
			            name: 'delete_line',
			            data: data.delete_line
			        },{
			            name: 'java_file',
			            data: data.java_file
			        },]
			    });
				if(data.dayStart){
					$("#dayStart").val(data.dayStart);
					$("#dayEnd").val(data.dayEnd);
				}
				
				
			}});	
}

function studentCommit2(student,startDay,endDay,timeRange){
	//repositories per user
	var url1 = "/GitlabMonitor/student/comment"
		$.ajax(url1, {
			type : 'POST',
			data : {
				"student" : student,
			},
			success : function(data, textStatus) {
				var value = data;
				var result = "";
				for(var i in value){
					result=result+"<p>"+value[i].words+"--"+value[i].time+"</p>";
				}
				$('#words').html(
					result
				)
			}
		});
	var url = "/GitlabMonitor/student/commit/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"student" : student,
				"dayStart": startDay,
				"dayEnd": endDay,
				"timeRange": timeRange,
			},
			success : function(data, textStatus) {
				$('#user').highcharts({
			        title: {
			            text: 'User Commit',
			            x: -20 //center
			        },
			        xAxis: {
			            categories: data.day
			        },
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: ''
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'commit_count',
			            data: data.commit_count
			        },{
			            name: 'add_line',
			            data: data.add_line
			        },{
			            name: 'delete_line',
			            data: data.delete_line
			        },{
			            name: 'java_file',
			            data: data.java_file
			        },]
			    });
				$("#dayStart").val(data.dayStart);
				$("#dayEnd").val(data.dayEnd);
			}});	
}

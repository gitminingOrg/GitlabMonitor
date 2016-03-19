var projectId = $("#projectId").val();
var startDay = $("#startDay").val();
var endDay = $("#endDay").val();

$(document).ready(projectCommit(projectId,startDay,endDay));

function showDailyChart(){
	$("#memberChart").hide();
	$("#infoChart").hide();
	$("#dailyChart").show();
	$("#dailyChart").highcharts().reflow();
}

function showMemberChart(){
	$("#dailyChart").hide();
	$("#infoChart").hide();
	$("#memberChart").show();
	$("#memberChart").highcharts().reflow();
}

function showInfoChart(){
	$("#dailyChart").hide();
	$("#memberChart").hide();
	$("#infoChart").show();
	
}
function projectComment(team,token,sen){
	var url = "/GitlabMonitor/project/comment/submit"
		$.ajax(url, {
			type : 'POST',
			data : {
				"team" : team,
				"token" : token,
				"sen" : sen,
			},
			success : function(data, textStatus) {
				if(data.status == 0){
					alert(data.reason);
				}else{
						var url1 = "/GitlabMonitor/project/comment"
						$.ajax(url1, {
							type : 'POST',
							data : {
								"team" : team,
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
function projectCommit(projectId,startDay,endDay){
//	var url1 = "/GitlabMonitor/project/comment"
//		$.ajax(url1, {
//			type : 'POST',
//			data : {
//				"team" : team,
//			},
//			success : function(data, textStatus) {
//				var value = data;
//				var result = "";
//				for(var i in value){
//					result=result+"<p>"+value[i].words+"--"+value[i].time+"</p>";
//				}
//				$('#words').html(
//					result
//				)
//			}
//		});
	//repositories per user
	var url = "/GitlabMonitor/project/commit/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"projectId" : projectId,
				"dayStart": startDay,
				"dayEnd": endDay,
			},
			success : function(data, textStatus) {
				$('#dailyChart').highcharts({
			        title: {
			            text: 'Project Commit',
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
			}});	
	
	var url = "/GitlabMonitor/project/teammember"
		$.ajax(url, {
			type : 'POST',
			data : {
				"projectId" : projectId,
				"dayStart": startDay,
				"dayEnd": endDay,
			},
			success : function(data, textStatus) {
				$('#memberChart').highcharts({
					chart: {
			            type: 'column'
			        },
			        title: {
			            text: 'Team Member'
			        },
			        subtitle: {
			            text: 'gitlab.com'
			        },
			        xAxis: {
			            categories: data.statistics
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: 'Count'
			            }
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            column: {
			                pointPadding: 0.2,
			                borderWidth: 0
			            }
			        },
			        series: [{
			            name: data.stu0,
			            data: data.data0

			        }, {
			            name: data.stu1,
			            data: data.data1

			        }, {
			            name: data.stu2,
			            data: data.data2

			        }, {
			            name: data.stu3,
			            data: data.data3
			        }]
			    });}
			});
}

function projectCommit(projectId,startDay,endDay,timeRange){
//	var url1 = "/GitlabMonitor/project/comment"
//		$.ajax(url1, {
//			type : 'POST',
//			data : {
//				"projectId" : projectId,
//			},
//			success : function(data, textStatus) {
//				var value = data;
//				var result = "";
//				for(var i in value){
//					result=result+"<p>"+value[i].words+"--"+value[i].time+"</p>";
//				}
//				$('#words').html(
//					result
//				)
//			}
//		});
	//repositories per user
	var url = "/GitlabMonitor/project/commit/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"projectId" : projectId,
				"dayStart": startDay,
				"dayEnd": endDay,
				"timeRange": timeRange,
			},
			success : function(data, textStatus) {
				$('#dailyChart').highcharts({
			        title: {
			            text: 'Project Commit',
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
			}});	
	
	var url = "/GitlabMonitor/project/teammember"
		$.ajax(url, {
			type : 'POST',
			data : {
				"projectId" : projectId,
				"dayStart": startDay,
				"dayEnd": endDay,
				"timeRange": timeRange,
			},
			success : function(data, textStatus) {
				$('#memberChart').highcharts({
					chart: {
			            type: 'column'
			        },
			        title: {
			            text: 'Team Member'
			        },
			        subtitle: {
			            text: 'gitlab.com'
			        },
			        xAxis: {
			            categories: data.statistics
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: 'Count'
			            }
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            column: {
			                pointPadding: 0.2,
			                borderWidth: 0
			            }
			        },
			        series: [{
			            name: data.stu0,
			            data: data.data0

			        }, {
			            name: data.stu1,
			            data: data.data1

			        }, {
			            name: data.stu2,
			            data: data.data2

			        }, {
			            name: data.stu3,
			            data: data.data3
			        }]
			    });
				$("#dayStart").val(data.dayStart);
				$("#dayEnd").val(data.dayEnd);
			}
			});
}

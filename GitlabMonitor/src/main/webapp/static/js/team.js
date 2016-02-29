var team = $("#team").text();
var startDay = $("#startDay").text();
var endDay = $("#endDay").text();

$(document).ready(function() {
	projectCommit(team,startDay,endDay);
});

function projectCommit(student,startDay,endDay){
	//repositories per user
	var url = "/GitlabMonitor/project/commit/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"team" : team,
				"dayStart": startDay,
				"dayEnd": endDay,
			},
			success : function(data, textStatus) {
				$('#user').highcharts({
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
}

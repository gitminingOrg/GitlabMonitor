var student = $("#student").text();
var startDay = $("#startDay").text();
var endDay = $("#endDay").text();

function studentEvent(student,startDay,endDay){
	//repositories per user
	var url = "/GitlabMonitor/student/event/range"
		$.ajax(url, {
			type : 'POST',
			data : {
				"student" : student,
				"dayStart": startDay,
				"dayEnd": endDay,
			},
			success : function(data, textStatus) {
				$('#chart').highcharts({
			        title: {
			            text: 'User Event',
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
			            name: 'push',
			            data: data.push
			        },{
			            name: 'issue',
			            data: data.issue
			        },{
			            name: 'comment',
			            data: data.comment
			        },{
			            name: 'create',
			            data: data.create
			        },{
			            name: 'total',
			            data: data.total
			        }]
			    });
			}});	
}

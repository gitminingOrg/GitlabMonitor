$(function() {
	var chart;

	$(document).ready(function() {
		//repositories per user
		var url = "/GitlabMonitor/student/commit/range"
			$.ajax(url, {
				type : 'GET',
				success : function(data, textStatus) {
					$('#user').highcharts({
				        title: {
				            text: 'Repositories  per User',
				            x: -20 //center
				        },
				        xAxis: {
				            categories: data.range
				        },
				        yAxis: {
				            title: {
				                text: 'User'
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#808080'
				            }]
				        },
				        tooltip: {
				            valueSuffix: ' Users'
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        series: [{
				            name: 'Repository',
				            data: data.count
				        }]
				    });
				}});
	  
	});

});
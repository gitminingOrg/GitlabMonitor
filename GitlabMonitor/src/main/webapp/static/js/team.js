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
function projectCommit(team,startDay,endDay){
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

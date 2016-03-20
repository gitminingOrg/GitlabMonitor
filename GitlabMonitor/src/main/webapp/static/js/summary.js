function showIcon(field){
	$("#icon"+field).show();
}

function hideIcon(field){
	$("#icon"+field).hide();
}

function refreshTable(dayStart,dayEnd,formula,filter,commitOrder){
	var url = "/GitlabMonitor/project/summary/data";
		$.ajax(url, {
			type : 'POST',
			data : {
				"dayStart" : dayStart,
				"dayEnd" : dayEnd,
				"formula" : formula,
				"filter" : filter,
				"commitOrder" : commitOrder,
			},
			success : function(data, textStatus) {
				var commits = data.commits;
				var grid = "";
				for(var i=0;i<commits.length;i++){
					grid=grid+"<tr><th>"+(i+1)+"</th>";
					grid=grid+"<th><a href=\"/GitlabMonitor/project/commit?id="+commits[i].id+"&team="+commits[i].team+"&dayStart="+dayStart+"&dayEnd="+dayEnd+"\">"+commits[i].name+"</a></th>";
					grid=grid+"<th>"+commits[i].commit_count+"</th><th>"+commits[i].add_line+"</th><th>"+commits[i].delete_line+"</th>";
					grid=grid+"<th>"+commits[i].java_file+"</th><th>"+commits[i].total_add+"</th>";
					grid=grid+"<th>"+commits[i].total_delete+"</th><th>"+commits[i].formula+"</th></tr>";
				}
				$("#commit_body").html(grid);
			}
		});
}
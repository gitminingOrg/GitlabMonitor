function cancelDelete(){
	$("#addColumn").show();
	$("#deleteColumn").hide();
	$("#mentionwords").hide();
}

function checkColumnName(name){
	var type = /^[A-Za-z0-9\u4E00-\u9FA5\uF900-\uFA2D]*[A-Za-z0-9\u4E00-\u9FA5\uF900-\uFA2D][A-Za-z0-9\u4E00-\u9FA5\uF900-\uFA2D]*$/
	var re = new RegExp(type);
	if (name.match(re) == null) {
		$("#mentionwords").show();
		$("#addColumn").hide();
        return;
    }else{
    	$("#addColumn").submit();
    }
}

function show_remove(name,id){
	$("#warning_words").html('Are you sure to remove item named '+name+'?');
	$("#delete_item").val(id);
	$("#deleteColumn").show();
	$("#addColumn").hide();
}

function overRemove(obj){
	$(obj).removeClass().addClass("remove circle icon");
}

function outRemove(obj){
	$(obj).removeClass().addClass("remove icon");
}

function overLeft(obj){
	$(obj).removeClass().addClass("angle double left icon");
}

function outLeft(obj){
	$(obj).removeClass().addClass("angle left icon");
}

function showGrid(){
	$("#grid").show();
	$("#button").hide();
}

function showButton(){
	$("#grid").hide();
	$("#button").show();
}

function showText(item_id,project_id){
	var label = $("#ID"+item_id+"L"+project_id);
	var text = $("#ID"+item_id+"T"+project_id);
	label.hide();
	text.show();
}

function removeItem(item_id){
	var form = $("#form"+item_id);
	form.submit();
}

function showScoreTable(courseName){
	$("#statistics").hide();
	$("#showButton").show();
	$("#scoreButton").hide();
	$("#scoreTable").show();
}

function showStatistics(courseName){
	$("#statistics").show();
	$("#showButton").hide();
	$("#scoreButton").show();
	$("#scoreTable").hide();
	$("#statisticsTable").html('<p>calculating......</p>');
	var url = "/GitlabMonitor/project/score/statistics"
		$.ajax(url,{
			type : 'POST',
			data : {
				"courseName" : courseName,
			},
			success : function(data, textStatus){
				if(data.status == 0){
					var statisticsList = data.content.statisticsList;
					var table = "<h3>Statistics</h3><table class=\"table table-striped table-bordered\">";
					table+="<thead><tr><th>item</th><th>average</th>";
					table+="<th>range</th><th>variance</th><th>median</th>" +
							"<th>upper_quartile</th><th>lower_quartile</th></tr></thead><tbody>";
					for(var i=0;i<statisticsList.length;i++){
						table+="<tr><th>"+statisticsList[i].item_name+"</th><th>"+statisticsList[i].average+"</th>" +
								"<th>"+statisticsList[i].range+"</th><th>"+statisticsList[i].variance+"</th>" +
								"<th>"+statisticsList[i].median+"</th><th>"+statisticsList[i].upper_quartile+"</th><th>"+statisticsList[i].lower_quartile+"</th></tr>"
					}
					table+="</tbody></table>"
					
				}
				$("#statisticsTable").html(table);
			    $('#statisticsChart').highcharts({
			        title: {
			            text: 'Score Range',
			            x: -20 //center
			        },
			        subtitle: {
			            text: 'Source: Gitlab.com',
			            x: -20
			        },
			        xAxis: {
			            categories: ['0-1', '2', '3', '4', '5', '6','7', '8', '9', '10']
			        },
			        yAxis: {
			            title: {
			                text: 'Score'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: ' Groups'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: data.content.scoreRanges
			    });
			}
		});
	
}

function changeScore(item_id,project_id,score){
    var type = /^[0-9]*[0-9][0-9]*$/;
    var re = new RegExp(type);
    if (score.match(re) == null) {
        alert("请输入自然数!");
        return;
    }

	var label = $("#ID"+item_id+"L"+project_id);
	var text = $("#ID"+item_id+"T"+project_id);
	var url = "/GitlabMonitor/project/score/change"
		$.ajax(url, {
			type : 'POST',
			data : {
				"item_id" : item_id,
				"project_id" : project_id,
				"score" : score,
				
			},
			success : function(data, textStatus) {
				if(data.status == 'wrong'){
					alert(data);
					label.show();
					text.hide();
				}else{
					label.html(score);
					label.show();
					text.hide();
				}
			}
		});
}
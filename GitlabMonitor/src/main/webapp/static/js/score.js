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

function changeScore(item_id,project_id,score){
    var type = /^[0-9]*[1-9][0-9]*$/;
    var re = new RegExp(type);
    if (score.match(re) == null) {
        alert("请输入大于零的整数!");
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
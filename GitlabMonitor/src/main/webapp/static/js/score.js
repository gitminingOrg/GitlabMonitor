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
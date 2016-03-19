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

function changeScore(item_id,project_id,score){
	var label = $("#ID"+item_id+"L"+project_id);
	var text = $("#ID"+item_id+"T"+project_id);
	label.html(score);
	label.show();
	text.hide();
}
function show(){
	$('.ui.modal')
	  .modal('show')
	;
}

function addCourse(){
	var name = $("#courseName").val();
	var starttime = $("#dayStart").val();
	var endtime = $("#dayEnd").val();
	var teachers = $("#teachers").val();
	
	var url = "/GitlabMonitor/course/add";
	$.ajax(url, {
		type : 'POST',
		data : {
			"name" : name,
			"starttime" : starttime,
			"endtime" : endtime,
			"teachers" : teachers
		},
		success : function(data, textStatus) {
			
		}
	});
}
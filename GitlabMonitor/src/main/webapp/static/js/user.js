function checkUser(item,value){
	if(item == "repeatPassword"){
		if(value==$("#password").val()){
			$("#"+item+"Check").val("ok");
			$("#"+item+"ok").show();
			$("#"+item+"fail").hide();
		}else{
			$("#"+item+"Check").val("wrong");
			$("#"+item+"ok").hide();
			$("#"+item+"fail").show();
		}
		return
	}
	var type = "";
	if(item=="email"){
		type=/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/
	}else if(item=="name"){
		type=/^[a-zA-Z0-9]+$/
	}
	var re = new RegExp(type);
	if (value.match(re) == null) {
		$("#"+item+"Check").val("wrong");
		$("#"+item+"fail").html("<i class=\"remove icon\"></i>format invalid!");
		$("#"+item+"fail").show();
		$("#"+item+"ok").hide();
        return;
    }
	var url = "/GitlabMonitor/register/check"
		$.ajax(url,{
			type:'POST',
			data:{
				"item":item,
				"value":value,
			},
			success : function(data,textStatus){
				if(data.status == 1){
					$("#"+item+"Check").val("wrong");
					$("#"+item+"fail").html("<i class=\"remove icon\"></i>already exists!");
					$("#"+item+"fail").show();
					$("#"+item+"ok").hide();
					
				}else{
					$("#"+item+"Check").val("ok");
					$("#"+item+"ok").show();
					$("#"+item+"fail").hide();
				}
			}
		});
}

function tryRegister(){
	
	if($('#emailCheck').val()=="ok" &&  $('#nameCheck').val()=="ok" && $('#repeatPasswordCheck').val()=="ok"){
		$('#registerForm').submit();
	}else{
		return;
	}
}
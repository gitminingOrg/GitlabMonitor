function checkUser(item,value){
	var url = "/GitlabMonitor/register/check"
		$.ajax(url,{
			type:'POST',
			data:{
				"item":item,
				"value":value,
			},
			success : function(data,textStatus){
				if(data.status == 1){
					$("#"+item+"check").val("wrong");
					$("#"+item+"fail").show();
					$("#"+item+"ok").hide();
					
				}else{
					$("#"+item+"check").val("ok");
					$("#"+item+"ok").show();
					$("#"+item+"fail").hide();
				}
			}
		});
}

function tryRegister(){
	if($('emailCheck').val=="ok" &&  $('nameCheck').val=="ok"){
		$('#registerForm').submit();
	}else{
		return;
	}
}
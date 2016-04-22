function passwordReset(email,token,password){
	var url = "/GitlabMonitor/login/password/reset"
		$.ajax(url, {
			type : 'POST',
			data : {
				"email" : email,
				"token" : token,
				"password" : password,
			},
			success : function(data, textStatus) {
				if(data.status == 0){
					$("#success").show();
					$("#fail").hide();
				}else{
					$("#success").hide();
					$("#fail").show();
				}
			}
		});
}
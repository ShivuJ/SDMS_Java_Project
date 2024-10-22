$(document).ready(function(){
	
	$('#login').click(function(){
		
		var email = $('#userName').val();
		var pass = $('#password').val();
		
		var loginData = {
			email:email,
			password:pass
		}
		$.ajax({
				url: 'login',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(loginData),
				success: function(response){
					if(response.startsWith("/")){
						window.location.href = response;
					}else{
						toastr.error("Invalid Credentials");
					}
					
					
				}
	});
	
	});
});
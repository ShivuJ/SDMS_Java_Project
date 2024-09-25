$(document).ready(function(){
	
	$('#login').click(function(){
		
		var email = $('#userName').val();
		var pass = $('#password').val();
		
		var loginData = {
			email:email,
			pass:pass
		}
		$.ajax({
				url: 'login',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(loginData),
				success: function(response){
					
					window.location.href = "/home.html";
					
				}
	});
	
	});
});
$(document).ready(function(){
	console.log("Document is Ready....");
	 /*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
	            const formContainer = document.getElementById('formContainer');
	            if (formContainer.style.display === 'none' || formContainer.style.display === '') {
	                formContainer.style.display = 'block';
	            } else {
	                formContainer.style.display = 'none';
	            }
	        });
		
	/* POST reuest to add data*/	
	$('#submit').click(function(){
		var firstName = $('#firstName').val();
		var lastName = $('#lastName').val();
		var email = $('#teacherEmail').val();
		var teacherClass = $('#teachingClass').val();
		var subject = $('#subject').val();
		var dateOfJoining = $('#dateOfJoining').val();
		var employmentStatus = $('#employmentStatus').val();
		var qualification = $('#qualification').val();
		var yearOfGraduation = $('#yearOfGraduation').val();
		var phone = $('#contact').val();
		var password = $('#password').val();
		var role = $('#role').val();

		var saveData = {
			firstName : firstName,
			lastName : lastName,
			email : email,
			teacherClass : teacherClass,
			subject : subject,
			dateOfJoining : dateOfJoining,
			employmentStatus : employmentStatus,
			qualification : qualification,
			yearOfGraduation : yearOfGraduation,
			phone : phone,
			password : password,
			role : role
		}

		console.log(saveData);

		$.ajax({
			url : '/addTeacher',
			type : 'POST',
			contentType : 'application/json',
			data : JSON.stringify(saveData),
			dataType : 'JSON',
			success : function(response){
				if(response.status == 200){
					toastr.success('Data Save Successfully');
				}else {
					toastr.error('Something went wrong');
				}
			},			
			error: function(xhr, status, error) {
			           // alertify.error("An error occurred: " + error);
			        }

		})
	});
	
	/* Get reuest to view data*/	
	$(window).on('load', function(){
		console.log("Getting Table Ready")
		$.ajax({
			url: 'teacher',
			type: 'GET',
			success: function(response){
				let html = "";
				for(let i = 0; i < response.length; i++){
					
					html += `
						<tr>
							<td>${i + 1}</td>
							<td>${response[i].firstName}</td>
							<td>${response[i].lastName}</td>
							<td>${response[i].email}</td>
							<td>${response[i].role}</td>
							<td>${response[i].phone}</td>
							<td>${response[i].teacherClass}</td>
							<td>${response[i].subject}</td>
							<td>${response[i].password}</td>
							<td>
								<button type="button" id="editBtn" class="iconBtn"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
								<button type="button" id="deleteBtn" class="iconBtn"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
							</td>
						</tr>
					`
				}
				$('.userTable tbody').append(html);
				console.log(response);
			}
		})
	});
});
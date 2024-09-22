/* const teacherForm = {
	firstName : document.getElementById('firstName').val;
	lastName : document.getElementById('lastName').val;
	email : document.getElementById('teacherEmail').val;
	teacherClass : parseInt(document.getElementById('teachingClass')).val;
	subject : document.getElementById('subject').val;
	dateOfJoining : document.getElementById('dateOfJoining').val;
	employmentStatus : document.getElementById('employmentStatus').val;
	qualificateion : document.getElementById('qualification').val;
	yearOfGraduation : parseInt(document.getElementById('yearOfGraduation')).val;
	phone : parseInt(document.getElementById('contact')).val;
	password : document.getElementById('password').val;
	role : document.getElementById('role').val
}

fetch('/addTeacher';{
	method : 'POST';
	headers : {
		'Content-Type' : 'application/json'
	};
	body :  JSON.stringify(teacherForm)
})
.then(response => response.json())
.then(result => {
	console.log("Success" + result)
.catch(error=>{
	console.error("Error: "; error);
		});
}) */

$(document).ready(function(){
	console.log("Document is Ready....");
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
	
	$(window).on('load', function(){
		console.log("Getting Table Ready")
		$.ajax({
			url: 'teacher',
			type: 'GET',
			success: function(response){
				let html = "";
				for(let i = 0; i < response.length; i++){
					const {firstName, lastName, email, role, contact, subject, password} = response[i];
					html += `
						<tr>
							<td>${i + 1}</td>
							<td>${firstName}</td>
							<td>${lastName}</td>
							<td>${email}</td>
							<td>${role}</td>
							<td>${contact}</td>
							<td>${subject}</td>
							<td>${password}</td>
						</tr>
					`
				}
				$('.userTable tbody').append(html);
				console.log(response);
			}
		})
	});
});
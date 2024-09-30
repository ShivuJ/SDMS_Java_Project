$(document).ready(function(){
	console.log("Document is Ready....");
	 /*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});
			
	function toggleUserBtn(){
		const formContainer = document.getElementById('formContainer');
            if (formContainer.style.display === 'none' || formContainer.style.display === '') {
                formContainer.style.display = 'block';
            } else {
                formContainer.style.display = 'none';
            }
	}
	/* POST reuest to add data*/	
	$('#submit').click(function(){
		addTeacher();
		
	});
	
	$(".userTable").on('click', '#deleteBtn',function(){
		var id = $(this).closest('tr').data("id");
		deleteUser(id);
	});
	
	$(".userTable").on('click', '#editBtn',function(){
		toggleUserBtn();
		var id = $(this).closest('tr').data("id");
		updateUser(id);
	});
		
	function addTeacher(){
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
		var status = "Y";

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
			role : role,
			status : status
		}
		console.log(saveData);
		
		if(firstName=="" || firstName==null){
			toastr.error("Please add first name");
		}else if(lastName=="" || lastName==null){
			toastr.error("Please add last name");
		}else if(email=="" || email==null){
			toastr.error("Please add email id");
		}else if(role==null){
			toastr.error("Please select role");
		}else if(teacherClass==null){
			toastr.error("Please select class");
		}else if(phone==null || phone=="" || phone.length>10 || phone.length<10){
			toastr.error("Please add proper contact number");
		}else if(subject==null){
			toastr.error("Please select subject");
		}else if(!dateOfJoining){
			toastr.error("Please select Date of Joining");
		}else if(employmentStatus==null){
			toastr.error("Please select employment status");
		}else if(qualification==null){
			toastr.error("Please select qualification");
		}else if(yearOfGraduation==null || yearOfGraduation==""){
			toastr.error("Please add year of graduation");
		}else if(password==null || password=="" || password.length<8){
			toastr.error("Password should be of 8 characters");
		}else{
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

			});					
		}
		
	}
	
	/* Get reuest to view data*/	
	$(window).on('load', function(){
		console.log("Getting Table Ready")
		$.ajax({
			url: 'teacher',
			type: 'GET',
			success: function(response){
				let html = "";
				for(let i = 0; i < response.length; i++){
					if(response[i].status == "Y"){
						html += `
							<tr data-id = "${response[i].id}">
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
									<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
								</td>
							</tr>
						`
					}
				}
				$('.userTable tbody').append(html);
				console.log(response);
			}
		})
	});
	
	/*Delete User */
	function deleteUser(id){
			$.ajax({
				url: "/deleteUser",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(id),
				dataType : 'JSON',
				success : function(response){
					if(response==="Success"){
						toastr.success("User Deleted Successfully.");
					}else{
						toastr.error("Something went wrong.");
					}
				}
					
			});
		}
		
	function updateUser(id){
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
				var status = "Y";

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
					role : role,
					status : status
				}
				console.log(saveData);
				
				if(firstName=="" || firstName==null){
					toastr.error("Please add first name");
				}else if(lastName=="" || lastName==null){
					toastr.error("Please add last name");
				}else if(email=="" || email==null){
					toastr.error("Please add email id");
				}else if(role==null){
					toastr.error("Please select role");
				}else if(teacherClass==null){
					toastr.error("Please select class");
				}else if(phone==null || phone=="" || phone.length>10 || phone.length<10){
					toastr.error("Please add proper contact number");
				}else if(subject==null){
					toastr.error("Please select subject");
				}else if(!dateOfJoining){
					toastr.error("Please select Date of Joining");
				}else if(employmentStatus==null){
					toastr.error("Please select employment status");
				}else if(qualification==null){
					toastr.error("Please select qualification");
				}else if(yearOfGraduation==null || yearOfGraduation==""){
					toastr.error("Please add year of graduation");
				}else if(password==null || password=="" || password.length<8){
					toastr.error("Password should be of 8 characters");
				}else{
					$.ajax({
						url : '//updateUser/{id}',
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

					});					
				}
	}
});
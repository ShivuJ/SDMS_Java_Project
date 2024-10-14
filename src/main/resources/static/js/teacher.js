$(document).ready(function() {
	console.log("Document is Ready....");
	/*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');
	
	function toggleUserBtn() {
		const formContainer = document.getElementById('formContainer');
		if (formContainer.style.display === 'none' || formContainer.style.display === '') {
			formContainer.style.display = 'block';
		} else {
			formContainer.style.display = 'none';
		}
	}
	/* POST reuest to add data*/
	$('#submit').click(function() {
		addTeacher();
	});

	$(".userTable").on('click', '#deleteBtn', function() {
		var id = $(this).closest('tr').data("id");
		deleteUser(id);
	});

	$(".userTable").on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data("id");
		if (id) {
			editUser(id);
		} else {
			toastr.error("Id is null");
		}

	});

	function getUserData() {
		return {
			id: $('#userId').val(),
			firstName: $('#firstName').val(),
			lastName: $('#lastName').val(),
			email: $('#teacherEmail').val(),
			teacherClass: $('#teachingClass').val(),
			subject: $('#subject').val(),
			dateOfJoining: $('#dateOfJoining').val(),
			employmentStatus: $('#employmentStatus').val(),
			qualification: $('#qualification').val(),
			yearOfGraduation: $('#yearOfGraduation').val(),
			phone: $('#contact').val(),
			password: $('#password').val(),
			role: $('#role').val(),
			status: "Y"
		}
	}

	function isValidate(saveData) {
		if (!saveData.firstName) {
			toastr.error("Please add first name");
			return false;
		} else if (!saveData.lastName) {
			toastr.error("Please add last name");
			return false;
		} else if (!saveData.email) {
			toastr.error("Please add email id");
			return false;
		} else if (!saveData.role) {
			toastr.error("Please select role");
			return false;
		} else if (!saveData.teacherClass) {
			toastr.error("Please select class");
			return false;
		} else if (!saveData.phone || saveData.phone.length != 10) {
			toastr.error("Please add a proper contact number");
			return false;
		} else if (!saveData.subject) {
			toastr.error("Please select subject");
			return false;
		} else if (!saveData.dateOfJoining) {
			toastr.error("Please select Date of Joining");
			return false;
		} else if (!saveData.employmentStatus) {
			toastr.error("Please select employment status");
			return false;
		} else if (!saveData.qualification) {
			toastr.error("Please select qualification");
			return false;
		} else if (!saveData.yearOfGraduation) {
			toastr.error("Please add year of graduation");
			return false;
		} else if (!saveData.password || saveData.password.length < 8) {
			toastr.error("Password should be of at least 8 characters");
			return false;
		}
		return true;
	}

	function addTeacher() {
		var saveData = getUserData();
		console.log(saveData);
		if (isValidate(saveData)) {
			$.ajax({
				url: '/addTeacher',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(saveData),
				dataType: 'JSON',
				success: function(response) {
					if (response.status == 200) {
						toastr.success("Data Save Successfully");
						setTimeout(function() {
							location.reload();
						}, 2000);
					} else {
						toastr.error("Something went wrong");
					}
				},
				error: function(xhr, status, error) {
					// alertify.error("An error occurred: " + error);
				}

			});
		}

	}

	/* Get reuest to view data*/
	$(window).on('load', function() {
		console.log("Getting Table Ready")
		$.ajax({
			url: 'teacher',
			type: 'GET',
			success: function(response) {
				let html = "";
				for (let i = 0; i < response.length; i++) {
					if (response[i].status == "Y") {
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
	function deleteUser(id) {
		$.ajax({
			url: "/deleteUser",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(id),
			dataType: 'JSON',
			success: function(response) {
				if (response === "Success") {
					toastr.success("User Deleted Successfully.");
				} else {
					toastr.error("Something went wrong.");
				}
			}

		});
	}

	function editUser(id) {
		$.ajax({
			url: '/editUser/' + id,
			type: 'GET',
			success: function(response) {
				console.log(response);
				$('#userId').val(response.id);
				$('#firstName').val(response.firstName);
				$('#lastName').val(response.lastName);
				$('#teacherEmail').val(response.email);
				$('#teachingClass').val(response.teacherClass);
				$('#subject').val(response.subject);
				$('#dateOfJoining').val(response.dateOfJoining);
				$('#employmentStatus').val(response.employmentStatus);
				$('#qualification').val(response.qualification);
				$('#yearOfGraduation').val(response.yearOfGraduation);
				$('#contact').val(response.phone);
				$('#password').val(response.password);
				$('#role').val(response.role);
			}
		});
	}

	/*function updateUser(id){
		var saveData = getUserData();
				console.log(saveData);
				if(isValidate(saveData)){
					$.ajax({
						url : '/updateUser/'+ id,
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
	}*/
});
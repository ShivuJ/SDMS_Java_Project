$(document).ready(function() {
	console.log("Document is Ready....");
	/*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');
	const classMap = {};
	const subMap = {};
	getClasses();
	getSubject();

	let classMapReady = false;
	let subMapReady = false;

	function checkAndLoadTeacher() {
		if (classMapReady && subMapReady) {
			loadTeacher();
		}
	}

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

	$("#userTable").on('click', '#deleteBtn', function() {
		var id = $(this).closest('tr').data("id");
		deleteUser(id);
	});

	$("#userTable").on('click', '#editBtn', function() {
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
				success: function(response) {
					if (response == "Success") {
						toastr.success("Data Save Successfully");
						setTimeout(function() {
							location.reload();
						}, 2000);
					} else {
						toastr.error("Something went wrong");
					}
				},
				error: function(xhr, status, error) {
					toastr.error("An error occurred: " + error + status + xhr);
					console.log("An error occurred: " + error + status + xhr);
				}

			});
		}

	}

	/* Get reuest to view data*/

	/*Delete User */
	function deleteUser(id) {
		$.ajax({
			url: "/deleteUser",
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(id),
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

	function getClasses() {
		$.ajax({
			url: '/getClasses',
			type: 'GET',
			success: function(response) {
				console.log(response)
				if (Array.isArray(response)) {
					const dropdown = $('#teachingClass');
					response.forEach(function(cls) {
						const option = $('<option></option>').val(cls.id).text(cls.stuClass);
						dropdown.append(option);
						classMap[cls.id] = cls.stuClass;
					});
				}
				classMapReady = true;
				checkAndLoadTeacher();
			}
		})
	}

	function getSubject() {
		$.ajax({
			url: '/readSubject',
			type: 'GET',
			success: function(response) {
				console.log(response)
				if (Array.isArray(response)) {
					const dropdown = $('#subject');
					response.forEach(function(sub) {
						const option = $('<option></option>').val(sub.id).text(sub.subject);
						dropdown.append(option);
						subMap[sub.id] = sub.subject;
					});
				}
				subMapReady = true;
				checkAndLoadTeacher();
			}
		})
	}

	function loadTeacher() {
		$.ajax({
			url: 'teacher',
			type: 'GET',
			success: function(response) {
				let html = "";
				response.forEach((teacher, i) => {
					if (response[i].status == "Y") {
						const className = classMap[teacher.teacherClass];
						const subject = subMap[teacher.subject];
						html += `
								<tr data-id = "${teacher.id}">
									<td>${i + 1}</td>
									<td>${teacher.firstName}</td>
									<td>${teacher.lastName}</td>
									<td>${teacher.email}</td>
									<td>${teacher.role}</td>
									<td>${teacher.phone}</td>
									<td>${className}</td>
									<td>${subject}</td>
									<td>${teacher.password}</td>
									<td>
										<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
										<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
									</td>
								</tr>
								`
					}
				})
				$('#userTable tbody').append(html);
				var eventFired = function(type) {
					var n = $('#userTable')[0];
					//n.innerHTML += '<div>' + type + ' event - ' + new Date().getTime() + '</div>';
					n.scrollTop = n.scrollHeight;
				}

				$('#userTable')
					.on('order.dt', function() { eventFired('Order'); })
					.on('search.dt', function() { eventFired('Search'); })
					.on('page.dt', function() { eventFired('Page'); })
					.DataTable();


				console.log(response);
			}
		})
	}


});
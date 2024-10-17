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

	$('#submit').click(function() {
		addStudent();
		setTimeout(function() {
			location.reload();
		}, 2000);
	});

	function getStudentData() {
		return {
			id: $('#studentId').val(),
			stuFirstName: $('#firstName').val(),
			stuLastName: $('#lastName').val()
		}
	}

	function isValidate(saveData) {
		if (!saveData.stuFirstName) {
			toastr.error("Please add first name");
			return false;
		} else if (!saveData.stuLastName) {
			toastr.error("Please add last name");
			return false;
		}/* else if (!saveData.email) {
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
		}*/
		return true;
	}

	function addStudent() {
		var saveData = getStudentData();
		console.log(saveData);
		if (isValidate(saveData)) {
			$.ajax({
				url: 'addStudent',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(saveData),
				dataType: 'JSON',
				success: function(response) {
					console.log(response);
					if (response.status == "Y") {
						toastr.success("User Added Successfully...");
					} else {
						toastr.error("Something Went Wrong...");
					}
				}
			});
		}
	}
})
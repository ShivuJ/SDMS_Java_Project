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
	});

	function getStudentData() {
		return {
			id: $('#studentId').val(),
			stuFirstName: $('#firstName').val(),
			stuLastName: $('#lastName').val(),
			stuContact: $('#stuContact').val(),
			stuEmail: $('#stuEmail').val(),
			stuWhatsapp: $("#whatsappRadio input[type='radio']:checked").val(),
			stuClass: $('#stuClass').val(),
			stuPass: $('#stuPassword').val()
		}
	}

	function isValidate(saveData) {
		if (!saveData.stuFirstName) {
			toastr.error("Please add first name");
			return false;
		} else if (!saveData.stuLastName) {
			toastr.error("Please add last name");
			return false;
		} else if (!saveData.stuContact || saveData.stuContact.length != 10) {
			toastr.error("Please add a proper contact number");
			return false;
		} else if (!saveData.stuEmail) {
			toastr.error("Please add email id");
			return false;
		} else if (!saveData.stuClass) {
			toastr.error("Please select class");
			return false;
		} else if (!saveData.stuPass || saveData.stuPass.length < 8) {
			toastr.error("Password should be of at least 8 characters");
			return false;
		}
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
			setTimeout(function() {
				location.reload();
			}, 2000);
		}
	}

	$(window).on("load", function() {
		$.ajax({
			url: '/studentList',
			type: 'GET',
			success: function(response) {
				let html = "";
				for (let i = 0; i < response.length; i++) {
					if (response[i].status == "Y") {
						html += `
							<tr>
								<td>${i + 1}</td>
								<td>${response[i].stuFirstName}</td>
								<td>${response[i].stuLastName}</td>
								<td>${response[i].stuEmail}</td>
								<td>${response[i].stuContact}</td>
								<td>${response[i].stuClass}</td>
								<td>${response[i].stuPass}</td>
								<td>
									<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
								</td>
							</tr>
						`
					}
				}
				$('.stuTable tbody').append(html);
				console.log(response);
			}
		});
	});

})
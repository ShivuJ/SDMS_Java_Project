$(document).ready(function() {
	console.log("Document is Ready....");
	/*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');
	const classMap = {};

	function toggleUserBtn() {
		const formContainer = document.getElementById('formContainer');
		if (formContainer.style.display === 'none' || formContainer.style.display === '') {
			formContainer.style.display = 'block';
		} else {
			formContainer.style.display = 'none';
		}
	}

	$('#submit').click(function() {
		addTemplate();
	});

	$('.stuTable').on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data('id');
		editStudent(id);
	});
	
	$('.stuTable').on('click', '#deleteBtn', function() {
		var id = $(this).closest('tr').data('id');
		deleteStudent(id);
	})

	function getTemplate() {
		return {
			id: $('#emailId').val(),
			emailType: $('#emailType').val(),
			subject: $('#subject').val(),
			template: $('#template').val()
		}
	}

	function isValidate(saveData) {
		if (!saveData.emailType) {
			toastr.error("Please add email Type");
			return false;
		} else if (!saveData.subject) {
			toastr.error("Please add subject");
			return false;
		} else if (!saveData.template) {
			toastr.error("Please add template");
			return false;
		} 
		return true;
	}

	function addTemplate() {
		var saveData = getTemplate();
		console.log(saveData);
		if (isValidate(saveData)) {
			$.ajax({
				url: '/emailTemplate',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(saveData),
				dataType: 'JSON',
				success: function(response) {
					console.log(response);
					if (response == "Success") {
						toastr.success("Template Added Successfully...");
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

	function getClasses() {
		$.ajax({
			url: '/getClasses',
			type: 'GET',
			success: function(response) {
				console.log(response)
				if (Array.isArray(response)) {
					const dropdown = $('#stuClass');
					response.forEach(function(cls) {
						const option = $('<option></option>').val(cls.id).text(cls.stuClass);
						dropdown.append(option);
						classMap[cls.id] = cls.stuClass;
					});

					loadStudent(); 	
				}

			}
		})
	}

	/*$(window).on("load", function() {

		
	});*/

	function editStudent(id) {
		$.ajax({
			url: '/editStudent/' + id,
			type: 'GET',
			success: function(response) {
				console.log("Edit Student" + response);
				$('#studentId').val(response.id);
				$('#firstName').val(response.stuFirstName);
				$('#lastName').val(response.stuLastName);
				$('#stuContact').val(response.stuContact);
				$('#stuEmail').val(response.stuEmail);
				$("#whatsappRadio input[type='radio']:checked").val(response.stuWhatsapp);
				$('#stuClass').val(response.stuClass);
				$('#stuPassword').val(response.stuPass);
			}
		})
	}



	function loadStudent() {
		$.ajax({
			url: '/studentList',
			type: 'GET',
			success: function(response) {
				let html = "";
				response.forEach((student, i) => {
					if (student.status == "Y") {
						const className = classMap[student.stuClass];
						html += `
								<tr data-id="${student.id}">
									<td>${i + 1}</td>
									<td>${student.stuFirstName}</td>
									<td>${student.stuLastName}</td>
									<td>${student.stuEmail}</td>
									<td>${student.stuContact}</td>
									<td>${className}</td>
									<td>${student.stuPass}</td>
									<td>
										<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
										<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
									</td>
								</tr>
								`
					}
				});
				$('.stuTable tbody').append(html);
				console.log(response);
			}
		});
	}
	
	function deleteStudent(id){
		$.ajax({
			url: '/deleteStudent',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(id),
			dataType: 'JSON',
			success: function(response){
				if(response=="Success"){
					toastr.success("Student Deleted Successfully...");
				}else{
					toastr.error("Something Went Wrong...");
				}
			}
		})
	}

	getClasses();
});
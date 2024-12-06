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
		addTemplate();
	});

	$('.templateTable').on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data('id');
		editEmailTemp(id);
	});

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

	$(window).on("load", function() {
		loadEmails();
	});

	function editEmailTemp(id) {
		$.ajax({
			url: '/editEmail/' + id,
			type: 'GET',
			success: function(response) {
				console.log("Edit Student" + response);
				$('#emailId').val(response.id),
				$('#emailType').val(response.emailType),
				$('#subject').val(response.subject),
				$('#template').val(response.template)
			}
		})
	}



	function loadEmails() {
		$.ajax({
			url: '/readEmails',
			type: 'GET',
			success: function(response) {
				let html = "";
				response.forEach((template, i) => {
					html += `
								<tr data-id="${template.id}">
									<td>${i + 1}</td>
									<td>${template.emailType}</td>
									<td>${template.subject}</td>
									<td><button type="button" id="viewTemplate" class="viewTemplate" title="View Template"><a href="/emailTemplateHtml.html" target="_blank">Email Template</a></button></td>
									<td>
										<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									</td>
								</tr>
								`

				});
				$('.templateTable tbody').append(html);
				console.log(response);
			}
		});
	}

});
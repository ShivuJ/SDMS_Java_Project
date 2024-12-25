$(document).ready(function() {
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');

	loadActiveSubject();

	$('#active-tab').click(function() {
		loadActiveSubject();
	});

	$('#inactive-tab').click(function() {
		loadInactiveSubject();
	});

	function toggleUserBtn() {
		const formContainer = document.getElementById('formContainer');
		if (formContainer.style.display === 'none' || formContainer.style.display === '') {
			formContainer.style.display = 'block';
		} else {
			formContainer.style.display = 'none';
		}
	}

	$('#submit').click(function() {
		addSubject();
	});

	$('.subjectTable').on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data('id');
		editSub(id);
	});

	$('.subjectTable').on('click', '#deleteBtn', function() {
		var id = $(this).closest('tr').data('id');
		deleteSub(id);
	});

	function getSubjectData() {
			return {
				id: $('#subjectId').val(),
				subject: $('#subject').val()
			}
		}

	function isValidate(savedata) {
			if (!savedata.subject) {
				toastr.error("Please add subject");
				return false;
			}

			return true;
		}

	function addSubject() {
			var savedata = getSubjectData();
			if (isValidate(savedata)) {
				$.ajax({
					url: '/addSubject',
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(savedata),
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

	function loadActiveSubject() {
			$.ajax({
				url: '/readSubject',
				type: 'GET',
				success: function(response) {
					let html = "";
					response.forEach((subject, i) => {
						if (subject.status == 'Y') {
							html += `
							<tr data-id=${subject.id}>
								<td>${i + 1}</td>
								<td>${subject.subject}</td>
								<td>
									<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
								</td>
							</tr>
						`
						}

					});

					$('#activeSubjectsBody').append(html);
				}
			});
		}

	function loadInactiveSubject() {
			$.ajax({
				url: '/readSubject',
				type: 'GET',
				success: function(response) {
					let html = "";
					response.forEach((subject, i) => {
						if (subject.status == 'N') {
							html += `
								<tr data-id=${subject.id}>
									<td>${i + 1}</td>
									<td>${subject.subject}</td>
									<td>
										<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
										<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
									</td>
								</tr>
							`
						}

					});

					$('#inactiveSubjectsBody').append(html);
				}
			});
		}

	function editSub(id) {
			$.ajax({
				url: '/editSubject/' + id,
				type: 'POST',
				success: function(response) {
					$('#subjectId').val(response.id),
						$('#subject').val(response.subject)
				}
			})
		}

	function deleteSub(id) {
			$.ajax({
				url: '/inactivateSubject/' + id,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(id),
				dataType: 'JSON',
				success: function(response) {
					if (response == "Success") {
						toastr.success("Student Deleted Successfully...");
					} else {
						toastr.error("Something Went Wrong...");
					}
				}
			})

		}
});
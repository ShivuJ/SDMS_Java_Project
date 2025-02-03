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

	$('#activeSubjectsBody').on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data('id');
		editSub(id);
	});

	$('#activeSubjectsBody').on('click', '#deleteBtn', function() {
		var id = $(this).closest('tr').data('id');
		deleteSub(id);
	});

	$('#inactiveSubjectsBody').on('click', '#activeBtn', function() {
		var id = $(this).closest('tr').data('id');
		activateSub(id);
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
		debugger;
		var savedata = getSubjectData();
		if (isValidate(savedata)) {
			$.ajax({
				url: '/addSubject',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(savedata),
				success: function(response) {
					debugger;
					console.log(response);
					if (response == "Success") {
						toastr.success("Subject Added Successfully...");
						setTimeout(function() {
							location.reload();
						}, 2000);
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
				let activeCounter = 0;
				response.forEach((subject, i) => {

					if (subject.status == 'Y') {
						activeCounter++;
						html += `
							<tr data-id=${subject.id}>
								<td>${activeCounter}</td>
								<td>${subject.subject}</td>
								<td>
									<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
								</td>
							</tr>
						`
					}

				});

				$('#activeSubjectsBody').html(html);
				var eventFired = function(type) {
					var n = $('.activeSubjectTable')[0];
					//n.innerHTML += '<div>' + type + ' event - ' + new Date().getTime() + '</div>';
					n.scrollTop = n.scrollHeight;
				}

				$('.activeSubjectTable')
					.on('order.dt', function() { eventFired('Order'); })
					.on('search.dt', function() { eventFired('Search'); })
					.on('page.dt', function() { eventFired('Page'); })
					.DataTable();

			}
		});
	}

	function loadInactiveSubject() {
		$.ajax({
			url: '/readSubject',
			type: 'GET',
			success: function(response) {
				let html = "";
				let inactiveCounter = 0;
				response.forEach((subject, i) => {

					if (subject.status == 'N') {
						inactiveCounter++;
						html += `
								<tr data-id=${subject.id}>
									<td>${inactiveCounter}</td>
									<td>${subject.subject}</td>
									<td>
										<button type="button" id="activeBtn" class="iconBtn" title="Activate"><img src="./img/check-mark.png" alt="Active" style="cursor: pointer; height:20px">
									</td>
								</tr>
							`
					}

				});

				$('#inactiveSubjectsBody').html(html);
				var eventFired = function(type) {
					var n = $('.inactiveSubjectTable')[0];
					//n.innerHTML += '<div>' + type + ' event - ' + new Date().getTime() + '</div>';
					n.scrollTop = n.scrollHeight;
				}

				$('.inactiveSubjectTable')
					.on('order.dt', function() { eventFired('Order'); })
					.on('search.dt', function() { eventFired('Search'); })
					.on('page.dt', function() { eventFired('Page'); })
					.DataTable();

			}
		});
	}

	function editSub(id) {
		$.ajax({
			url: '/editSubject/' + id,
			type: 'GET',
			success: function(response) {
				$('#subjectId').val(response.id);
				$('#subject').val(response.subject);
			}
		})
	}

	function deleteSub(id) {
		$.ajax({
			url: '/inactivateSubject/' + id,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(id),
			success: function(response) {
				if (response == "Success") {
					toastr.success("Student Inactivated Successfully...");
				} else {
					toastr.error("Something Went Wrong...");
				}
			}
		})

	}

	function activateSub(id) {
		$.ajax({
			url: '/activateSubject/' + id,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(id),
			success: function(response) {
				if (response == "Success") {
					toastr.success("Student Activated Successfully...");
				} else {
					toastr.error("Something Went Wrong...");
				}
			}
		})

	}

});
$(document).ready(function() {
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
			addSubject();
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
});
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
		addClass();
	})

	function getClassData() {
		return {
			id: $('#classId').val(),
			stuClass: $('#class').val()
		}
	}

	function isValidate(saveData) {

		if ($('#class').val() == null || $('#class').val() == "") {
			toastr.error("Please add first name");
			return false;
		}
		return true;
	}

	function addClass() {
		var saveData = getClassData();
		console.log(saveData);
		if (isValidate(saveData)) {
			$.ajax({
				url: '/addClass',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(saveData),
				dataType: 'JSON',
				success: function(response) {
					if (response == 200) {
						toastr.success("Data Save Successfully");
					} else {
						toastr.error("Something went wrong");
					}
				}
			});
		}
	}
})
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

		let html = `
		<tr >
				<td><input type="text" id="className" name="className" disabled></td>
				<td><input type="text" id="stuName" name="studentName" disabled></td>
				<td><input type="text" id="subject" name="subject" disabled></td>
				<td><input type="number" id="assessmentMarks" name="assessmentMarks"></td>
				<td><input type="number" id="examMarks" name="examMarks"></td>
				<td><input type="number" id="totalMarks" name="totalMarks" disabled></td>
		</tr>
		`
		$('#assignGradeForm tbody').append(html);
	}

	$('#submit').click(function() {
	
	});


	

	/*function getClasses() {
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

				}

			}
		})
	}*/


	/*getClasses();*/
});
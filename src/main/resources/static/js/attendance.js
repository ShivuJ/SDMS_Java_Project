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
			bindStudent();
		} else {
			formContainer.style.display = 'none';
		}

		let html = `
		<tr >
				<td><input type="text" id="stuName" name="stuName" disabled></td>
				<td><input type="text" id="rollNumber" name="rollNumber" disabled></td>
				<td><select id="attendance" name="attendance" required><option value="" selected disabled hidden>Select Attendance</option></select></td>
		</tr>
		`
		$('#attendanceForm tbody').append(html);
	}

	$('#submit').click(function() {
	
	});
	
	function getCookie(name) {
			let cookies = document.cookie.split("; ");
			for (i = 0; i < cookies.length; i++) {
				let cookie = cookies[i].split("=");
				if (cookie[0] === name) {
					return decodeURIComponent(cookie[1]);
				}
			}
		}

	function bindStudent(){
		$.ajax({
			url: '/attendance/students',
			type: 'GET',
			success: function(res){
				console.log(res);
				$("#class").val(res.className);
			}
		})
	}
	

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
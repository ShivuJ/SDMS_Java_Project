$(document).ready(function() {
	console.log("Document is Ready....");
	/*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');
	const classMap = {};
	const stuMap = {};
	let stuCount = 0;
	let stuClass = 0;
	const stuClassObject = {};
	let teacherClass = getCookie("userClass");

	function toggleUserBtn() {
		const formContainer = document.getElementById('formContainer');
		if (formContainer.style.display === 'none' || formContainer.style.display === '') {
			formContainer.style.display = 'block';
			addStuData();

		} else {
			formContainer.style.display = 'none';
		}


	}

	$('#submit').click(function() {

	});


	function getCookie(name) {
		let cookies = document.cookie.split("; ");
		console.log(cookies)
		for (i = 0; i < cookies.length; i++) {
			let cookie = cookies[i].split("=");
			if (cookie[0] === name) {
				return decodeURIComponent(cookie[1]);
			}
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

				}

			}
		});
	}

	function getStudent() {
		$.ajax({
			url: '/studentList',
			type: 'GET',
			success: function(response) {
				console.log(response)
				if (Array.isArray(response)) {
					const dropdown = $('#stuName');
					response.forEach(function(stu) {
						const option = $('<option></option>').val(stu.id).text(stu.stuClass);
						dropdown.append(option);
						stuMap[stu.id] = stu.stuClass;

					});
					
					stuCount = response.length;
					for (let i = 0; i < stuCount; i++) {
						stuClassObject[i] = response[i].stuClass;
						
					}
					console.log(stuClassObject);

				}

			}
		});
	}

	function addStuData() {
		$("#className").val(getCookie("userClass"));
		for (let i = 0; i < stuCount; i++) {
			if (stuClassObject[i] === teacherClass) {
				let html = `
					<tr >
							<td><input type="text" id="className" name="className" placeholder="${classMap[getCookie("userClass")]}" disabled></td>
							<td><input type="text" id="stuName" name="studentName" placeholder="" disabled></td>
							<td><input type="text" id="subject" name="subject" disabled></td>
							<td><input type="number" id="assessmentMarks" name="assessmentMarks"></td>
							<td><input type="number" id="examMarks" name="examMarks"></td>
							<td><input type="number" id="totalMarks" name="totalMarks" disabled></td>
					</tr>
				`
				$('#assignGradeForm tbody').append(html);
			}
		}



	}


	getClasses();
	getStudent();
});
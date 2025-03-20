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
	const stuNameObject = {};
	const stuId = {};
	const subMap = {};
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
						stuMap[stu.id] = stu.stuFirstName + " " + stu.stuLastName;

					});

					stuCount = response.length;
					for (let i = 0; i < stuCount; i++) {
						stuClassObject[i] = response[i].stuClass;
						stuId[i] = response[i].id;
						stuNameObject[i] = response[i].stuFirstName + " " + response[i].stuLastName;

					}
					console.log(stuClassObject);
					console.log(stuNameObject);

				}

			}
		});
	}

	function addStuData() {
		$("#className").val(getCookie("userClass"));
		$("#subject").val(getCookie("userSubject"));
		for (let i = 0; i < stuCount; i++) {
			if (stuClassObject[i] === teacherClass) {
				let html = `
					<tr >
							<td><input type="text" id="className" name="className" placeholder="${classMap[getCookie("userClass")]}" disabled></td>
							<td><input type="text" id="stuName${i}" name="studentName" placeholder="${stuNameObject[i]}" disabled></td>
							<td><input type="text" id="subject" name="subject" placeholder="${subMap[getCookie("userSubject")]}" disabled></td>
							<td><input type="number" id="assessmentMarks${i}" name="assessmentMarks" onchange="calMarks(${i})"></td>
							<td><input type="number" id="examMarks${i}" name="examMarks" onchange="calMarks(${i})"></td>
							<td><input type="number" id="totalMarks${i}" name="totalMarks" disabled></td>
					</tr>
				`
				$("#stuName").val(stuId[i]);
				$('#assignGradeForm tbody').append(html);
			}
		}

	}

	function getSubject() {
		$.ajax({
			url: '/readSubject',
			type: 'GET',
			success: function(response) {
				console.log(response)
				if (Array.isArray(response)) {
					const dropdown = $('#stuClass');
					response.forEach(function(sub) {
						const option = $('<option></option>').val(sub.id).text(sub.stuClass);
						dropdown.append(option);
						subMap[sub.id] = sub.subject;
					});

				}

			}
		});
	}
	
	function calMarks(index){
		let assessment = parseInt($(`#assessmentMarks${index}`).val());
		let exam = parseInt($(`#examMarks${index}`).val());
		
		let totalMarks= assessment + exam;
		$(`#totalMarks${index}`).val(totalMarks);
		
		
	}

	getClasses();
	getStudent();
	getSubject();
});
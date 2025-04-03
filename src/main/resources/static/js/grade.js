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
		assignGrade();
		$('#assignGradeForm tbody').empty();
		toggleUserBtn();

	});

	$('#gradeTable').on('click', '#editBtn', function() {
		$('#submit').text("Update");
		toggleUserBtn();
		var id = $(this).closest('tr').data("id");
		if (id) {
			editMarks(id);
		} else {
			toastr.error("Id is null");
		}
	})


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

	function calMarks(index) {
		let assessment = parseInt($(`#assessmentMarks${index}`).val()) || 0;
		let exam = parseInt($(`#examMarks${index}`).val()) || 0;

		let totalMarks = assessment + exam;
		$(`#totalMarks${index}`).val(totalMarks);


	}

	function addStuData() {
		$("#className").val(getCookie("userClass"));
		$("#subject").val(getCookie("userSubject"));
		for (let i = 0; i < stuCount; i++) {
			if (stuClassObject[i] === teacherClass) {
				let html = `
					<tr data-id = "">
							<td>${i}</td>
							<td><input type="text" id="className" name="className" placeholder="${classMap[getCookie("userClass")]}" disabled></td>
							<td><input type="text" id="stuName${i}" name="studentName" placeholder="${stuNameObject[i]}" data-id="${stuId[i]}" disabled></td>
							<td><input type="text" id="subject" name="subject" placeholder="${subMap[getCookie("userSubject")]}" disabled></td>
							<td><input type="number" id="assessmentMarks${i}" name="assessmentMarks"></td>
							<td><input type="number" id="examMarks${i}" name="examMarks"></td>
							<td><input type="number" id="totalMarks${i}" name="totalMarks" disabled></td>
					</tr>
				`
				$("#stuName").val(stuId[i]);
				$('#assignGradeForm tbody').append(html);
			}
		}

		$(`[id^=assessmentMarks], [id^=examMarks]`).on('input', function() {
			let index = $(this).attr('id').replace(/\D/g, ''); // Extract numeric index
			calMarks(index);
		});

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

	/*function getGradeData() {
		return {
			
			id: $('#gradeId').val(),
			stuTeachClass: $('#className').val(),
			stuName: $('#class').val(),
			
		}
	}*/

	function submitGrade() {
		let studentData = [];

		document.querySelectorAll('#assignGradeForm tbody tr').forEach((row, index) => {
			let idInput = row.querySelector(`[id^=gradeId]`);
			let id = idInput ? idInput.value : "";

			let className = getCookie("userClass");

			let stuNameInput = row.querySelector(`[id^=stuName]`);
			let stuName = stuNameInput ? stuNameInput.getAttribute("data-id") : "";
			/*let stuName = $(`#stuName${index}`).val();*/

			let subject = getCookie("userSubject");

			let assessmentMarksInput = row.querySelector(`[id^=assessmentMarks]`);
			let assessmentMarks = assessmentMarksInput ? assessmentMarksInput.value : 0;

			let examMarksInput = row.querySelector(`[id^=examMarks]`);
			let examMarks = examMarksInput ? examMarksInput.value : 0;

			let totalMarksInput = row.querySelector(`[id^=totalMarks]`);
			let totalMarks = totalMarksInput ? totalMarksInput.value : 0;

			let studentObj = {
				id: id,
				stuTeachClass: className,
				stuName: stuName,
				subject: subject,
				assessmentMarks: parseInt(assessmentMarks),
				examMarks: parseInt(examMarks),
				totalMarks: parseInt(totalMarks)
			};

			studentData.push(studentObj);

		});

		console.log(studentData);
		return studentData;
	}

	function isValidate() {
		let isValid = true;

		$("#assignGradeForm tbody tr").each(function(index, row) {
			let assessmentMarks = $(row).find(`[id^=assessmentMarks]`).val();
			let examMarks = $(row).find(`[id^=examMarks]`).val();

			if (!assessmentMarks || assessmentMarks.trim() === "") {
				toastr.error(`Please add Assessment Marks for Student ${index + 1}`);
				isValid = false;
			}

			if (!examMarks || examMarks.trim() === "") {
				toastr.error(`Please add Exam Marks for Student ${index + 1}`);
				isValid = false;
			}
		});

		return isValid;
	}

	function assignGrade() {
		let studentData = submitGrade();
		if (isValidate()) {
			$.ajax({
				url: '/assignGrade',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(studentData),
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


	function loadGrades() {

		$.ajax({
			url: '/getStudentMarks',
			type: 'GET',
			success: function(response) {
				console.log(response);
				let html = "";

				response.forEach((grade, i) => {
					const className = classMap[grade.stuTeachClass];
					const stuName = stuMap[grade.stuName];
					const subName = subMap[grade.subject]
					html = `
					<tr data-id="${grade.id}">
						<td>${i + 1}</td>
						<td>${className}</td>
						<td>${stuName}</td>
						<td>${subName}</td>
						<td><span class="marks">${grade.assessmentMarks}</span> <input type="number" class="edit-input" value=`${grade.assessmentMarks}` style="display:none"></td>
						<td>${grade.examMarks}</td>
						<td>${grade.totalMarks}</td>
						<td>
							<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
						</td>
					</tr>
					
				`
					$('#gradeTable tbody').append(html);
				});


			}
		});
	}

	function editMarks(id) {
		$.ajax({
			url: '/editMarks/' + id,
			type: 'GET',
			success: function(res) {
				console.log(res);
				$('a')
			}
		})
	}

	loadGrades();


	getClasses();
	getStudent();
	getSubject();
});
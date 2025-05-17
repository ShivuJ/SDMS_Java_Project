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

		/*let html = `
		<tr >
				<td><input type="text" id="stuName" name="stuName" disabled></td>
				<td><input type="text" id="rollNumber" name="rollNumber" disabled></td>
				<td><select id="attendance" name="attendance" required><option value="" selected disabled hidden>Select Attendance</option></select></td>
		</tr>
		`
		$('#attendanceForm tbody').append(html);*/
	}

	$('#submit').click(function() {
		submitAttendance()
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

		function bindStudent() {
		    $.ajax({
		        url: '/attendance/students',
		        type: 'GET',
		        success: function(res) {
		            console.log(res);

		            // Clear previous options
		            $("#className").empty().append('<option value="" disabled selected hidden>Select Class</option>');

		            // Get unique classes from res
		            let uniqueClasses = new Map(); // Map to avoid duplicates: key = classId, value = className

		            res.forEach(student => {
		                uniqueClasses.set(student.stuClass, student.className); // assuming you have both in response
		            });

		            // Populate class dropdown
		            uniqueClasses.forEach((className, classId) => {
		                $("#className").append(`<option value="${classId}">${className}</option>`);
		            });

		            // Clear previous students
		            $('#attendanceForm tbody').empty();

		            // Append student rows
		            res.forEach(student => {
		                let fullName = student.stuFirstName + " " + student.stuLastName;
		                let html = `
		                    <tr>
		                        <td>${fullName}</td>
		                        <td data-id="${student.id}">${student.rollNumber}</td>
		                        <td>
		                            <select id="attendance_${student.id}" name="attendance" required>
		                                <option value="" selected disabled hidden>Select Attendance</option>
		                                <option value="Present">Present</option>
		                                <option value="Absent">Absent</option>
		                            </select>
		                        </td>
		                    </tr>
		                `;
		                $('#attendanceForm tbody').append(html);
		            });
		        }
		    });
		}

	
	function submitAttendance() {
		let attendanceData = [];

		let className = document.getElementById("className").value;
		let attendanceDate = document.getElementById("attendanceDate").value;

		document.querySelectorAll('#attendanceForm tbody tr').forEach((row) => {
			let studentId = row.getAttribute("data-id");

			// Get the select dropdown using studentId in its ID
			let attendanceStatusSelect = document.getElementById(`attendance_${studentId}`);
			let attendanceStatus = attendanceStatusSelect ? attendanceStatusSelect.value : "Absent";

			// Create the object for each student
			let attendanceObj = {
				studentId: studentId,
				classId: className,
				date: attendanceDate,
				attendance: attendanceStatus
			};

			attendanceData.push(attendanceObj);
		});

		console.log("Final Attendance Data:", attendanceData);

		// You can send it via AJAX here if needed
		$.ajax({
			url: "/generateAttendance",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(attendanceData),
			success: function (response) {
				alert("Attendance saved successfully!");
				document.getElementById("attendanceForm").reset();
				$('#attendanceForm tbody').empty();
			},
			error: function (error) {
				console.error("Error saving attendance:", error);
				alert("Failed to save attendance.");
			}
		});
	}
});
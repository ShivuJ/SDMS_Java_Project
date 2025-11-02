$(document).ready(function() {
	console.log("Document is Ready....");
	/*Toggle model for form */
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
		toggleUserBtn();
	});

	$('#navbar').load('nav.html');
	const classMap = {};
	let editingAttendanceId = null; // Track if we're editing

	function toggleUserBtn() {
		const formContainer = document.getElementById('formContainer');
		if (formContainer.style.display === 'none' || formContainer.style.display === '') {
			formContainer.style.display = 'block';
			bindStudent();
			editingAttendanceId = null; // Reset edit mode
			document.getElementById('submit').innerText = 'Submit';
		} else {
			formContainer.style.display = 'none';
		}
	}

	$('#submit').click(function() {
		if (editingAttendanceId) {
			submitEditAttendance();
		} else {
			submitAttendance();
		}
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

				window.allStudents = res;

				$("#className").on("change", function() {
					const selectedClassId = $(this).val();
					const filteredStudents = window.allStudents.filter(student => student.stuClass == selectedClassId);

					// Clear student table
					$('#attendanceForm tbody').empty();

					// Populate student table
					filteredStudents.forEach(student => {
						let fullName = student.stuFirstName + " " + student.stuLastName;
						let html = `
					            <tr data-id="${student.id}">
					                <td>${fullName}</td>
					                <td>${student.rollNumber}</td>
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
				});

			}
		});
	}

	function submitAttendance() {
		let attendanceData = [];

		let className = document.getElementById("className").value;
		let attendanceDate = document.getElementById("attendanceDate").value;

		// Validate inputs
		if (!className || !attendanceDate) {
			alert("Please select class and date");
			return;
		}

		document.querySelectorAll('#attendanceForm tbody tr').forEach((row) => {
			let studentId = row.getAttribute("data-id");

			// Get the select dropdown using studentId in its ID
			let attendanceStatusSelect = document.getElementById(`attendance_${studentId}`);
			let attendanceStatus = attendanceStatusSelect ? attendanceStatusSelect.value : "Absent";

			// Validate attendance status is selected
			if (!attendanceStatus) {
				alert(`Please select attendance status for student ID: ${studentId}`);
				return;
			}

			// Create the object for each student
			let attendanceObj = {
				studentId: studentId,
				classId: className,
				date: attendanceDate,
				attendance: attendanceStatus
			};

			attendanceData.push(attendanceObj);
		});

		if (attendanceData.length === 0) {
			alert("No attendance data to submit");
			return;
		}

		console.log("Final Attendance Data:", attendanceData);

		// You can send it via AJAX here if needed
		$.ajax({
			url: "/generateAttendance",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(attendanceData),
			success: function(response) {
				alert("Attendance saved successfully!");
				document.getElementById("attendanceForm").reset();
				$('#attendanceForm tbody').empty();
				toggleUserBtn();
				loadAttendanceTable(); // Refresh the table
			},
			error: function(error) {
				console.error("Error saving attendance:", error);
				alert("Failed to save attendance.");
			}
		});
	}

	function submitEditAttendance() {
		let className = document.getElementById("className").value;
		let attendanceDate = document.getElementById("attendanceDate").value;

		// Validate inputs
		if (!className || !attendanceDate) {
			alert("Please select class and date");
			return;
		}

		let attendanceStatus = null;
		document.querySelectorAll('#attendanceForm tbody tr').forEach((row) => {
			let attendanceStatusSelect = row.querySelector('select');
			if (attendanceStatusSelect) {
				attendanceStatus = attendanceStatusSelect.value;
			}
		});

		if (!attendanceStatus) {
			alert("Please select attendance status");
			return;
		}

		let updateData = {
			classId: className,
			date: attendanceDate,
			attendance: attendanceStatus
		};

		console.log("Updating attendance ID:", editingAttendanceId, "with data:", updateData);

		$.ajax({
			url: `/attendance/${editingAttendanceId}`,
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify(updateData),
			success: function(response) {
				alert("Attendance updated successfully!");
				const form = document.querySelector('form');
				if (form) {
					form.reset();
				}
				$('#attendanceForm tbody').empty();
				$('#className').val('');
				$('#attendanceDate').val('');
				editingAttendanceId = null;
				document.getElementById('submit').innerText = 'Submit';
				toggleUserBtn();
				loadAttendanceTable(); // Refresh the table
			},
			error: function(error) {
				console.error("Error updating attendance:", error);
				alert("Failed to update attendance.");
			}
		});
	}

	function loadAttendanceTable() {
		$.ajax({
			url: '/readStuAttend',
			type: 'GET',
			success: function(response) {
				console.log("Full Response:", response);
				let html = "";
				
				if (!response || response.length === 0) {
					console.log("No attendance records found");
					$("#classTable tbody").empty().append('<tr><td colspan="7" style="text-align:center;">No records found</td></tr>');
					return;
				}
				
				for (let i = 0; i < response.length; i++) {
					console.log("Processing record:", response[i]);
					
					// Extract data safely from the updated response structure
					const record = response[i];
					
					// Student data
					const studentName = (record.students && record.students.stuFirstName) 
						? `${record.students.stuFirstName} ${record.students.stuLastName || ''}`.trim() 
						: 'N/A';
					const rollNumber = (record.students && record.students.rollNumber) 
						? record.students.rollNumber 
						: 'N/A';
					
					// Class data
					const className = (record.classes && record.classes.stuClass) 
						? record.classes.stuClass 
						: 'N/A';
					
					// Attendance data
					const date = record.date || 'N/A';
					const attendance = record.attendance || 'N/A';
					
					console.log(`Student: ${studentName}, Roll: ${rollNumber}, Class: ${className}, Date: ${date}, Attendance: ${attendance}`);
					
					html += `
						<tr data-id="${record.id}">
							<td>${className}</td>
							<td>${date}</td>
							<td>${studentName}</td>
							<td>${rollNumber}</td>
							<td>${attendance}</td>
							<td>
								<button type="button" class="iconBtn editBtn" title="Edit" data-id="${record.id}"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px"></button>
							</td>
						</tr>
					`;
				}
				
				$("#recordTable tbody").append(html);

				// Reinitialize DataTable if it exists
				if ($.fn.DataTable.isDataTable('#classTable')) {
					$('#classTable').DataTable().destroy();
				}

				$('#recordTable').DataTable();

				// Attach event handlers to edit and delete buttons
				attachTableEventHandlers();

				console.log("Table loaded successfully with", response.length, "records");
			},
			error: function(error) {
				console.error("Error loading attendance table:", error);
				$("#classTable tbody").empty().append('<tr><td colspan="7" style="text-align:center; color:red;">Error loading records</td></tr>');
			}
		});
	}

	function attachTableEventHandlers() {
		$(document).off('click', '.editBtn').on('click', '.editBtn', function() {
			const attendanceId = $(this).data('id');
			editAttendanceRecord(attendanceId);
		});

		$(document).off('click', '.deleteBtn').on('click', '.deleteBtn', function() {
			const attendanceId = $(this).data('id');
			deleteAttendanceRecord(attendanceId);
		});
	}

	function editAttendanceRecord(attendanceId) {
		console.log("Editing attendance record with ID:", attendanceId);

		// Fetch the attendance record details
		$.ajax({
			url: `/attendance/${attendanceId}`,
			type: 'GET',
			success: function(attendance) {
				console.log("Fetched attendance record:", attendance);

				editingAttendanceId = attendanceId;
				document.getElementById('submit').innerText = 'Update';

				// Populate form fields
				document.getElementById("className").value = attendance.classes.id;
				document.getElementById("attendanceDate").value = attendance.date;

				// Show form
				const formContainer = document.getElementById('formContainer');
				formContainer.style.display = 'block';

				// Populate student table with single record
				$('#attendanceForm tbody').empty();
				let fullName = attendance.students.stuFirstName + " " + attendance.students.stuLastName;
				let html = `
					<tr data-id="${attendance.students.id}">
						<td>${fullName}</td>
						<td>${attendance.students.rollNumber}</td>
						<td>
							<select id="attendance_${attendance.students.id}" name="attendance" required>
								<option value="" selected disabled hidden>Select Attendance</option>
								<option value="Present" ${attendance.attendance === 'Present' ? 'selected' : ''}>Present</option>
								<option value="Absent" ${attendance.attendance === 'Absent' ? 'selected' : ''}>Absent</option>
							</select>
						</td>
					</tr>
				`;
				$('#attendanceForm tbody').append(html);

				// Scroll to form
				$('html, body').animate({
					scrollTop: $("#formContainer").offset().top - 100
				}, 800);
			},
			error: function(error) {
				console.error("Error fetching attendance record:", error);
				alert("Failed to load attendance record for editing");
			}
		});
	}

	function deleteAttendanceRecord(attendanceId) {
		if (!confirm("Are you sure you want to delete this attendance record?")) {
			return;
		}

		console.log("Deleting attendance record with ID:", attendanceId);

		$.ajax({
			url: `/attendance/${attendanceId}`,
			type: 'DELETE',
			success: function(response) {
				alert("Attendance record deleted successfully!");
				loadAttendanceTable(); // Refresh the table
			},
			error: function(error) {
				console.error("Error deleting attendance record:", error);
				alert("Failed to delete attendance record");
			}
		});
	}

	$(window).on("load", function() {
		loadAttendanceTable();
	});
});
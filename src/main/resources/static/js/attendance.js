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

	// Handle save button in modal
	$(document).on('click', '#saveAttendanceBtn', function() {
		submitEditAttendance();
	});

	function submitEditAttendance() {
		let attendanceDate = document.getElementById("editAttendanceDate").value;
		let attendanceStatus = document.getElementById("editAttendanceStatus").value;

		// Validate inputs
		if (!attendanceDate || !attendanceStatus) {
			alert("Please fill in all required fields");
			return;
		}

		let updateData = {
			date: attendanceDate,
			attendance: attendanceStatus
		};

		console.log("Updating attendance ID:", editingAttendanceId, "with data:", updateData);

		// Disable save button to prevent multiple clicks
		$('#saveAttendanceBtn').prop('disabled', true);

		$.ajax({
			url: `/attendance/${editingAttendanceId}`,
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify(updateData),
			success: function(response) {
				console.log("Update response:", response);
				alert("Attendance updated successfully!");

				// Hide modal and wait for animation
				$('#editAttendanceModal').modal('hide');

				// Reset variables
				editingAttendanceId = null;

				// Wait for modal to close, then refresh table
				setTimeout(function() {
					console.log("Modal closed, refreshing table...");
					$('#saveAttendanceBtn').prop('disabled', false);
					loadAttendanceTable();
				}, 600);
			},
			error: function(error) {
				console.error("Error updating attendance:", error);
				console.error("Error status:", error.status);
				console.error("Error response:", error.responseText);
				alert("Failed to update attendance. Please try again.");
				$('#saveAttendanceBtn').prop('disabled', false);
			}
		});
	}

	function loadAttendanceTable() {
		console.log("Loading attendance table...");

		$.ajax({
			url: '/readStuAttend',
			type: 'GET',
			success: function(response) {
				console.log("Full Response received:", response);
				let html = "";

				if (!response || response.length === 0) {
					console.log("No attendance records found");
					$("#classTable tbody").empty().append('<tr><td colspan="6" style="text-align:center;">No records found</td></tr>');
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

				console.log("Generated HTML rows:", html.length);
				
				// Reinitialize DataTable if it exists
				if ($.fn.DataTable.isDataTable('#classTable')) {
					console.log("Destroying existing DataTable");
					$('#classTable').DataTable().destroy();
				}
				
				$("#classTable tbody").empty().append(html);

				console.log("Initializing DataTable");
				$('#classTable').DataTable();

				// Attach event handlers to edit and delete buttons
				attachTableEventHandlers();

				console.log("Table loaded successfully with", response.length, "records");
			},
			error: function(error) {
				console.error("Error loading attendance table:", error);
				console.error("Error status:", error.status);
				console.error("Error response:", error.responseText);
				$("#classTable tbody").empty().append('<tr><td colspan="6" style="text-align:center; color:red;">Error loading records</td></tr>');
			}
		});
	}

	function attachTableEventHandlers() {
		$(document).off('click', '.editBtn').on('click', '.editBtn', function() {
			const attendanceId = $(this).data('id');
			editAttendanceRecord(attendanceId);
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

				// Populate modal fields
				if (attendance.students) {
					const fullName = attendance.students.stuFirstName + " " + attendance.students.stuLastName;
					document.getElementById("editStudentName").value = fullName;
					document.getElementById("editRollNumber").value = attendance.students.rollNumber || 'N/A';
				}

				if (attendance.classes) {
					document.getElementById("editClassName").value = attendance.classes.stuClass || 'N/A';
				}

				document.getElementById("editAttendanceDate").value = attendance.date || '';
				document.getElementById("editAttendanceStatus").value = attendance.attendance || '';

				// Show modal
				$('#editAttendanceModal').modal('show');
			},
			error: function(error) {
				console.error("Error fetching attendance record:", error);
				alert("Failed to load attendance record for editing");
			}
		});
	}


	$(window).on("load", function() {
		loadAttendanceTable();
	});
});
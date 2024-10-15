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
		setTimeout(function() {
			location.reload();
		}, 2000);
	})

	$('.classTable').on('click', '#editBtn', function(){
		$('#submit').text('Update');
		toggleUserBtn();
		var id = $(this).closest('tr').data("id");
		if(id){
			editClass(id);
		}else{
			toastr.error("Class Not Found...")
		}
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

	$(window).on("load", function() {
		$.ajax({
			url: '/getClasses',
			type: 'GET',
			success: function(response) {
				let html = "";
				for (let i = 0; i < response.length; i++) {
					if (response[i].status == "Y") {
						html += `
							<tr data-id = "${response[i].id}">
								<td>${i + 1}</td>
								<td>${response[i].stuClass}</td>
								<td>
									<button type="button" id="editBtn" class="iconBtn" title="Edit"><img src="./img/edit.png" alt="Edit" style="cursor: pointer; height:20px">
									<button type="button" id="deleteBtn" class="iconBtn" title="Inactivate"><img src="./img/delete.png" alt="Delete" style="cursor: pointer; height:20px">
								</td>
							</tr>
						`
					}
				}
				$(".classTable tbody").append(html);
				console.log(response);
			}

		});
	});
	
	function editClass(id){
		$.ajax({
			url: '/editClass/' + id,
			type: 'GET',
			success: function(response){
				$('#classId').val(response.id),
				$('#class').val(response.stuClass)
				console.log(response);
			}
		})
	}
})
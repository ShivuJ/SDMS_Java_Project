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
})
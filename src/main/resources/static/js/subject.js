$(document).ready(function() {
	document.getElementById('toggleFormBtn').addEventListener('click', function() {
			toggleUserBtn();
		});

		$('#navbar').load('nav.html');s

		function toggleUserBtn() {
			const formContainer = document.getElementById('formContainer');
			if (formContainer.style.display === 'none' || formContainer.style.display === '') {
				formContainer.style.display = 'block';
			} else {
				formContainer.style.display = 'none';
			}
		}
	
});
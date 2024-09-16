const teacherForm = {
	firstName : document.getElementById('firstName').value,
	lastName : document.getElementById('lastName').value,
	email : document.getElementById('teacherEmail').value,
	teacherClass : document.getElementById('teachingClass').value,
	subject : document.getElementById('subject').value,
	dateOfJoining : document.getElementById('dateOfJoining').value,
	employmentStatus : document.getElementById('employmentStatus').value,
	qualificateion : document.getElementById('qualification').value,
	yearOfGraduation : document.getElementById('yearOfGraduation').value,
	phone : document.getElementById('contact').value,
	password : document.getElementById('password').value,
	role : document.getElementById('role').value
}

fetch('/addTeacher',{
	method : 'POST',
	headers : {
		'Content-Type' : 'application/json'
	},
	body :  JSON.stringify(teacherForm)
})
.then(response => response.json())
.then(result => {
	console.log("Success" + result)
})
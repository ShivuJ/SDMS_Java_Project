package com.sdmsproject.sdms.model;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_db")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	
//	mapping teacher to Class.
//	@OneToOne
//	@JoinColumn(name = "teacher_class", referencedColumnName = "id", nullable = false)
	private Long teacherClass;
	
//	mapping teacher to Subject.
//	@OneToOne
//	@JoinColumn(name = "subject", referencedColumnName = "id", nullable = false)
	private Long subject;
	
	private String dateOfJoining;
	private String employmentStatus;
	private String qualification;
	private int yearOfGraduation;
	private String phone;
	private String password;
	private String role;
	private String status = "Y";

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getTeacherClass() {
		return teacherClass;
	}
	public void setTeacherClass(Long teacherClass) {
		this.teacherClass = teacherClass;
	}
	public Long getSubject() {
		return subject;
	}
	public void setSubject(Long subject) {
		this.subject = subject;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public int getYearOfGraduation() {
		return yearOfGraduation;
	}
	public void setYearOfGraduation(int yearOfGraduation) {
		this.yearOfGraduation = yearOfGraduation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}



}

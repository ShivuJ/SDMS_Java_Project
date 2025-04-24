package com.sdmsproject.sdms.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "student_db")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String stuFirstName;
	private String stuLastName;
	private String status = "Y";
	private String stuContact;
	private String stuEmail;
	private String stuWhatsapp;
	private String role = "Student";
	private String stuClass;
	private String stuPass;
	private String rollNumber;
	
	private String className;
	
	private String createdBy;
	private LocalDate createdOn;
	private String updatedBy;
	private LocalDate updatedOn;
	
	@OneToMany(mappedBy = "students", cascade = CascadeType.ALL)
	private List<AttendanceEntity> attendance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStuFirstName() {
		return stuFirstName;
	}
	public void setStuFirstName(String stuFirstName) {
		this.stuFirstName = stuFirstName;
	}
	public String getStuLastName() {
		return stuLastName;
	}
	public void setStuLastName(String stuLastName) {
		this.stuLastName = stuLastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStuContact() {
		return stuContact;
	}
	public void setStuContact(String stuContact) {
		this.stuContact = stuContact;
	}
	public String getStuEmail() {
		return stuEmail;
	}
	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}
	public String getStuWhatsapp() {
		return stuWhatsapp;
	}
	public void setStuWhatsapp(String stuWhatsapp) {
		this.stuWhatsapp = stuWhatsapp;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStuClass() {
		return stuClass;
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	public String getStuPass() {
		return stuPass;
	}
	public void setStuPass(String stuPass) {
		this.stuPass = stuPass;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDate getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public LocalDate getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String string) {
		this.rollNumber = string;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<AttendanceEntity> getAttendance() {
		return attendance;
	}
	public void setAttendance(List<AttendanceEntity> attendance) {
		this.attendance = attendance;
	}
	
	
}

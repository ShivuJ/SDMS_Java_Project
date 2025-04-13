package com.sdmsproject.sdms.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="attendance_db")
public class AttendanceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private ClassEntity classes;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private StudentEntity students;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private SubjectEntity subjects;
	
	private LocalDate date;
	private String attendance;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClassEntity getClasses() {
		return classes;
	}
	public void setClasses(ClassEntity classes) {
		this.classes = classes;
	}
	public StudentEntity getStudents() {
		return students;
	}
	public void setStudents(StudentEntity students) {
		this.students = students;
	}
	public SubjectEntity getSubjects() {
		return subjects;
	}
	public void setSubjects(SubjectEntity subjects) {
		this.subjects = subjects;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	
}

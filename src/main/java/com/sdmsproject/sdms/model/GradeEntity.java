package com.sdmsproject.sdms.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grade_db")
public class GradeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private Long stuTeachClass;
	private Long subject;
	private String stuName;
	private int assessmentMarks;
	private int examMarks;
	private int totalMarks;
	private int teacherId;
	private LocalDate createdOn;
	private LocalDate updatedOn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getStuTeachClass() {
		return stuTeachClass;
	}
	public void setStuTeachClass(Long stuTeachClass) {
		this.stuTeachClass = stuTeachClass;
	}
	public Long getSubject() {
		return subject;
	}
	public void setSubject(Long subject) {
		this.subject = subject;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public int getAssessmentMarks() {
		return assessmentMarks;
	}
	public void setAssessmentMarks(int assessmentMarks) {
		this.assessmentMarks = assessmentMarks;
	}
	public int getExamMarks() {
		return examMarks;
	}
	public void setExamMarks(int examMarks) {
		this.examMarks = examMarks;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public LocalDate getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDate getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn;
	}
}

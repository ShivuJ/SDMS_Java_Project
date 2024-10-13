package com.sdmsproject.sdms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "strudent_db")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String stuFirstName;
	private String stuLastName;
	
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
	
	
}

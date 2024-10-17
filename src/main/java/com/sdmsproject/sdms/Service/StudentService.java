package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.StudentEntity;

public interface StudentService {
	
	ResponseEntity<String> createStudent(StudentEntity stuEntity);

}

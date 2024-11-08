package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.StudentEntity;

public interface StudentService {
	
	ResponseEntity<String> createStudent(StudentEntity stuEntity);
	List<StudentEntity> readAllStudent();
	ResponseEntity<StudentEntity> readStuById(Long id);
	ResponseEntity<String> inactivateStudent(Long id);

}

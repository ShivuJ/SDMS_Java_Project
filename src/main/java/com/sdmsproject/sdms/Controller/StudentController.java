package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.StudentService;
import com.sdmsproject.sdms.model.StudentEntity;

@RestController
public class StudentController {
	
	@Autowired
	StudentService stuService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<String> createStudent(@RequestBody StudentEntity stuEntity){
		ResponseEntity<String> response = stuService.createStudent(stuEntity);
		return response;
		
	}
	
	@GetMapping("/studentList")
	public List<StudentEntity> readAllStudent(){
		return stuService.readAllStudent();
	}

}

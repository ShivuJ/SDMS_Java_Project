package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.ClassService;
import com.sdmsproject.sdms.model.ClassEntity;

@RestController
public class ClassController {
	
	@Autowired
	ClassService classService;
	
	@PostMapping("/addClass")
	public ResponseEntity<String> createClass(@RequestBody ClassEntity classEntity){
		ResponseEntity<String> response = classService.createClass(classEntity);
		
		System.out.println("User added: " + classEntity);
		
		return response;
	}
	
	@GetMapping("/getClasses")
	public List<ClassEntity> readAllClasses(){
		return classService.readAllClasses();
	}
	
	@GetMapping("/editClass/{id}")
	ResponseEntity<ClassEntity> readAllClassById(@PathVariable Long id){
		ResponseEntity<ClassEntity> response = classService.readAllClassById(id);
		
		return response;
	}

}

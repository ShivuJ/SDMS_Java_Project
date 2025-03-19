package com.sdmsproject.sdms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sdmsproject.sdms.Service.GradeService;
import com.sdmsproject.sdms.model.GradeEntity;

@Controller
public class GradeController {
	
	@Autowired
	GradeService gradeService;
	
	@PostMapping("/assignGrade")
	public ResponseEntity<String> assignGrade(@RequestBody GradeEntity grade){
		ResponseEntity<String> response = gradeService.assignGrade(grade);
		
		System.out.println("Grade Assigned: " + grade);
		return response;
	}
	
}

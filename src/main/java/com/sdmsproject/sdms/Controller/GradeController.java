package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.GradeService;
import com.sdmsproject.sdms.model.GradeEntity;

@RestController
public class GradeController {
	
	@Autowired
	GradeService gradeService;
	
	@PostMapping("/assignGrade")
	public ResponseEntity<String> assignGrade(@RequestBody List<GradeEntity> grade){
		ResponseEntity<String> response = gradeService.assignGrade(grade);
		
		System.out.println("Grade Assigned: " + grade);
		return response;
	}
	
	@GetMapping("/getStudentMarks")
	public List<GradeEntity> readAllStuMarks(){
		return gradeService.readAllStuMarks();
	}
	
}

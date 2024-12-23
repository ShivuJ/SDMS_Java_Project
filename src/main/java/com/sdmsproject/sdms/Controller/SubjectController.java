package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.SubjectService;
import com.sdmsproject.sdms.model.SubjectEntity;

@RestController
public class SubjectController {
	
	@Autowired
	SubjectService subService;
	
	@PostMapping("/addSubject")
	public ResponseEntity<String> createSubject(@RequestBody SubjectEntity subject){
		ResponseEntity<String> response = subService.createSubject(subject);
		System.out.println(subject);
		return response;
	}
	
	@GetMapping("/readSubject")
	public List<SubjectEntity> readAllSubject(){
		return subService.readAllSubject();
	}

}

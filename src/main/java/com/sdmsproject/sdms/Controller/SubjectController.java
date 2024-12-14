package com.sdmsproject.sdms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sdmsproject.sdms.Service.SubjectService;
import com.sdmsproject.sdms.model.SubjectEntity;

public class SubjectController {
	
	@Autowired
	SubjectService subService;
	
	@PostMapping("/addSubject")
	public ResponseEntity<String> createSubject(@RequestBody SubjectEntity subject){
		ResponseEntity<String> response = subService.createSubject(subject);
		return response;
	}

}

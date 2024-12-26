package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping("/inactivateSubject/{id}")
	public ResponseEntity<String> inactivateSub(@RequestBody Long id){
		ResponseEntity<String> response = subService.inactivateSub(id);
		
		return response;
	}
	
	@GetMapping("/editSubject/{id}")
	public ResponseEntity<SubjectEntity> readSubById(@PathVariable Long id){
		ResponseEntity<SubjectEntity> response = subService.readSubById(id);
		
		return response;
	}
	
	@PostMapping("/activateSubject/{id}")
	public ResponseEntity<String> activateSub(@RequestBody Long id){
		ResponseEntity<String> response = subService.activeSub(id);
		
		return response;
	}

}

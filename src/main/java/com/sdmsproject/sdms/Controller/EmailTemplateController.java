package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.EmailTemplateService;
import com.sdmsproject.sdms.model.EmailTemplate;

@RestController
public class EmailTemplateController {
	
	@Autowired
	EmailTemplateService emailTempService;
	
	@PostMapping("/emailTemplate")
	public ResponseEntity<String> createTemplate(@RequestBody EmailTemplate emailTempData){
		
		ResponseEntity<String> response = emailTempService.createTemplate(emailTempData);
		return response;
		
	}
	
	@GetMapping("/readEmails")
	public List<EmailTemplate> readEmailTemplated(){
		return emailTempService.readEmailTemplated();
		
	}

}

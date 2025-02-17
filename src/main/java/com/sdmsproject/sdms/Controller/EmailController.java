package com.sdmsproject.sdms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.EmailService;

@RestController
public class EmailController {

	@Autowired
	public EmailService emailService;
	
	@GetMapping("/sendEmail")
	public String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text, @RequestParam String name, @RequestParam String username, @RequestParam String password, @RequestParam String type) {
		
		emailService.SendSimpleMail(to, subject, text, name, username, password, type);
		return "Email Sent Successfully";
	}
	
	
}

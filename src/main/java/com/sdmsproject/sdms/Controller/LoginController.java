package com.sdmsproject.sdms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.LoginService;
import com.sdmsproject.sdms.model.UserEntity;

@RestController
//@CrossOrigin("https://localhost:8080/index.html")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<String> userValidation(@RequestBody UserEntity loginUser){
		
		ResponseEntity<String> response = loginService.loginUser(loginUser);
		System.out.println(loginUser);
		
		return response;
	}


}

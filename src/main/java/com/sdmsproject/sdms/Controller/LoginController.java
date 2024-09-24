package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sdmsproject.sdms.Service.LoginService;
import com.sdmsproject.sdms.model.UserEntity;

@Controller
//@CrossOrigin("https://localhost:8080/index.html")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public List<UserEntity> userValidation(){
		return loginService.loginUser();
	}
	
//	@PostMapping("/home")
//	public String getUserLogin() {
//		return "redirect://localhost:8080/home.html";
//	}


}

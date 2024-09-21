package com.sdmsproject.sdms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@CrossOrigin("https://localhost:8080/login.html")
public class LoginController {

	@PostMapping("/home")
	public String getUserLogin() {
		return "redirect://localhost:8080/home.html";
	}


}

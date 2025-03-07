	package com.sdmsproject.sdms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.LoginService;
import com.sdmsproject.sdms.model.UserEntity;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
//@CrossOrigin("https://localhost:8080/index.html")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<String> userValidation(@RequestBody UserEntity users, HttpServletResponse response){
		
//		UserEntity user = new UserEntity();
//		
//		user.setEmail(users.getEmail());
//		user.setPassword(users.getPassword());
		
		UserEntity loggedInUser = loginService.loginUser(users);
		
		if(loggedInUser==null) {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}
		
		// Set cookies for username, class, and role
		setCookie(response, "username", loggedInUser.getFirstName(), 1);
		setCookie(response, "userClass", loggedInUser.getTeacherClass().toString(), 1);
		setCookie(response, "userRole", loggedInUser.getRole(), 1);
		setCookie(response, "userLastName", loggedInUser.getLastName(), 1);
		
		// Redirect based on role
		String redirectUrl = switch(loggedInUser.getRole()) {
			case "Admin" -> "/adminHome.html";
			case "Teacher" -> "/teacherHome.html";
			case "Student" -> "/studentHome.html";
			default -> "Invalid User";
			
		};
		
//		ResponseEntity<String> response = loginService.loginUser(loginUser);
//		System.out.println(loginUser);
		
		return ResponseEntity.ok(redirectUrl);
	}

	private void setCookie(HttpServletResponse response, String name, String value, int days) {
		
		Cookie cookie = new Cookie(name, value);
		
		cookie.setMaxAge((int) (days * 0.5 * 60 * 60));
		cookie.setPath("/");
		
		response.addCookie(cookie);
	}


}

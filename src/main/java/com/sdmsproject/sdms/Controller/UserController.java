package com.sdmsproject.sdms.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.User;

public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/addTeacher")
	public String addTeacher(@RequestBody User user) {
		
		if(user == null) {
			System.out.println("User Data is missing");
		}
		
		return userService.createUser(user);
	}
}

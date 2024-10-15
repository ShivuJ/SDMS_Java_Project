package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.UserEntity;

public interface LoginService {
	
		ResponseEntity<String> loginUser(UserEntity user);
		
}

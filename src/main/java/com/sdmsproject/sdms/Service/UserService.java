package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.User;

public interface UserService {

	ResponseEntity<String> createUser(User user);
}

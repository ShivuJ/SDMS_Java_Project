package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.UserEntity;

public interface UserService {

	ResponseEntity<String> createUser(UserEntity user);
}

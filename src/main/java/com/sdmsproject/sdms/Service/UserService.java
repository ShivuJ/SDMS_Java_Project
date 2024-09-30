package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.UserEntity;

public interface UserService {

	ResponseEntity<String> createUser(UserEntity user);
	List<UserEntity> readAllUsers();
	ResponseEntity<String> inactivateUser(Long id);
	ResponseEntity<String> updateUser(Long id, UserEntity user);
}

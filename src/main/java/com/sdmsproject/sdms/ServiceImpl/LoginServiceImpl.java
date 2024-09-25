package com.sdmsproject.sdms.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.LoginService;
import com.sdmsproject.sdms.model.UserEntity;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseEntity<String> loginUser(UserEntity user){
		
		
		List<UserEntity> users = userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
		
		if (!users.isEmpty()) {
			return ResponseEntity.ok("Success");
		}else {
			return ResponseEntity.status(401).body("Invalid Credentials");
		}
		
		
	}

}

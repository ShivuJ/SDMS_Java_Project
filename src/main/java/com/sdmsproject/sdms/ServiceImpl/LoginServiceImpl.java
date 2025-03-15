package com.sdmsproject.sdms.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.LoginService;
import com.sdmsproject.sdms.model.UserEntity;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserEntity loginUser(UserEntity user){
		
		
		List<UserEntity> users = userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
//		UserEntity getUser = users.get(0);
//		String role = getUser.getRole();
		
		
//		if (!users.isEmpty()) {
//			if(role.equals("Admin")) {
//				return ResponseEntity.ok("/adminHome.html");
//			}else if(role.equals("Teacher")) {
//				return ResponseEntity.ok("/teacherHome.html");
//			}else if(role.equals("Student")) {
//				return ResponseEntity.ok("/studentHome.html");
//			}else {
//				return ResponseEntity.ok("Invalid User");
//			}
//			
//		}else {
//			return ResponseEntity.status(401).body("Invalid Credentials");
//		}
		
		if(users.isEmpty()) {
			return null;
		}
		
		return users.get(0);
		
		
	}

}

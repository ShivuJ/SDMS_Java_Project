package com.sdmsproject.sdms.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.UserEntity;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;

//	 @Autowired
//	 UserService userService;
	 
	@Override
	public ResponseEntity<String>  createUser(UserEntity user) {

//		UserEntity userEntity = new UserEntity();
//		BeanUtils.copyProperties(user, userEntity);

//		ResponseEntity<String> responseEntity = userService.createUser(user);
		userRepository.save(user);
	    return ResponseEntity.ok("Success");
	}
	
	@Override
	public List<UserEntity> readAllUsers(){
		
		List<UserEntity> userList = userRepository.findAll();
		List<UserEntity> users = new ArrayList<>();
		
		for(UserEntity userEntity : userList) {
			UserEntity user = new UserEntity();
			
			user.setId(userEntity.getId());
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setEmail(userEntity.getEmail());
			user.setRole(userEntity.getRole());
			user.setPhone(userEntity.getPhone());
			user.setTeacherClass(userEntity.getTeacherClass());
			user.setSubject(userEntity.getSubject());
			user.setPassword(userEntity.getPassword());
			user.setStatus(userEntity.getStatus());
			users.add(user);
		}
		
		return users;
		
	}

	@Override
	public ResponseEntity<String> inactivateUser(Long id) {
		UserEntity user = userRepository.findById(id).get();
		user.setStatus("N");
		
		userRepository.save(user);
		
		return ResponseEntity.ok("Success");
	}

}

package com.sdmsproject.sdms.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.UserEntity;

@Service
public class UserServiceImpl implements UserService{

//	@Autowired
//	UserRepository userRepository;

	 @Autowired
	 UserService userService;
	 
	@Override
	public ResponseEntity<String>  createUser(UserEntity user) {

//		UserEntity userEntity = new UserEntity();
//		BeanUtils.copyProperties(user, userEntity);

		ResponseEntity<String> responseEntity = userService.createUser(user);
	    return responseEntity;
	}

}

package com.sdmsproject.sdms.ServiceImpl;

import java.util.ArrayList;
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
	public List<UserEntity> loginUser(){
		
		List<UserEntity> userList = userRepository.findAll();
		List<UserEntity> loginUser = new ArrayList<>();
		
		for(UserEntity userEntity : userList) {
			UserEntity userData = new UserEntity();
			
			userData.setEmail(userEntity.getEmail());
			userData.setPassword(userEntity.getPassword());
			loginUser.add(userData);
		}
		
		return loginUser;
		
	}

}

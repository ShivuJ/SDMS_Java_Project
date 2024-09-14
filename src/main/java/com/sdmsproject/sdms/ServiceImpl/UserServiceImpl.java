package com.sdmsproject.sdms.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sdmsproject.sdms.Repository.UserRepository;
import com.sdmsproject.sdms.Service.UserService;
import com.sdmsproject.sdms.model.User;
import com.sdmsproject.sdms.model.UserEntity;

public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository uesrRepository;
	
	@Override
	public String createUser(User user) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		uesrRepository.save(userEntity);
		
		return "User Save Successfully....";
	}

}

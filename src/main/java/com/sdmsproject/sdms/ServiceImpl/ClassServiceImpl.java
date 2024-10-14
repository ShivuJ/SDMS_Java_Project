package com.sdmsproject.sdms.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Service.ClassService;
import com.sdmsproject.sdms.model.ClassEntity;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository classRepository;
	
	@Override
	public ResponseEntity<String> createClass(ClassEntity classEntity){
		Long id = classEntity.getId();
		
		if(id != null) {
			return null;
		}else {
			classRepository.save(classEntity);
			return ResponseEntity.ok("Success");
		}

	}
	
}

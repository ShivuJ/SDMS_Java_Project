package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.ClassEntity;
import com.sdmsproject.sdms.model.UserEntity;

public interface ClassService {

	ResponseEntity<String> createClass(ClassEntity classEntity);
	List<ClassEntity> readAllClasses();
	ResponseEntity<ClassEntity> readAllClassById(Long id);
}

package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.ClassEntity;

public interface ClassService {

	ResponseEntity<String> createClass(ClassEntity classEntity);
}

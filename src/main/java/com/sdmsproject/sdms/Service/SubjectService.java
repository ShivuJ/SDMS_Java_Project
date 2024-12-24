package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.SubjectEntity;

public interface SubjectService {

	ResponseEntity<String> createSubject(SubjectEntity subject);
	List<SubjectEntity> readAllSubject();
}

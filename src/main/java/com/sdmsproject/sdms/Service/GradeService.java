package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.GradeEntity;

public interface GradeService {

	ResponseEntity<String> assignGrade(GradeEntity grade);
}

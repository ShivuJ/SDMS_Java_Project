package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.GradeEntity;
import com.sdmsproject.sdms.model.StudentEntity;

public interface GradeService {

	ResponseEntity<String> assignGrade(List<GradeEntity> grade);
	List<GradeEntity> readAllStuMarks();
}

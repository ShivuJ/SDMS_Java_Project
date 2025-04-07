package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.GradeEntity;

public interface GradeService {

	ResponseEntity<String> assignGrade(List<GradeEntity> grade);
	List<GradeEntity> readAllStuMarks();
	ResponseEntity<GradeEntity> readGradeById(Long id);
}

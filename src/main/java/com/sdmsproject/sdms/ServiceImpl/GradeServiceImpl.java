package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.GradeRepository;
import com.sdmsproject.sdms.Service.GradeService;
import com.sdmsproject.sdms.model.GradeEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;


@Service
public class GradeServiceImpl implements GradeService {
	
	@Autowired
	GradeRepository gradeRepo;
	
	@Autowired
	HttpServletRequest request;

	@Override
	public ResponseEntity<String> assignGrade(List<GradeEntity> grade) {
		
		LocalDate currentDate = LocalDate.now();
		
		for(GradeEntity grades: grade) {
			if(grades.getId() == null) {
				grades.setId(grades.getId());
				grades.setStuTeachClass(grades.getStuTeachClass());
				grades.setSubject(grades.getSubject());
				grades.setStuName(grades.getStuName());
				grades.setAssessmentMarks(grades.getAssessmentMarks());
				grades.setExamMarks(grades.getExamMarks());
				grades.setTotalMarks(grades.getTotalMarks());
				grades.setTeacherId(grades.getTeacherId());
				grades.setCreatedOn(currentDate);
				grades.setUpdatedOn(currentDate);
				
				gradeRepo.save(grades);				
			}else {
				grades.setId(grades.getId());
				grades.setStuTeachClass(grades.getStuTeachClass());
				grades.setSubject(grades.getSubject());
				grades.setStuName(grades.getStuName());
				grades.setAssessmentMarks(grades.getAssessmentMarks());
				grades.setExamMarks(grades.getExamMarks());
				grades.setTotalMarks(grades.getTotalMarks());
				grades.setTeacherId(grades.getTeacherId());
				grades.setUpdatedOn(currentDate);
				
				gradeRepo.save(grades);
			}
		}
		
		return ResponseEntity.ok("Success");
	}
	
	private String getCookie(String name) {
		Cookie[] cookie = request.getCookies();
		
		if(cookie != null) {
			for(Cookie cookies: cookie) {
				if(cookies.getName().equals(name)) {
					return cookies.getValue();
				
				}
				
			}
		}
		return "Unknown";
	}
	

	@Override
	public List<GradeEntity> readAllStuMarks() {

		List<GradeEntity> gradeList = gradeRepo.findAll();
		List<GradeEntity> grades = new ArrayList<>();
		
		for(GradeEntity gradeEntity: gradeList) {
			
			if(getCookie("userClass").equals(gradeEntity.getStuTeachClass().toString())) {
				GradeEntity getGrades = new GradeEntity();
				getGrades.setId(gradeEntity.getId());
				getGrades.setStuTeachClass(gradeEntity.getStuTeachClass());
				getGrades.setSubject(gradeEntity.getSubject());
				getGrades.setStuName(gradeEntity.getStuName());
				getGrades.setAssessmentMarks(gradeEntity.getAssessmentMarks());
				getGrades.setExamMarks(gradeEntity.getExamMarks());
				getGrades.setTotalMarks(gradeEntity.getTotalMarks());
				grades.add(getGrades);
			}
		}
		
		return grades;
	}

	@Override
	public ResponseEntity<GradeEntity> readGradeById(Long id) {
		GradeEntity grade = gradeRepo.findById(id).get();
		return ResponseEntity.ok(grade);
	}

	
}

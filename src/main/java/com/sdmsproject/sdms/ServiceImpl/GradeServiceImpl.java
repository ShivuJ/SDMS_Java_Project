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
	
	private String getFullNameByCookie() {
		String firstName = getCookie("username");
		String lastName = getCookie("userLastName");
		
		return firstName + " " + lastName;
	}

}

package com.sdmsproject.sdms.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.AttendanceRepository;
import com.sdmsproject.sdms.Repository.StudentRepository;
import com.sdmsproject.sdms.Service.AttendanceService;
import com.sdmsproject.sdms.model.StudentEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	AttendanceRepository attendamceRepo;
	
	@Autowired
	StudentRepository stuRepo;
	
	@Autowired
	HttpServletRequest request;
	
	//Get list student by class
	@Override
	public List<StudentEntity> getStudentByClass() {
		String classId = getCookie("userClass");
		List<StudentEntity> student = stuRepo.findStudentByClass(Long.parseLong(classId));
		return student;
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

}

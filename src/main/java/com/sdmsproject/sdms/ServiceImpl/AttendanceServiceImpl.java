package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.AttendanceRepository;
import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Repository.StudentRepository;
import com.sdmsproject.sdms.Service.AttendanceService;
import com.sdmsproject.sdms.model.AttendanceEntity;
import com.sdmsproject.sdms.model.StudentEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	AttendanceRepository attendanceRepo;
	
	@Autowired
	StudentRepository stuRepo;
	
	@Autowired
	ClassRepository classRepo;
	
	@Autowired
	HttpServletRequest request;
	
	//Get list student by class
	@Override
	public List<StudentEntity> getStudentByClass() {
		long classId = Long.parseLong(getCookie("userClass"));
		String className = classRepo.findById(classId).get().getStuClass();
		List<StudentEntity> student = stuRepo.findStudentByClass(classId);
		
		/*
		 * for (StudentEntity students : student) { ((StudentEntity)
		 * students).setClassName(className); }
		 */
		
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

	@Override
	public ResponseEntity<String> generateAttendace(List<AttendanceEntity> attendance) {
		LocalDate currentDate = LocalDate.now();
		String fullName = getCookie("username") + " " + getCookie("userLastName");
		
		for(AttendanceEntity attendances: attendance) {
			Long id = attendances.getId();
			
			if(id == null) {
				attendances.setAttendance(attendances.getAttendance());
				attendances.setClasses(attendances.getClasses());
				attendances.setDate(attendances.getDate());
				attendances.setCreatedBy(fullName);
				attendances.setCreatedOn(currentDate);
				attendances.setUpdatedBy(fullName);
				attendances.setUpdatedOn(currentDate);
				
				attendanceRepo.save(attendances);
			}
		}
		
		return ResponseEntity.ok("Success");
	}

}

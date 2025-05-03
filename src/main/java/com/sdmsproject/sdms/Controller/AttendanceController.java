package com.sdmsproject.sdms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdmsproject.sdms.Service.AttendanceService;
import com.sdmsproject.sdms.model.AttendanceEntity;
import com.sdmsproject.sdms.model.StudentEntity;

@RestController
public class AttendanceController {
	
	@Autowired
	AttendanceService attendanceService;

	@GetMapping("/attendance/students")
	public List<StudentEntity> getStudentByClass(){
		return attendanceService.getStudentByClass();
	}
	
	@PostMapping("/generateAttendance")
	public ResponseEntity<String> generateAttendance(@RequestBody List<AttendanceEntity> attendance){
		
		ResponseEntity<String> response = attendanceService.generateAttendace(attendance);
		System.out.println("Attendance " + attendance);
		
		return response;
	}
}

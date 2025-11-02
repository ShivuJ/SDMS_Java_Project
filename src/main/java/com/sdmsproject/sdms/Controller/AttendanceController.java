package com.sdmsproject.sdms.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<String> generateAttendance(@RequestBody List<Map<String, Object>> attendanceList){
		
		ResponseEntity<String> response = attendanceService.generateAttendance(attendanceList);
		System.out.println("Attendance " + attendanceList);
		
		return response;
	}
	
	@GetMapping("/readStuAttend")
	public List<AttendanceEntity> readAllAttend(){
		return attendanceService.readAllAttend();
	}
	
	// Get single attendance record by ID
//	@GetMapping("/attendance/{id}")
//	public ResponseEntity<AttendanceEntity> getAttendanceById(@PathVariable Long id) {
//		System.out.println("Fetching attendance record with ID: " + id);
//		AttendanceEntity attendance = attendanceService.getAttendanceById(id);
//		
//		if (attendance != null) {
//			System.out.println("Attendance record found: " + attendance.getId());
//			return ResponseEntity.ok(attendance);
//		}
//		
//		System.out.println("Attendance record not found for ID: " + id);
//		return ResponseEntity.notFound().build();
//	}

	// Update/Edit attendance record
	@PutMapping("/attendance/{id}")
	public ResponseEntity<String> updateAttendance(@PathVariable Long id, @RequestBody Map<String, Object> attendanceMap) {
		System.out.println("Updating attendance record with ID: " + id);
		System.out.println("Update data: " + attendanceMap);
		
		return attendanceService.editAttendance(id, attendanceMap);
	}
}
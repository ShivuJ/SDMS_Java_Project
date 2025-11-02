package com.sdmsproject.sdms.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.AttendanceEntity;
import com.sdmsproject.sdms.model.StudentEntity;

public interface AttendanceService {

	List<StudentEntity> getStudentByClass();
	ResponseEntity<String> generateAttendance(List<Map<String, Object>> attendanceList);
	List<AttendanceEntity> readAllAttend();
	ResponseEntity<String> editAttendance(Long attendanceId, Map<String, Object> attendanceMap);
	AttendanceEntity getAttendanceById(Long attendanceId);
}

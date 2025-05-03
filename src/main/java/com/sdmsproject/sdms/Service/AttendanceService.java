package com.sdmsproject.sdms.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.AttendanceEntity;
import com.sdmsproject.sdms.model.StudentEntity;

public interface AttendanceService {

	List<StudentEntity> getStudentByClass();
	ResponseEntity<String> generateAttendace(List<AttendanceEntity> attendance);
}

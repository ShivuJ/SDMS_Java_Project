package com.sdmsproject.sdms.Service;

import java.util.List;

import com.sdmsproject.sdms.model.StudentEntity;

public interface AttendanceService {

	List<StudentEntity> getStudentByClass();
}

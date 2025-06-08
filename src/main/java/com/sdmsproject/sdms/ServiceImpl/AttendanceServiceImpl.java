package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.AttendanceRepository;
import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Repository.StudentRepository;
import com.sdmsproject.sdms.Service.AttendanceService;
import com.sdmsproject.sdms.model.AttendanceEntity;
import com.sdmsproject.sdms.model.ClassEntity;
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

	// Get list student by class
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

		if (cookie != null) {
			for (Cookie cookies : cookie) {
				if (cookies.getName().equals(name)) {
					return cookies.getValue();

				}

			}
		}
		return "Unknown";
	}

	@Override
	public ResponseEntity<String> generateAttendance(List<Map<String, Object>> attendanceList) {
		LocalDate currentDate = LocalDate.now();
		String fullName = getCookie("username") + " " + getCookie("userLastName");

		for (Map<String, Object> attendanceMap : attendanceList) {
			String studentId = Optional.ofNullable(attendanceMap.get("studentId")).map(Object::toString).orElse(null);
			String classId = Optional.ofNullable(attendanceMap.get("classId")).map(Object::toString).orElse(null);
			String dateStr = Optional.ofNullable(attendanceMap.get("date")).map(Object::toString).orElse(null);

			LocalDate date = null;
			if (dateStr != null) {
				date = LocalDate.parse(dateStr); // Assumes format is ISO (yyyy-MM-dd)
			}

			String status = Optional.ofNullable(attendanceMap.get("attendance")).map(Object::toString).orElse(null);

			
			Long clsId = Long.parseLong(classId);

			if (studentId == null || classId == null || date == null || status == null) {
				// Log and skip this record
				System.out.println("Missing data in attendance entry, skipping.");
				continue;
			} else {
				Long stuId = Long.parseLong(studentId);
				StudentEntity student = stuRepo.findById(stuId).orElse(null);
				ClassEntity cls = classRepo.findById(clsId).orElse(null);

				if (student != null && cls != null) {
					AttendanceEntity entity = new AttendanceEntity();
					entity.setStudents(student);
					entity.setClasses(cls);
					entity.setAttendance(status);
					entity.setDate(date);
					entity.setCreatedBy(fullName);
					entity.setCreatedOn(currentDate);
					entity.setUpdatedBy(fullName);
					entity.setUpdatedOn(currentDate);

					attendanceRepo.save(entity);
				}
			}
		}

		return ResponseEntity.ok("Success");
	}
}

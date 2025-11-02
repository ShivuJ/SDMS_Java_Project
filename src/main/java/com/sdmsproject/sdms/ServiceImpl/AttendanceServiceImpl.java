package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
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

	@Override
	public List<AttendanceEntity> readAllAttend() {
	    Long classId = Long.parseLong(getCookie("userClass"));
	    // Use the new repository method that includes student details and roll number
	    List<AttendanceEntity> attendList = attendanceRepo.findByClassWithStudentDetails(classId);
	    List<AttendanceEntity> attendance = new ArrayList<>();
	    

	    for (AttendanceEntity attendances : attendList) {
	        AttendanceEntity getAttendance = new AttendanceEntity();

	        // Set ID
	        getAttendance.setId(attendances.getId());
	        
	        // Set attendance status
	        getAttendance.setAttendance(attendances.getAttendance());
	        
	        // Set date
	        getAttendance.setDate(attendances.getDate());
	        
	        // Set audit fields
	        getAttendance.setCreatedBy(attendances.getCreatedBy());
	        getAttendance.setCreatedOn(attendances.getCreatedOn());
	        getAttendance.setUpdatedBy(attendances.getUpdatedBy());
	        getAttendance.setUpdatedOn(attendances.getUpdatedOn());

	        // Fetch and set complete student details
	        if (attendances.getStudents() != null && attendances.getStudents().getId() != null) {
	            Optional<StudentEntity> student = stuRepo.findById(attendances.getStudents().getId());
	            if (student.isPresent()) {
	                getAttendance.setStudents(student.get());
	                System.out.println("Student: " + student.get().getStuFirstName() + " " + 
	                    student.get().getStuLastName() + " - Roll: " + student.get().getRollNumber());
	            }
	        }
	        
	        // Fetch and set complete class details
	        if (attendances.getClasses() != null && attendances.getClasses().getId() != null) {
	            Optional<ClassEntity> cls = classRepo.findById(attendances.getClasses().getId());
	            if (cls.isPresent()) {
	                getAttendance.setClasses(cls.get());
	                System.out.println("Class: " + cls.get().getStuClass());
	            }
	        }

	        attendance.add(getAttendance);
	    }
	    
	    System.out.println("Total attendance records retrieved: " + attendance.size());
	    return attendance;
	}

	@Override
	public ResponseEntity<String> editAttendance(Long attendanceId, Map<String, Object> attendanceMap) {
	    // Validate attendance ID
	    if (attendanceId == null || attendanceId <= 0) {
	        return ResponseEntity.badRequest().body("Invalid attendance ID");
	    }

	    // Check if attendance record exists
	    Optional<AttendanceEntity> existingAttendance = attendanceRepo.findById(attendanceId);
	    if (!existingAttendance.isPresent()) {
	        return ResponseEntity.status(404).body("Attendance record not found");
	    }

	    try {
	        AttendanceEntity attendance = existingAttendance.get();
	        LocalDate currentDate = LocalDate.now();
	        String fullName = getCookie("username") + " " + getCookie("userLastName");

	        // Update student if provided
	        if (attendanceMap.containsKey("studentId") && attendanceMap.get("studentId") != null) {
	            Long studentId = Long.parseLong(attendanceMap.get("studentId").toString());
	            StudentEntity student = stuRepo.findById(studentId).orElse(null);
	            if (student != null) {
	                attendance.setStudents(student);
	            } else {
	                return ResponseEntity.badRequest().body("Invalid student ID");
	            }
	        }

	        // Update class if provided
	        if (attendanceMap.containsKey("classId") && attendanceMap.get("classId") != null) {
	            Long classId = Long.parseLong(attendanceMap.get("classId").toString());
	            ClassEntity cls = classRepo.findById(classId).orElse(null);
	            if (cls != null) {
	                attendance.setClasses(cls);
	            } else {
	                return ResponseEntity.badRequest().body("Invalid class ID");
	            }
	        }

	        // Update date if provided
	        if (attendanceMap.containsKey("date") && attendanceMap.get("date") != null) {
	            String dateStr = attendanceMap.get("date").toString();
	            try {
	                LocalDate date = LocalDate.parse(dateStr); // Assumes ISO format (yyyy-MM-dd)
	                attendance.setDate(date);
	            } catch (Exception e) {
	                return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd");
	            }
	        }

	        // Update attendance status if provided
	        if (attendanceMap.containsKey("attendance") && attendanceMap.get("attendance") != null) {
	            String status = attendanceMap.get("attendance").toString();
	            attendance.setAttendance(status);
	        }

	        // Set update metadata
	        attendance.setUpdatedBy(fullName);
	        attendance.setUpdatedOn(currentDate);

	        // Save the updated record
	        attendanceRepo.save(attendance);

	        return ResponseEntity.ok("Attendance record updated successfully");

	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body("Invalid number format in request");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error updating attendance: " + e.getMessage());
	    }
	}
}
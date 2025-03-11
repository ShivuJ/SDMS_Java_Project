package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.StudentRepository;
import com.sdmsproject.sdms.Service.StudentService;
import com.sdmsproject.sdms.model.StudentEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public ResponseEntity<String> createStudent(StudentEntity stuEntity) {
		Long id = stuEntity.getId();
		
		//creating variable for set value for Created On, Created By, Updated On, and Updated By
		String cuName = getFullNameByCookie();
		LocalDate currentDate = LocalDate.now();
		
		if(id!=null) {
			StudentEntity existingStudent = studentRepository.findById(id).get();
			existingStudent.setId(stuEntity.getId());
			existingStudent.setStuFirstName(stuEntity.getStuFirstName());
			existingStudent.setStuLastName(stuEntity.getStuLastName());
			existingStudent.setStuContact(stuEntity.getStuContact());
			existingStudent.setStuEmail(stuEntity.getStuEmail());
			existingStudent.setStuClass(stuEntity.getStuClass());
			existingStudent.setStuWhatsapp(stuEntity.getStuWhatsapp());
			existingStudent.setRole(stuEntity.getRole());
			existingStudent.setStatus(stuEntity.getStatus());
			existingStudent.setStuPass(stuEntity.getStuPass());
			existingStudent.setUpdatedBy(cuName);
			existingStudent.setUpdatedOn(currentDate);
			
			studentRepository.save(existingStudent);
			return ResponseEntity.ok("Success");
		}else {
			stuEntity.setId(stuEntity.getId());
			stuEntity.setStuFirstName(stuEntity.getStuFirstName());
			stuEntity.setStuLastName(stuEntity.getStuLastName());
			stuEntity.setStuContact(stuEntity.getStuContact());
			stuEntity.setStuEmail(stuEntity.getStuEmail());
			stuEntity.setStuClass(stuEntity.getStuClass());
			stuEntity.setStuWhatsapp(stuEntity.getStuWhatsapp());
			stuEntity.setRole(stuEntity.getRole());
			stuEntity.setStatus(stuEntity.getStatus());
			stuEntity.setStuPass(stuEntity.getStuPass());
			stuEntity.setCreatedBy(cuName);
			stuEntity.setCreatedOn(currentDate);
			stuEntity.setUpdatedBy(cuName);
			stuEntity.setUpdatedOn(currentDate);
			studentRepository.save(stuEntity);
			return ResponseEntity.ok("Success");
		}
		
	}

	@Override
	public List<StudentEntity> readAllStudent() {
		List<StudentEntity> stuList = studentRepository.findAll();
		List<StudentEntity> student = new ArrayList<>();
		
		for(StudentEntity studentEntity : stuList) {
			
			StudentEntity getStudent = new StudentEntity();
			
			getStudent.setId(studentEntity.getId());
			getStudent.setStuFirstName(studentEntity.getStuFirstName());
			getStudent.setStuLastName(studentEntity.getStuLastName());
			getStudent.setStuContact(studentEntity.getStuContact());
			getStudent.setStuEmail(studentEntity.getStuEmail());
			getStudent.setStuClass(studentEntity.getStuClass());
			getStudent.setStuWhatsapp(studentEntity.getStuWhatsapp());
			getStudent.setRole(studentEntity.getRole());
			getStudent.setStatus(studentEntity.getStatus());
			getStudent.setStuPass(studentEntity.getStuPass());
			student.add(getStudent);
			
		}
		return student;
	}

	@Override
	public ResponseEntity<StudentEntity> readStuById(Long id) {
		StudentEntity getStudent = studentRepository.findById(id).get();
		return ResponseEntity.ok(getStudent);
	}

	@Override
	public ResponseEntity<String> inactivateStudent(Long id) {
		StudentEntity student = studentRepository.findById(id).get();
		student.setStatus("N");
		
		studentRepository.save(student);
		return ResponseEntity.ok("Success");
	}
	
	// get Cookie data
	private String getCookiesData(String name) {
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
		String firstName = getCookiesData("username");
		String lastName = getCookiesData("userLastName");
		
		return firstName + " " + lastName;
	}
	
}

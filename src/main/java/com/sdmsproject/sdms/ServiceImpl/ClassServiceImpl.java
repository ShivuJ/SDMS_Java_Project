package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.ClassRepository;
import com.sdmsproject.sdms.Service.ClassService;
import com.sdmsproject.sdms.model.ClassEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public ResponseEntity<String> createClass(ClassEntity classEntity){
		Long id = classEntity.getId();
		
		//creating variable for set value for Created On, Created By, Updated On, and Updated By
		String cuName = getFullNameByCookie();
		LocalDate currentDate = LocalDate.now();
		
		if(id != null) {
			ClassEntity existingClass = classRepository.findById(id).get();
			
			existingClass.setStuClass(classEntity.getStuClass());
			existingClass.setUpdatedBy(cuName);
			existingClass.setUpdatedOn(currentDate);
			
			classRepository.save(existingClass);
			
			return ResponseEntity.ok("Success");
		}else {
			classEntity.setStuClass(classEntity.getStuClass());
			classEntity.setCreatedBy(cuName);
			classEntity.setCreatedOn(currentDate);
			classEntity.setUpdatedBy(cuName);
			classEntity.setUpdatedOn(currentDate);
			classRepository.save(classEntity);
			return ResponseEntity.ok("Success");
		}

	}

	@Override
	public List<ClassEntity> readAllClasses() {
		List<ClassEntity> classList = classRepository.findAll();
		List<ClassEntity> classes = new ArrayList<>();
		
		for(ClassEntity classEntity : classList) {
			ClassEntity addClass = new ClassEntity();
			addClass.setId(classEntity.getId());
			addClass.setStuClass(classEntity.getStuClass());
			addClass.setStatus(classEntity.getStatus());
			classes.add(addClass);
		}
		return classes;
	}

	@Override
	public ResponseEntity<ClassEntity> readAllClassById(Long id) {
		ClassEntity editClass = classRepository.findById(id).get();

		return ResponseEntity.ok(editClass);
	}

	@Override
	public ResponseEntity<String> inactivateClass(Long id) {
		ClassEntity editClass = classRepository.findById(id).get();
		
		editClass.setStatus("N");
		classRepository.save(editClass);
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

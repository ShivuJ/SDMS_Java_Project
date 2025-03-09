package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.SubjectRepository;
import com.sdmsproject.sdms.Service.SubjectService;
import com.sdmsproject.sdms.model.SubjectEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	SubjectRepository subRepo;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public ResponseEntity<String> createSubject(SubjectEntity subject) {
		Long id = subject.getId();
		

		String username = getFullNameByCookies();
		LocalDate currentDate = LocalDate.now();
		if(id!=null) {
			
			SubjectEntity existingSubject = subRepo.findById(id).get();
			
			subject.setUpdatedBy(username);
			subject.setUpdatedOn(currentDate);
			subject.setSubject(subject.getSubject());
			subject.setCreatedBy(existingSubject.getCreatedBy());
			subject.setCreatedOn(existingSubject.getCreatedOn());
			
			subRepo.save(subject);
			return ResponseEntity.ok("Success");
		}else {
				
			subject.setCreatedOn(currentDate);
			subject.setCreatedBy(username);
			subject.setUpdatedBy(username);
			subject.setUpdatedOn(currentDate);
						
			subRepo.save(subject);
			return ResponseEntity.ok("Success");
		}
		
	}

	@Override
	public List<SubjectEntity> readAllSubject() {

		List<SubjectEntity> subjectList = subRepo.findAll();
		List<SubjectEntity> subjects = new ArrayList<>();
		
		for (SubjectEntity subjectEntity : subjectList) {
			SubjectEntity subject = new SubjectEntity();
			
			subject.setId(subjectEntity.getId());
			subject.setSubject(subjectEntity.getSubject());
			subject.setStatus(subjectEntity.getStatus());
			
			subjects.add(subject);
		}
		return subjects;
	}

	@Override
	public ResponseEntity<String> inactivateSub(Long id) {
		SubjectEntity subject = subRepo.findById(id).get();
		subject.setStatus('N');
		
		subRepo.save(subject);
		
		return ResponseEntity.ok("Success");
	}

	@Override
	public ResponseEntity<SubjectEntity> readSubById(Long id) {
		SubjectEntity subject = subRepo.findById(id).get();
		return ResponseEntity.ok(subject);
	}

	@Override
	public ResponseEntity<String> activeSub(Long id) {
		SubjectEntity subject = subRepo.findById(id).get();
		subject.setStatus('Y');
		
		subRepo.save(subject);
		
		return ResponseEntity.ok("Sucsess");
	}

//🔹 Helper method to get cookie value by name
	private String getCookieValue(String name) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		
		return "Unknown";
	}
	
	private String getFullNameByCookies() {
		String firstName = getCookieValue("username");
		String lastName = getCookieValue("userLastName");
		
		return firstName + " " + lastName;
	}
	
	
	
}

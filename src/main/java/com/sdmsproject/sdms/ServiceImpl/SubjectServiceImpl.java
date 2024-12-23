package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.SubjectRepository;
import com.sdmsproject.sdms.Service.SubjectService;
import com.sdmsproject.sdms.model.SubjectEntity;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	SubjectRepository subRepo;

	@Override
	public ResponseEntity<String> createSubject(SubjectEntity subject) {
		Long id = subject.getId();
		String username = System.getProperty("user.name");
		LocalDate currentDate = LocalDate.now();
		if(id!=null) {
			
			SubjectEntity existingSubject = subRepo.findById(id).get();
			
			existingSubject.setUpdatedBy(username);
			existingSubject.setUpdatedOn(currentDate);
			existingSubject.setSubject(subject.getSubject());
			
			subRepo.save(subject);
			return ResponseEntity.ok("Sucess");
		}else {
				
			subject.setCreatedOn(currentDate);
			subject.setCreatedBy(username);
			subject.setUpdatedBy(username);
			subject.setUpdatedOn(currentDate);
						
			subRepo.save(subject);
			return ResponseEntity.ok("Sucess");
		}
		
	}

}

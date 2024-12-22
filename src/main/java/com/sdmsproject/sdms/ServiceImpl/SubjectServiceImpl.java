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
		if(id!=null) {
			return null;
		}else {
			String username = System.getProperty("user.name");
			LocalDate currentDate = LocalDate.now();
			
			subject.setCreatedOn(currentDate);
			subject.setCreatedBy(username);
			subject.setUpdatedBy(username);
			subject.setUpdatedOn(currentDate);
						
			subRepo.save(subject);
			return ResponseEntity.ok("Sucess");
		}
		
	}

}

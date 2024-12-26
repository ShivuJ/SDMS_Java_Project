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
		
		return ResponseEntity.ok("Sucess");
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
		
		return ResponseEntity.ok("Sucess");
	}
	
	

}

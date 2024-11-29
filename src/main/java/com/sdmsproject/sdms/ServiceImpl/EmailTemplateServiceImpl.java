package com.sdmsproject.sdms.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.EmailTemplateRepository;
import com.sdmsproject.sdms.Service.EmailTemplateService;
import com.sdmsproject.sdms.model.EmailTemplate;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	EmailTemplateRepository emailTempRepo;
	
	@Override
	public ResponseEntity<String> createTemplate(EmailTemplate emailTemplate) {
		Long id = emailTemplate.getId();
		if(id != null) {
			return null;
		}else {
			emailTempRepo.save(emailTemplate);
			return ResponseEntity.ok("Success");
		}
	}

}

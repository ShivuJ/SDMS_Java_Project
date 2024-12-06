package com.sdmsproject.sdms.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
			EmailTemplate existingEmail = emailTempRepo.findById(id).get();
			
			existingEmail.setId(emailTemplate.getId());;
			existingEmail.setEmailType(emailTemplate.getEmailType());
			existingEmail.setSubject(emailTemplate.getSubject());
			existingEmail.setTemplate(emailTemplate.getTemplate());
			emailTempRepo.save(existingEmail);
			return ResponseEntity.ok("Success");
		}else {
			emailTempRepo.save(emailTemplate);
			return ResponseEntity.ok("Success");
		}
	}

	@Override
	public List<EmailTemplate> readEmailTemplated() {
		
		List<EmailTemplate> emailList = emailTempRepo.findAll();
		List<EmailTemplate> emails = new ArrayList<>();
		
		for(EmailTemplate emailTemp : emailList) {
			EmailTemplate email = new EmailTemplate();
			
			email.setId(emailTemp.getId());
			email.setEmailType(emailTemp.getEmailType());
			email.setSubject(emailTemp.getSubject());
			email.setTemplate(emailTemp.getTemplate());
			
			emails.add(email);
		}
		return emails;
	}

	@Override
	public ResponseEntity<EmailTemplate> readById(Long id) {
		EmailTemplate emailTemp = emailTempRepo.findById(id).get();
		return ResponseEntity.ok(emailTemp);
	}

}

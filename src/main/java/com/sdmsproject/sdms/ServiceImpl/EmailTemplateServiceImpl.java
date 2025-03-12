package com.sdmsproject.sdms.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.EmailTemplateRepository;
import com.sdmsproject.sdms.Service.EmailTemplateService;
import com.sdmsproject.sdms.model.EmailTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	EmailTemplateRepository emailTempRepo;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public ResponseEntity<String> createTemplate(EmailTemplate emailTemplate) {
		Long id = emailTemplate.getId();
		
		//creating variable for set value for Created On, Created By, Updated On, and Updated By
		String cuName = getFullNameByCookie();
		LocalDate currentDate = LocalDate.now();
		
		if(id != null) {
			EmailTemplate existingEmail = emailTempRepo.findById(id).get();
			
			existingEmail.setId(emailTemplate.getId());;
			existingEmail.setEmailType(emailTemplate.getEmailType());
			existingEmail.setSubject(emailTemplate.getSubject());
			existingEmail.setTemplate(emailTemplate.getTemplate());
			existingEmail.setUpdatedBy(cuName);
			existingEmail.setUpdatedOn(currentDate);
			emailTempRepo.save(existingEmail);
			return ResponseEntity.ok("Success");
		}else {
			emailTemplate.setId(emailTemplate.getId());;
			emailTemplate.setEmailType(emailTemplate.getEmailType());
			emailTemplate.setSubject(emailTemplate.getSubject());
			emailTemplate.setTemplate(emailTemplate.getTemplate());
			emailTemplate.setCreatedBy(cuName);
			emailTemplate.setCreatedOn(currentDate);
			emailTemplate.setUpdatedBy(cuName);
			emailTemplate.setUpdatedOn(currentDate);
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

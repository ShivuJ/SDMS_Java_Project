package com.sdmsproject.sdms.Service;

import org.springframework.http.ResponseEntity;

import com.sdmsproject.sdms.model.EmailTemplate;

public interface EmailTemplateService {

	ResponseEntity<String> createTemplate(EmailTemplate emailTemplate);
}

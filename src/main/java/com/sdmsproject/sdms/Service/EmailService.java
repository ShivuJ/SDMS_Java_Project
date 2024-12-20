package com.sdmsproject.sdms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sdmsproject.sdms.Repository.EmailTemplateRepository;
import com.sdmsproject.sdms.model.EmailTemplate;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;

	EmailTemplate emailTemplate = new EmailTemplate();

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
		System.out.println("Injected JavaMailSender: " + mailSender);
	}

	@PostConstruct
	public void postConstructCheck() {
		if (mailSender == null) {
			throw new IllegalStateException("JavaMailSender is null in EmailService!");
		} else {
			System.out.println("JavaMailSender initialized correctly: " + mailSender.getClass().getName());
		}
	}

	public void SendSimpleMail(String to, String subject, String text, String name, String username, String password, String type) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//			String emailType = emailTemplate.getEmailType();
			String emailType = type;
			EmailTemplate emailTemp = emailTemplateRepository.findByEmailType(emailType);
//			if ("Registration".equals(emailTemplate.getEmailType())) {
//				emailType = "Registration";
//				emailTemp = emailTemplateRepository.findByEmailType(emailType);
//			}
			
			String template = emailTemp.getTemplate();
			
			template = template.replace("[Name]", name).replace("[Username]", username).replace("[Password]", password);
			
			helper.setTo(to);
			helper.setSubject(subject);
			/* String htmlContent = template; */
			/* helper.setText(htmlContent, true); */
			helper.setText(template, true);
			// Set `true` to indicate HTML content
			mailSender.send(message);
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

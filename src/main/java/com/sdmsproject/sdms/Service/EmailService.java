package com.sdmsproject.sdms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@PostConstruct
	public void checkMailSender() {

		if (mailSender == null) {

			throw new IllegalStateException("JavaMailSender bean is not initialized!");
		}
		System.out.println("JavaMailSender bean successfully initialized.");
	}

	public void SendSimpleMail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);

		System.out.println(message);
	}
}

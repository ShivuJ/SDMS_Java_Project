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


	public void SendSimpleMail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);

		System.out.println(message);
	}
}
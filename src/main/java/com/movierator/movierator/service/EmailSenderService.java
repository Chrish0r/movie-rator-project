//package com.movierator.movierator.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailSenderService {
//	
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	public void sendEmail(String toEmail, String subject, String emailBody) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(emailBody);
//		
//		message.setFrom("anna.updated@gmail.com");
//		message.setText(toEmail);
//		message.setText(emailBody);
//		message.setSubject(subject);
//		
//		mailSender.send(message);
//		
//		logger.info("Email sent successfully...");
//
//	}
//
//}

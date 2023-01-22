//package com.movierator.movierator.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//	
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	private JavaMailSender emailSender;
//	
//	public EmailService(JavaMailSender emailSender) {
//		this.emailSender = emailSender;
//	}
//
//	
//	public void sendEmail(String to, String subject, String text) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(text);
//		
//		message.setFrom("anna.updated@gmail.com");
//		message.setText(to);
//		message.setText(text);
//		message.setSubject(subject);
//		
//		this.emailSender.send(message);
//		
//		logger.info("Email sent successfully...");
//
//	}
//
//}

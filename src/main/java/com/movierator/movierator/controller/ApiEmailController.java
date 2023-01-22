//package com.movierator.movierator.controller;
//
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.movierator.movierator.service.EmailService;
//
//public class ApiEmailController {
//
//	private EmailService emailService;
//
//	public ApiEmailController(EmailService emailService) {
//		this.emailService = emailService;
//	}
//
//	@PostMapping("/api/message")
//	public String sendEmail() {
//		this.emailService.sendEmail("Haxton.endgame@gmail.com", "this is the subject", "This is the body");
//
//		return "Message sent";
//	}
//
//}

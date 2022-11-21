package com.movierator.movierator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
	UserRepository userRepository;

	@GetMapping("/sign-up")
	public String showRegisterForm(Model model) {
		User user = new User();

		model.addAttribute(user);

		return "sign-up";
	}

	@PostMapping("/sign-up")
	public String signUp(@ModelAttribute User user, Model model) {
		System.out.println("Signed up user id: " + user.getId());
		
		return "sign-up-success";
	}
}

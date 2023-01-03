package com.movierator.movierator.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movierator.movierator.controller.formObjects.Login;

@Controller
public class LoginController {
	

	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		
		model.addAttribute("loginForm", new Login());
		
		return "login";
	}
	
	@RequestMapping ( method=RequestMethod.GET, value="/prelogout")
	public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {
	
		return "prelogout";
	}

}

package de.othr.im.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.othr.im.model.User;
import de.othr.im.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	

	@RequestMapping("/register")
	public ModelAndView showRegisterForm() {
		User userPlain = new User();
		
		ModelAndView mv = new ModelAndView("/user/registrationform");
		mv.addObject("registerForm", userPlain);
		
		return mv;
	}
}

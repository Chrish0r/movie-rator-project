package com.movierator.movierator.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.movierator.movierator.controller.formObjects.Login;
import com.movierator.movierator.model.Admin;
import com.movierator.movierator.model.Moderator;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;

@Controller
public class LoginController {
	
	private static final String ADMIN_SESSION = "adminSession";
	private static final String MODERATOR_SESSION = "moderatorSession";
	private static final String REGULAR_USER_SESSION = "regularUserSession";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	ModeratorRepository moderatorRepository;

	@Autowired
	RegularUserRepository regularUserRepository;

	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		
		model.addAttribute("loginForm", new Login());
		
		return "login";
	}
	
	@PostMapping("/login")
	public String home(@ModelAttribute("loginForm") Login loginForm, HttpServletRequest request,
			Principal principal) {

		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		String myAuthorities = authorities.toString();

		logger.info("Authorities of the logged user " + principal.getName() + ": " + myAuthorities);

		// searching in the database for the underlying user
		Optional<User> loggedUserOpt = userRepository.findUserByLogin(principal.getName());
		// TODO check is Optional present and return error or logger if not - mb no need
		// since SpringSecurity does it?

		if (myAuthorities.contains("ADMIN")) {

			Optional<Admin> adminOpt;
			// TODO check is Optional present and return error or logger if not - mb no need
			// since SpringSecurity does it?
			adminOpt = adminRepository.findAdminByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(ADMIN_SESSION, adminOpt.get());

			return "admin";
		}
		if (myAuthorities.contains("MODERATOR")) {
			Optional<Moderator> moderatorOpt;

			// TODO check is Optional present and return error or logger if not - mb no need
			// since SpringSecurity does it?
			moderatorOpt = moderatorRepository.findModeratorByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(MODERATOR_SESSION, moderatorOpt.get());

			return "moderator";
		}
		if (myAuthorities.contains("REGULAR USER")) {
			Optional<RegularUser> regularUserOpt;

			// TODO check is Optional present and return error or logger if not - mb no need
			// since SpringSecurity does it?
			regularUserOpt = regularUserRepository.findRegularUserByUserId(loggedUserOpt.get().getId());
			request.getSession().setAttribute(REGULAR_USER_SESSION, regularUserOpt.get());

			return "regular-user";
		}

		logger.warn("An error occured while trying to load the http request based on " + principal.getName());
		return "/error";
	}
	
	@RequestMapping ( method=RequestMethod.GET, value="/prelogout")
	public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {
	
		return "prelogout";
	}

}

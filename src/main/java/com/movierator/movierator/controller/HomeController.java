package com.movierator.movierator.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movierator.movierator.model.Admin;
import com.movierator.movierator.model.Moderator;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;

@Controller
public class HomeController {

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

//	@GetMapping("/")
//	public String index(Model model) {
//		return "index";
//	}

	@RequestMapping(value = { "/", "/home" })
	public String home(HttpServletRequest request, Principal principal) {

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
		if (myAuthorities.contains("REGULAR_USER")) {
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
}

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

import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.UserRepository;
import com.movierator.movierator.util.Properties;

@Controller
public class HomeController {
	
	private static final String ADMIN_SESSION = "adminSession";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserRepository userRepository;
//	
//	@Autowired
//	AdminRepository adminRepository;
	
	@GetMapping("/")
	  public String index(Model model) {
	    return "index";
	  }
	
	@RequestMapping(value={"/", "/home"})
	public String home(HttpServletRequest request, Principal principal) {
			
				
		@SuppressWarnings("unchecked")
		List <GrantedAuthority> authorities= (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String myAuthorities = authorities.toString();	
		
		logger.info("Authorities of the logged user "+ principal.getName() + ": " + myAuthorities);
		
		Optional<User> loggedUserOpt = userRepository.findUserByLogin(principal.getName());
		
//		if (myAuthorities.contains("ADMIN")){
//					
//			Optional<Admin> adminOpt; 
//			
//			if (loggedUserOpt.get().getUsertype() == Properties.USER_TYPE_ADMIN) {
//				adminOpt = adminRepository.findAdminByUserId(loggedUserOpt.get().getId());
//				request.getSession().setAttribute(ADMIN_SESSION, adminOpt.get());
//			}
//			
//			return "admin";
//		} else {
//			logger.warn("An error occured while trying to load the http request based on " + principal.getName());
//		}
		return "/";
	}
}

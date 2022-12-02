package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movierator.movierator.model.Authority;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;
import com.movierator.movierator.util.Constants;

@Controller
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
	UserRepository userRepository;
    
    @Autowired
	AdminRepository adminRepository;

	@Autowired
	ModeratorRepository moderatorRepository;
	
	@Autowired
	RegularUserRepository regularUserRepository;
	
	// TODO: public String showPaginatedUsers() -> might need for Admin
    
    @RequestMapping("/user/add")
	public ModelAndView showAddRegularUserForm() {
		
    	ModelAndView mv = new ModelAndView();
    	
    	
		RegularUser regularUser = new RegularUser();
		regularUser.setUser(new User());
		
		mv.setViewName("/user/user-regular-user-add");
		mv.addObject("regularUserForm", regularUser);
		
		return mv;
	}
    
 
    @RequestMapping("/user/add/process")
    public ModelAndView CreateUser(@Valid @ModelAttribute("regularUserForm") RegularUser regularUserForm,
                               BindingResult bindingResult) {
        
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("/user/user-regular-user-add");
            return mv;
        }
        
        Optional<User> userDB= userRepository.findUserByLogin(regularUserForm.getUser().getLogin());

        if (userDB.isPresent()) {
        	logger.warn("Login already exists!");
        	
        	bindingResult.rejectValue("user.login", "error.user", "An account already exists for this login.");
            mv.setViewName("/user/user-regular-user-add");
       	   return mv;
        } 
           
        User user = regularUserForm.getUser();
        
        user.setPassword((passwordEncoder.encode(regularUserForm.getUser().getPassword())));
        
        List<Authority> myAuthorities = new ArrayList<Authority>();
        
        myAuthorities.add(new Authority(Constants.AUTHORITY_REGULAR_USER));
        
        user.setMyAuthorities(myAuthorities);
        user.setActive(1);
        
        // User is persisted into the database
        user = userRepository.save(user);   
        
        regularUserForm.setUser(user);
        // Regular user is persisted into the database
        regularUserRepository.save(regularUserForm);
        
        mv.addObject("useradded", "User added!");
//        mv.setViewName("redirect:/user/all");
        mv.setViewName("redirect:/index");
        
        return mv;
    }
}

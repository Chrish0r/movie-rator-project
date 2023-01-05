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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

		Optional<User> userDB = userRepository.findUserByLogin(regularUserForm.getUser().getLogin());

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
		mv.setViewName("redirect:/login");

		return mv;
	}

	@RequestMapping("/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Long userId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/user/all");

		/*
		 * User user = userRepository.findById(id) .orElseThrow(() -> new
		 * IllegalArgumentException("Invalid user Id:" + id));
		 */
		logger.info("updating the user ID " + userId);

		Optional<RegularUser> regularUserOpt;
		regularUserOpt = regularUserRepository.findRegularUserByUserId(userId);
		if (regularUserOpt.isPresent()) {
			mv.setViewName("user/user-regular-user-update");
			mv.addObject("regularUserForm", regularUserOpt.get());
			logger.info("Updating the regular user LOGIN " + regularUserOpt.get().getUser().getLogin());
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/regular-user/update/process")
	public ModelAndView updateRegularUser(@Valid @ModelAttribute("regularUserForm") RegularUser regularUserForm,
			BindingResult bindingResult) {

		ModelAndView mv = new ModelAndView();

		if (bindingResult.hasErrors()) {
			logger.warn(bindingResult.toString());
			mv.setViewName("/user/user-regular-user-update");
			return mv;
		}

		logger.info("Processing the update of the regular user ID " + regularUserForm.getId());

		User userForm = regularUserForm.getUser();
		// load user from database
		User userDB = userRepository.findById(userForm.getId()).get();
		userDB.setEmail(userForm.getEmail());

		regularUserForm.setUser(userDB);
		regularUserRepository.save(regularUserForm);

		mv.addObject("user updated", "Regular User updated!");
		mv.setViewName("redirect:/user/all");
		return mv;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteUser(@PathVariable("id") long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		user.setActive(0);
		userRepository.save(user);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/user/all");
		mv.addObject("user deleted", "User deleted!");

		return mv;
	}
}

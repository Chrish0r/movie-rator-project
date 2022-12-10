package com.movierator.movierator.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.movierator.movierator.model.Authority;
import com.movierator.movierator.model.RegularUser;
import com.movierator.movierator.model.User;
import com.movierator.movierator.repository.AdminRepository;
import com.movierator.movierator.repository.ModeratorRepository;
import com.movierator.movierator.repository.RegularUserRepository;
import com.movierator.movierator.repository.UserRepository;
import com.movierator.movierator.util.Constants;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

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

	@GetMapping("/")
	public ResponseEntity<List<User>> findAllActiveUsers() {

		logger.info("passing in UserRestController...");

		List<User> users = new ArrayList<User>();

		try {
			users = userRepository.findAllActive();

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/{id}")
	  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
	    Optional<User> userOpt = userRepository.findById(id);

	    if (userOpt.isPresent()) {
	      return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

//	@PostMapping("/")
//	public ResponseEntity<List<User>> createUser(@RequestBody User user) {
//
//		logger.info("Processing the add with POST");
//		logger.info(user.getLogin());
//
//		User userTemp = user
//		userTemp.setPassword((passwordEncoder.encode(user.getPassword())));
//
//		List<Authority> myAuthorities = new ArrayList<Authority>();
//
//		myAuthorities.add(new Authority(Constants.AUTHORITY_REGULAR_USER));
//
//		userTemp.setMyAuthorities(myAuthorities);
//		userTemp.setActive(1);
//
//		try {
//			// User is persisted into the database
//			User userDB = userRepository.save(userTemp);
//
//			return new ResponseEntity<>(userDB, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
////		try {
////			// Regular user is persisted into the database
	// RegularUser regularUser = new RegularUser();
	// regularUser.setEmail(userTeo.getEmail());

////			regularUserRepository.save(regularUser);
////
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
//	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateEmailByUserId(@RequestParam String email, @RequestParam long id) {
		logger.info("Processing updating an user email with PUT");
		
		if(email.isBlank()) {
			throw new IllegalArgumentException("New email may not be empty!");
		}
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		
		user.setEmail(email);
		
		try {
			// Updating i.e., persisting user into the database
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {

		logger.info("Processing deleting an user with DELETE");
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setActive(0);
		try {
			// Updating i.e., persisting user into the database
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

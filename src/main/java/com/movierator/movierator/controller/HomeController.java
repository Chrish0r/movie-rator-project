package com.movierator.movierator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movierator.movierator.model.NewsletterSubscriber;

@Controller
public class HomeController {

	@RequestMapping(value = { "/", "/start", "/home" })
	public String showIndexView(@ModelAttribute("newsletterSubscribe") NewsletterSubscriber newsletterSubscriber) {
		return "index";
	}

}

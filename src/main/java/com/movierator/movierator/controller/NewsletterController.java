package com.movierator.movierator.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.movierator.movierator.model.NewsletterSubscriber;
import com.movierator.movierator.repository.NewsletterSubscriberRepository;

@Controller
public class NewsletterController {
  private NewsletterSubscriberRepository newsletterSubscriberRepository;

  public NewsletterController(NewsletterSubscriberRepository newsletterSubscriberRepository) {
    this.newsletterSubscriberRepository = newsletterSubscriberRepository;
  }

  @PostMapping("/subscribe-to-newsletter")
  public String subscribeToNewsletter(@Valid NewsletterSubscriber newsletterSubscriber, BindingResult result, Model model) {
    if(result.hasErrors()) {
      return "index";
    }

    if(!this.newsletterSubscriberRepository.findByEmail(newsletterSubscriber.getEmail()).isEmpty()) {
      return "newsletter/already-subscribed";
    }

    this.newsletterSubscriberRepository.save(newsletterSubscriber);

    return "newsletter/subscribe-success";
  }
}

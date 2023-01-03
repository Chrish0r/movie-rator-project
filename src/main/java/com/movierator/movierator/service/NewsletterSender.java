package com.movierator.movierator.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.search.ReceivedDateTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.movierator.movierator.model.NewsletterSubscriber;
import com.movierator.movierator.repository.NewsletterSubscriberRepository;

@Service
public class NewsletterSender {
  private final Logger logger = LoggerFactory.getLogger(NewsletterSender.class);
  
  @Autowired
  private NewsletterSubscriberRepository newsletterSubscriberRepository;

//  @Autowired
//  private JavaMailSender emailSender;

  @Scheduled(fixedRate = 60000) // TODO: Add Cron expression to send newsletter weekly
  public void sendNewsletter() {
    logger.info("Sending newsletter...");
    Iterable<NewsletterSubscriber> subscribers = newsletterSubscriberRepository.findAll();

    List<String> recipients = new ArrayList<>();
    for (NewsletterSubscriber newsletterSubscriber : subscribers) {
      recipients.add(newsletterSubscriber.getEmail());
    }
    if(recipients.isEmpty()) {
      logger.info("No newsletter subscribers. Skip sending.");
      return;
    }
    
    SimpleMailMessage message = getNewsletterMessage();
    message.setTo((recipients.toArray(new String[0])));
//    emailSender.send(message);

    logger.info(String.format("Sent newsletter to %d subscribers", recipients.size()));
  }

  private SimpleMailMessage getNewsletterMessage() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("info@movie-rator.de");
    message.setSubject("Newsletter");
    message.setText("Lorem ipsum");

    return message;
  }
}

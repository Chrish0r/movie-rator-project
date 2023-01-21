package com.movierator.movierator;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//import com.movierator.movierator.service.EmailSenderService;

@SpringBootApplication
@EnableScheduling
public class MovieratorApplication implements WebMvcConfigurer {
	
//	 @Autowired
//	 private EmailSenderService emailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(MovieratorApplication.class, args);
	}
	
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendEmail() {
//		this.emailSenderService.sendEmail("Haxton.endgame@gmail.com", "this is the subject", "This is the body");
//	}
//	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.GERMAN);
	    return slr;
	}
	
	@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

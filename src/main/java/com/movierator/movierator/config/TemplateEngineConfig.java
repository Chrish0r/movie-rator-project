package com.movierator.movierator.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

public class TemplateEngineConfig {
  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    templateEngine.addDialect(new LayoutDialect());
    templateEngine.addDialect(new SpringSecurityDialect());
    return templateEngine;
  }
}

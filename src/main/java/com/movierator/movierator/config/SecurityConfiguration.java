package com.movierator.movierator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.movierator.movierator.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	MyUserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/start").permitAll() // index view
				.antMatchers("/api/**").permitAll()  
				.antMatchers("/user/**").permitAll()
				.antMatchers("/login").permitAll() 

				.antMatchers("/admin/**", "/settings/**").hasAuthority("ADMIN") // only admins can access this page
				// more permissions here....
				.antMatchers("/moderator/**", "/settings/**").hasAuthority("MODERATOR") // only moderators can access
																						// this page
				.antMatchers("/regular-user/**", "/settings/**").hasAuthority("REGULAR_USER") // only regular (and
																									// registered)
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.and().logout().logoutSuccessUrl("/login").invalidateHttpSession(true)

				.permitAll();

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}

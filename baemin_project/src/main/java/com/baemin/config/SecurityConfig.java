package com.baemin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baemin.config.auth.LoginSuccess;
import com.baemin.config.auth.OAuth2DetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccess loginSuccess;
	
	@Autowired
	OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest()
				.permitAll()
			.and()
				.formLogin()
				.loginPage("/auth/signin")
				.loginProcessingUrl("/auth/signin")
				.successHandler(loginSuccess)
				.failureUrl("/auth/failed")
			.and()
				.oauth2Login()
				.loginPage("/")
				.successHandler(loginSuccess)
				.userInfoEndpoint()
				.userService(oAuth2DetailsService);
	}
	
}

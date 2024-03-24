package com.pelican.pelicanapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

	  @Bean
	  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	    http
	 		.csrf((crsf) -> crsf.disable())
	        .authorizeHttpRequests(requests -> requests
	        		.requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()
	        		)
	        .httpBasic(Customizer.withDefaults());	//http (not https)
	    return http.build();
	  }
	
}

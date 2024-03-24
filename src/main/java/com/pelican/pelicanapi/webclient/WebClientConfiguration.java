package com.pelican.pelicanapi.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

	@Bean
	public WebClient jsphWebClient() {
		return WebClient.create("https://jsonplaceholder.typicode.com");
	}
	
	
}

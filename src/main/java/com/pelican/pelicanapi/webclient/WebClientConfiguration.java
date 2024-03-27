package com.pelican.pelicanapi.webclient;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {

	@Bean
	public WebClient jsphWebClient() {
		return WebClient.builder()
				.baseUrl("https://jsonplaceholder.typicode.com")
				.clientConnector(
						new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofSeconds(10))))
				.build();
	}
	
	
}

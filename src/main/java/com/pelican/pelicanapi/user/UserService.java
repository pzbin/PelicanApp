package com.pelican.pelicanapi.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.pelican.pelicanapi.post.dto.PostDto;
import com.pelican.pelicanapi.post.mapping.PostMapper;
import com.pelican.pelicanapi.webclient.JsphApiPaths;
import lombok.NonNull;

@Service
public class UserService {

	private WebClient webClient;
	
	@Autowired
	public UserService(WebClient jsphWebClient, PostMapper postMapper) {
		this.webClient = jsphWebClient;
	}
	
	public boolean validateUserId(Integer userId) {
		
		ResponseEntity<String> response = webClient
				.get()
				.uri(uri -> uri.path(JsphApiPaths.USER_BY_ID.getPath()).build(userId.toString()))
				.retrieve()
				.toEntity(String.class)
				.onErrorReturn(ResponseEntity.notFound().build())
				.block();	//sync call

		return response.getStatusCode().is2xxSuccessful();
	}
	
	
	public List<PostDto> getUserPosts(@NonNull Integer userId) {
			
			return webClient
					.get()
					.uri(uri -> uri.path(JsphApiPaths.POSTS_BY_USER_ID.getPath()).build(userId.toString()))
					.retrieve()
					.toEntityList(PostDto.class)
					.block()
					.getBody();	//sync call
	}
	
}

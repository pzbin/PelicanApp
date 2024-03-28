package com.pelican.pelicanapi.webclient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum JsphApiPaths {

	USERS("/users"),
	USER_BY_ID("/users/{userId}"),
	POSTS_BY_USER_ID("/users/{userId}/posts"),
	
	POSTS("/posts"),
	POST_BY_ID("/posts/{postId}")
	;
	
	private String path;
}

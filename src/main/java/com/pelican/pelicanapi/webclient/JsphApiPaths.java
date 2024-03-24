package com.pelican.pelicanapi.webclient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum JsphApiPaths {

	USERS("/users"),
	USER_BY_ID("/users/{userId}"),
	
	POSTS("/posts"),
	POSTS_BY_ID("/post/{postId}"),
	;
	
	private String path;
}

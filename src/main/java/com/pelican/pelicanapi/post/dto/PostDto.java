package com.pelican.pelicanapi.post.dto;

import lombok.Data;
@Data
public class PostDto {

	private int id;
	private int userId;
	private String title;
	private String body;
	
}

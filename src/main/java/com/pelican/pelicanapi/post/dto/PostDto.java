package com.pelican.pelicanapi.post.dto;

import lombok.Data;
@Data
public class PostDto {

	private Integer id;
	private Integer userId;
	private String title;
	private String body;
	
}

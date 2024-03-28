package com.pelican.pelicanapi.post.mapping;

import org.springframework.stereotype.Component;

import com.pelican.pelicanapi.post.dto.PostDto;
import com.pelican.pelicanapi.post.orm.Post;

import lombok.NonNull;

//@Mapper
@Component
public class PostMapper {

	public PostDto postOrmToDto(@NonNull Post post){
		return PostDto.builder().id(post.getId()).userId(post.getUserId()).title(post.getTitle()).body(post.getBody()).build();
	}
	
	public Post postDtoToOrm(@NonNull PostDto post){
		return new Post(post.getId(), post.getUserId(), post.getTitle(), post.getBody());
		
	}
	
}

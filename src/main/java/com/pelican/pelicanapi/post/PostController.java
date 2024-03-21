package com.pelican.pelicanapi.post;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelican.pelicanapi.post.dto.PostDto;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/post")
public class PostController {

	@PostMapping
	public ResponseEntity<PostDto> addPost(PostDto userId){
		return ResponseEntity.ok(new PostDto());
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getUserPosts(@PathParam("userId") Integer userId){
		return ResponseEntity.ok(List.of(new PostDto()));
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<List<PostDto>> getPost(@PathParam("postId") Integer postId){
		return ResponseEntity.ok(List.of(new PostDto()));
	}
	
	@DeleteMapping
	public ResponseEntity<?> deletePost(PostDto post){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<PostDto> updatePost(PostDto post){
		return ResponseEntity.ok(new PostDto());
	}
	
}

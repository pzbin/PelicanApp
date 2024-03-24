package com.pelican.pelicanapi.post;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import com.pelican.pelicanapi.post.dto.PostContentDto;
import com.pelican.pelicanapi.post.dto.PostDto;
import com.pelican.pelicanapi.post.mapping.PostMapper;
import com.pelican.pelicanapi.user.UserService;
import com.pelican.pelicanapi.utils.validation.GeneralInputValidation;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final UserService userService;
	private final PostService postService;
	
	
	@Autowired
	public PostController(UserService userService, PostService postService, PostMapper postMapper) {
		this.userService = userService;
		this.postService = postService;
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDto> addPost(@RequestBody PostDto post){
		
		if(!userService.validateUserId(post.getUserId())){
			GeneralInputValidation.handleInvalidInput();
		}
		post = postService.addPost(post);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		
		if(!GeneralInputValidation.validateId(postId)) {
			GeneralInputValidation.handleInvalidInput();
		}
		return ResponseEntity.ok(postService.getPostById(postId));
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable("postId") Integer postId){
		
		if(!GeneralInputValidation.validateId(postId)) {
			GeneralInputValidation.handleInvalidInput();
		}
		
		if(!postService.deletePostById(postId)) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, PostService.POST_DELETE_ERR_MSG);
		}
		return ResponseEntity.ok(PostService.POST_DELETE_SUCCESS_MSG);
	}
	
	@PatchMapping(path = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer postId, @RequestBody PostContentDto content){
		
		if(!GeneralInputValidation.validateId(postId) || Objects.isNull(content.getBody()) || Objects.isNull(content.getTitle())) {
			GeneralInputValidation.handleInvalidInput();
		}
		
		return ResponseEntity.ok(postService.patchPostById(postId, content));
	}

}

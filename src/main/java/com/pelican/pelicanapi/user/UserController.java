package com.pelican.pelicanapi.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pelican.pelicanapi.post.dto.PostDto;
import com.pelican.pelicanapi.utils.validation.GeneralInputValidation;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{userId}/posts")
	protected ResponseEntity<?> getPostsByUserId(@PathVariable("userId") Integer userId){
		
		if(!GeneralInputValidation.validateId(userId)) {
			GeneralInputValidation.handleInvalidInput();
		}
		List<PostDto> posts = userService.getUserPosts(userId);
		return Objects.nonNull(posts)?
				ResponseEntity.ok(posts) : ResponseEntity.ok(new ArrayList<>());
		
	}
}

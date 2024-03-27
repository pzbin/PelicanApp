package com.pelican.pelicanapi.utils.validation;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import com.pelican.pelicanapi.post.dto.PostContentDto;
import com.pelican.pelicanapi.post.dto.PostDto;

public class GeneralInputValidation {

	private static final String CHECK_INPUT_MESSAGE = "Dear user, please correct your input!";

	public static boolean validateId(Integer id) {
		return Objects.nonNull(id) && id > -1;
	}

	/**
	 * post id not needed for new post insert - genereated in remote api
	 * other fields - at least empty strings
	 * @param post
	 * @param inserting
	 * @return boolean value for inputs valid
	 */
	public static boolean validatePostInputs(PostDto post, boolean inserting) {
		return Objects.nonNull(post) 
				&& (inserting || Objects.nonNull(post.getId()))
				&& Objects.nonNull(post.getUserId())
				&& validatePostContent(null);
	}

	public static boolean validatePostContent(PostContentDto postContent) {
		return Objects.nonNull(postContent) && Objects.nonNull(postContent.getTitle())
				&& Objects.nonNull(postContent.getBody());
	}

	public static void handleInvalidInput() {
		throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, CHECK_INPUT_MESSAGE);
	}
	
}

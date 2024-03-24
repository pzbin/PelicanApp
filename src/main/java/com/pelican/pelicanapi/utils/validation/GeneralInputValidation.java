package com.pelican.pelicanapi.utils.validation;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class GeneralInputValidation {

	private static final String CHECK_INPUT_MESSAGE = "Dear user, please correct your input!";
	
	public static boolean validateId(Integer id) {
		
		return Objects.nonNull(id) && id > -1;
	}
	
	public static void handleInvalidInput() {
		throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, CHECK_INPUT_MESSAGE);
	}
	
	
}

package com.pelican.pelicanapi.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleApiError(HttpServerErrorException ex){
    	return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}


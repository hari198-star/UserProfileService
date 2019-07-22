package com.user.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotValidException extends Exception {

	public UserNotValidException(String message) {
		super(message);
	}

	
}

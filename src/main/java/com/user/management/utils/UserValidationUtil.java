package com.user.management.utils;

import java.util.List;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.user.management.exception.UserNotValidException;
import com.user.management.model.User;
import com.user.management.repository.UserRepository;

public class UserValidationUtil {
	
	public static void validateUser(User user) throws UserNotValidException {
		if(ObjectUtils.isEmpty(user)) {
			throw new UserNotValidException("User Object cannot be empty..");
		}
		
		if(StringUtils.isEmpty(user.getEmail())) {
			throw new UserNotValidException("Email is required to create a user.");
		}
		
		if(StringUtils.isEmpty(user.getFirstName())) {
			throw new UserNotValidException("First name is required to create a user.");
		}
		
		if(StringUtils.isEmpty(user.getLastName())) {
			throw new UserNotValidException("Last name is required to create a user.");
		}
		
		if(StringUtils.isEmpty(user.getPassword())) {
			throw new UserNotValidException("Password is required to create a user.");
		}
	}
	
	public static void validateEmail(User user, UserRepository repository) throws UserNotValidException {
		List<User> users = repository.findByEmail(user.getEmail().toLowerCase());
		
		if(users.size() > 0) {
			throw new UserNotValidException("Email already exists. Cannot create user account with duplicate email.");
		}
	}

}

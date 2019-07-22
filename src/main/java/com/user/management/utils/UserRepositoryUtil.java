package com.user.management.utils;

import java.util.List;

import org.springframework.util.ObjectUtils;

import com.user.management.exception.ResourceNotFoundException;
import com.user.management.model.User;
import com.user.management.repository.UserRepository;

public class UserRepositoryUtil {
	
	public static User getUserByEmail(UserRepository userRepository, String email) throws ResourceNotFoundException {
		
		System.out.println("start:UserRepositoryUtil.getUserByEmail()");
		List<User> users = userRepository.findByEmail(email);
		System.out.println("Printing users : "+ users + " " + ObjectUtils.isEmpty(users));
	    if(ObjectUtils.isEmpty(users)) {
	    	throw new ResourceNotFoundException("User with email not found :: " + email);
	    }
	    
	    return users.get(0);
	}

}

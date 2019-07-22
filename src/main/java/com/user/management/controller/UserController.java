package com.user.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.user.management.converters.UserConverter;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.exception.UserNotValidException;
import com.user.management.model.User;
import com.user.management.repository.UserRepository;
import com.user.management.request.ChangePasswordRequest;
import com.user.management.utils.PasswordHelperUtil;
import com.user.management.utils.UserRepositoryUtil;
import com.user.management.utils.UserValidationUtil;
import com.user.management.viewobjects.UserVO;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users list.
   *
   * @return the list
   */
  @GetMapping("/users")
  public List<UserVO> getAllUsers() {
    return UserConverter.convert(userRepository.findAll());
  }

  /**
   * Gets users by id.
   *
   * @param userId the user id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<UserVO> getUsersById(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
    return ResponseEntity.ok().body(UserConverter.convert(user));
  }
  
  /**
   * Gets users by email.
   *
   * @param userId the user email
   * @return the users by email
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/user")
  public ResponseEntity<UserVO> getUserByEmail(@RequestParam String email)
      throws ResourceNotFoundException {
    
	User user = UserRepositoryUtil.getUserByEmail(userRepository, email);
    
    return ResponseEntity.ok().body(UserConverter.convert(user));
  }

  /**
   * Create user user.
   *
   * @param user the user
   * @return the user
   */
  @PostMapping("/user")
  public ResponseEntity<String> createUser(@Valid @RequestBody User user) throws UserNotValidException {
	UserValidationUtil.validateUser(user);
	UserValidationUtil.validateEmail(user, userRepository);
	String encryptedPassword = PasswordHelperUtil.generateHash(user.getPassword());
	user.setPassword(encryptedPassword);
	
	userRepository.save(user);
    return new ResponseEntity<>("User Created Successfully.", HttpStatus.OK);
  }

  /**
   * Update user response entity.
   *
   * @param userId the user email
   * @param userDetails the user details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/user")
  public ResponseEntity<String> updateUser(
		  @RequestParam String email, @Valid @RequestBody User userDetails)
      throws ResourceNotFoundException {
	  
	User user = UserRepositoryUtil.getUserByEmail(userRepository, email);
	boolean canSave = false;

    if(!StringUtils.isEmpty(userDetails.getEmail())) {
    	user.setEmail(userDetails.getEmail());
    	canSave = true;
    }
    
    if(!StringUtils.isEmpty(userDetails.getLastName())) {
    	user.setLastName(userDetails.getLastName());
    	canSave = true;
    }
    
    if(!StringUtils.isEmpty(userDetails.getFirstName())) {
    	user.setFirstName(userDetails.getFirstName());
    	canSave = true;
    }
    
    if(canSave) {
    	user.setUpdatedAt(new Date());
        userRepository.save(user);
        return new ResponseEntity<>("User updated Successfully :" + email, HttpStatus.OK);
    }
    
    return new ResponseEntity<>("Nothing to save!", HttpStatus.OK);
  }

  /**
   * Delete user map.
   *
   * @param email
   * @return the map
   */
  @DeleteMapping("/user")
  public ResponseEntity<String> deleteUser(@RequestParam String email) throws ResourceNotFoundException {
	User user = UserRepositoryUtil.getUserByEmail(userRepository, email);
    userRepository.delete(user);
    return new ResponseEntity<>("User deleted Successfully : " + email, HttpStatus.OK);
  }
  
  
  /*
   * API To authenticate user
   */
  @PostMapping("/user/login")
  //TODO : Shd create a new object to take user login credentials..
  public ResponseEntity<String> login(@Valid @RequestBody User userDetails) throws ResourceNotFoundException {
	  //TODO : Move to util class
      if(ObjectUtils.isEmpty(userDetails)) {
    	  return new ResponseEntity<>("Invalid credentials : Email and Password are required.", HttpStatus.OK);
      }
	  
	  String email = userDetails.getEmail();
	  String password = userDetails.getPassword();
	  if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
		  return new ResponseEntity<>("Invalid credentials : Email and Password are required for Login.", HttpStatus.OK); 
	  }
	  
	  User user = UserRepositoryUtil.getUserByEmail(userRepository, email);
	  
	  if(PasswordHelperUtil.checkIfPasswordsAreSame(user.getPassword(), password)) {
		  return new ResponseEntity<>("Welcome " + user.getFirstName() + " " + user.getLastName() + "!", HttpStatus.OK);
	  }
	  
	  return new ResponseEntity<>("Access denied!", HttpStatus.OK);
  }
  
  @PutMapping("/user/changepassword")
  public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) 
		  throws ResourceNotFoundException {
	  //TODO : Move all validations to util class
	  if(ObjectUtils.isEmpty(changePasswordRequest)) {
    	  return new ResponseEntity<>("Invalid credentials : Email and Password are required.", HttpStatus.OK);
      }
	  
	  String email = changePasswordRequest.getEmail();
	  String oldPassword = changePasswordRequest.getOldPassword();
	  String newPassword = changePasswordRequest.getNewPassword();
	  if(StringUtils.isEmpty(email) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
		  return new ResponseEntity<>("Invalid credentials : Email, Old/New Passwords are required.", HttpStatus.OK); 
	  }
	  
	  User user = UserRepositoryUtil.getUserByEmail(userRepository, email);
	  
	  if(PasswordHelperUtil.checkIfPasswordsAreSame(user.getPassword(), oldPassword)) {
		  user.setPassword(PasswordHelperUtil.generateHash(newPassword));
		  userRepository.save(user);
		  return new ResponseEntity<>("Password Changed successfully.", HttpStatus.OK);
	  }
	  
	  return new ResponseEntity<>("Invalid Credentials, password cannot be changed.", HttpStatus.OK);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}

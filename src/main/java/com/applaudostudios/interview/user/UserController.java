package com.applaudostudios.interview.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.applaudostudios.interview.response.BaseResponse;
import com.applaudostudios.interview.response.Response;

@RequestMapping(value = "/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<? extends Response<User>> postUser(@Valid @RequestBody(required = true)UserRegistrationRequest userRegistrationRequest){
		BaseResponse<User> userResponse = new BaseResponse<>();
		User postedUser = this.userService.saveUser(userRegistrationRequest);
		return userResponse.createResponse(HttpStatus.CREATED, "User created successfullu", postedUser);
	} 
	
	@GetMapping
	public ResponseEntity<? extends Response<User>> getUserByUsername(@RequestParam(required = true)String username){
		User retrievedUser = this.userService.findByUsername(username);
		BaseResponse<User> userResponse = new BaseResponse<>();
		return userResponse.createResponse(HttpStatus.OK, "User retrieved successfully", retrievedUser);
	}

}

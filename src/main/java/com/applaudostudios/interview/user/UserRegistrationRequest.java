package com.applaudostudios.interview.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UserRegistrationRequest {

	@JsonProperty("username")
	@NotBlank
	private String username;
	
	@JsonProperty("password")
	@NotBlank
	private String password;
	
	@JsonProperty("roles")
	@NotNull
	private List<String> userRoles = new ArrayList<>();
	
	public UserRegistrationRequest() {
		super();
	}
	
	public UserRegistrationRequest(String username, String userPassword, List<String> userRoles) {
		this.password = userPassword;
		this.username = username;
		this.userRoles = userRoles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}
	
}

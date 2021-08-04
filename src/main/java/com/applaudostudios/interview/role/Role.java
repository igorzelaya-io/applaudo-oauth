package com.applaudostudios.interview.role;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private String authority;
	
	public Role() {
		super();
	}
	
	public Role(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}

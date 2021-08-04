package com.applaudostudios.interview.role;

public enum ERole {

	USER(0),
	ADMIN(1);
	
	private final int roleStatusCode;
	
	ERole(int statusCode) {
		this.roleStatusCode = statusCode;
	}
	
	public int getERoleStatusCode() {
		return this.roleStatusCode;
	}
	
	public static ERole valueOf(int statusCode) {
		return valueOf(String.valueOf(statusCode));
	}
	
}

package com.applaudostudios.interview.user;

public enum UserStatus {

	ACTIVE(0),
	INACTIVE(1);
	
	private final int userStatusCode;
	
	UserStatus(int statusCode){
		this.userStatusCode = statusCode;
	}
	
	public int getUserStatusCode() {
		return this.userStatusCode;
	}
	
	
	
}

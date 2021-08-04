package com.applaudostudios.interview.user;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.applaudostudios.interview.role.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id", nullable = false)
	@JsonProperty
	private String userId;
	
	@Column(name = "user_name", nullable = false)
	@JsonProperty("userName")
	private String userName;
	
	@Column(name = "user_password", nullable = false)
	@JsonProperty("userPassword")
	private String userPassword;
	
	@JsonProperty
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name ="user_id"))
	@AttributeOverrides({
		@AttributeOverride(name = "authority", column = @Column(name = "authority"))
	})
	private Set<Role> userRoles = new HashSet<>();
	
	@JsonProperty("userStatus")
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status")
	private UserStatus userStatus;
	
	public User() {
		this.userId = UUID.randomUUID().toString();
	}

	public User(String userId, String userName, String userPassword, Set<Role> userRoles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRoles = userRoles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public User userName(String userName) {
		this.setUserName(userName);
		return this;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public User userPassword(String userPassword) {
		this.setUserPassword(userPassword);
		return this;
	}

	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}
	
	public User userRoles(Set<Role> userRoles) {
		this.setUserRoles(userRoles);
		return this;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	public User userStatus(UserStatus userStatus) {
		this.setUserStatus(userStatus);
		return this;
	}
	
}
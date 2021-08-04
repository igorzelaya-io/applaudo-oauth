package com.applaudostudios.interview.user;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	
	@JsonIgnore
	private String userPassword;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserDetailsImpl buildFromUser(User user) {
		List<GrantedAuthority> authorities = user.getUserRoles()
												.stream()
												.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
												.collect(Collectors.toList());
		return new UserDetailsImpl(user.getUserName(), user.getUserPassword(), authorities);
	}
	
	public UserDetailsImpl(String userName, String userPassword, Collection<? extends GrantedAuthority> authorities) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}

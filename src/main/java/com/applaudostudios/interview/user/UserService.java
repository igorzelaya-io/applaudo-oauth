package com.applaudostudios.interview.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.applaudostudios.interview.exception.ResourceNotFoundException;
import com.applaudostudios.interview.role.ERole;
import com.applaudostudios.interview.role.Role;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User retrievedUser = userRepository.findByUserName(username)
											.orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
		return UserDetailsImpl.buildFromUser(retrievedUser);
	}

	public User saveUser(UserRegistrationRequest userRegistrationRequest) {
		User user = new User();
		user.userName(userRegistrationRequest.getUsername())
			.userPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()))
			.userStatus(UserStatus.ACTIVE);
		List<String> userStringRoles = userRegistrationRequest.getUserRoles();
		Set<Role> roles = new HashSet<>();
		if(userStringRoles == null || userStringRoles.isEmpty()){
			Role userRole = new Role(ERole.USER.toString());
			roles.add(userRole);
			user.setUserRoles(roles);
		}
		else {
			userStringRoles.forEach(role -> {
				switch(role) {
					
				case "ADMIN":
						Role adminRole = new Role(ERole.ADMIN.toString());
						roles.add(adminRole);
						break;
				
				case "USER":
					Role userRole = new Role(ERole.USER.toString());
					roles.add(userRole);
					break;
				
				default:
					throw new ResourceNotFoundException(Role.class, "authority", role);
				}
			});
		}
		user.setUserRoles(roles);
		this.userRepository.save(user);
		return user;
	}
	
	public User findByUsername(String userName) {
		return this.userRepository.findByUserName(userName)
									.orElseThrow(() -> new ResourceNotFoundException(User.class, "username", userName));
	}
	
}

package com.applaudostudios.interview.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("api");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/clients", "/users").permitAll()
			.antMatchers(HttpMethod.GET, "/artists").permitAll()
			.antMatchers(HttpMethod.GET, "/artists/**").authenticated()
			.antMatchers(HttpMethod.POST, "/artists").hasRole("ADMIN")
			.anyRequest().authenticated();
	}
	
}

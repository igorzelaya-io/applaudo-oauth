package com.applaudostudios.interview.security;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
		
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		logger.error("Unauthorized error: {}", authException.getMessage());

		String errorMessage;
		
		if(authException.getCause() != null) {
			errorMessage = new StringBuilder(authException.getCause().toString()).append(" ").append(authException.getMessage()).toString();
		}
		else {
			errorMessage = authException.getMessage();
		}
		byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", errorMessage));
		response.getOutputStream().write(body);
	}

	
}

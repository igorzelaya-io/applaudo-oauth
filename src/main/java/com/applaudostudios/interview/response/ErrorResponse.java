package com.applaudostudios.interview.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.applaudostudios.interview.resolver.LowerCaseResolver;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

/**
 * Class used to custom error handling.
 * @author igorzelaya
 *
 */
@JsonTypeIdResolver(LowerCaseResolver.class)
@JsonSerialize
public class ErrorResponse {
	
	
	@JsonProperty("errorResponseHttpStatus")
	private HttpStatus errorResponseHttpStatus;
	
	@JsonProperty("errorResponseDate")
	private Instant errorResponseDate;
	
	@JsonProperty("errorResponseMessage")
	private String errorResponseMessage;
	
	@JsonProperty("errorResponseDebugMessage")
	private String errorResponseDebugMessage;
	
	@JsonProperty("errorResponseSubErrors")
	private List<SubError> errorResponseSubErrors = new ArrayList<>();
	
	
	private ErrorResponse() {
		this.errorResponseDate = Instant.now();
	}
	
	public ErrorResponse(HttpStatus status) {
		this();
		this.errorResponseHttpStatus = status;
	}
	
	
	public ErrorResponse(HttpStatus status, Throwable ex) {
		this();
		this.errorResponseHttpStatus = status;
		this.errorResponseMessage = "Unexpected error.";
		this.errorResponseDebugMessage = ex.getLocalizedMessage();
	}
	
	public ErrorResponse(HttpStatus status, String message, Throwable ex) {
		this();
		this.errorResponseHttpStatus = status;
		this.errorResponseMessage = message;
		this.errorResponseDebugMessage = ex.getLocalizedMessage();
	}
	
	private void addSubError(SubError subError) {
		this.errorResponseSubErrors.add(subError);
	}
	
	private void addValidationError(String object, String message) {
		this.addSubError(new ValidationError(object, message));
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		this.addSubError(new ValidationError(object, field, rejectedValue, message));
	}
	
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), 
								fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors
			.stream()
			.forEach((fieldError) -> {
				this.addValidationError(fieldError);
			});
	}
	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationError(List<ObjectError> objectErrors) {
		objectErrors
			.stream()
			.forEach((objectError) -> this.addValidationError(objectError));
	}
	
	
	/**
	 * Method for adding error of Constraint Violation, normally used when a hybernate @Validated validation fails.
	 * @param constraintViolation
	 */
	private void addValidationError(ConstraintViolation<?> constraintViolation) {
		this.addValidationError(constraintViolation.getRootBeanClass().getSimpleName(), 
				constraintViolation.getPropertyPath().toString(),
				constraintViolation.getInvalidValue(),
				constraintViolation.getMessage());
	}
	
	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations
			.stream()
			.forEach((constraintViolation) -> this.addValidationError(constraintViolation));
	}

	public HttpStatus getErrorResponseHttpStatus() {
		return errorResponseHttpStatus;
	}

	public void setErrorResponseHttpStatus(HttpStatus errorResponseHttpStatus) {
		this.errorResponseHttpStatus = errorResponseHttpStatus;
	}

	public Instant getErrorResponseDate() {
		return errorResponseDate;
	}

	public void setErrorResponseDate(Instant errorResponseDate) {
		this.errorResponseDate = errorResponseDate;
	}

	public String getErrorResponseMessage() {
		return errorResponseMessage;
	}

	public void setErrorResponseMessage(String errorResponseMessage) {
		this.errorResponseMessage = errorResponseMessage;
	}

	public String getErrorResponseDebugMessage() {
		return errorResponseDebugMessage;
	}

	public void setErrorResponseDebugMessage(String errorResponseDebugMessage) {
		this.errorResponseDebugMessage = errorResponseDebugMessage;
	}

	public List<SubError> getErrorResponseSubErrors() {
		return errorResponseSubErrors;
	}

	public void setErrorResponseSubErrors(List<SubError> errorResponseSubErrors) {
		this.errorResponseSubErrors = errorResponseSubErrors;
	}
	
}

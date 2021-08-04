package com.applaudostudios.interview.exception;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.applaudostudios.interview.response.ErrorResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	private final static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
																		  HttpHeaders headers, HttpStatus status, WebRequest request){
		String error = new StringBuilder(ex.getParameterName()).append(" parameter is name").toString();
		return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
																	 HttpHeaders headers, HttpStatus httpStatus, WebRequest request){
		StringBuilder stringBuilder = new StringBuilder(ex.getContentType().toString()).append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes()	
							.stream()
							.forEach(mediaType -> stringBuilder.append(mediaType).append(", "));
		return buildResponseEntity(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, stringBuilder.substring(0, stringBuilder.length() - 2), ex));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ErrorResponse response){
		return new ResponseEntity<>(response, response.getErrorResponseHttpStatus());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status,
																  WebRequest request){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
		response.setErrorResponseMessage("Validation error.");
		response.addValidationErrors(ex.getBindingResult().getFieldErrors());
		response.addValidationError(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(response);
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
		response.setErrorResponseMessage("Validation error");
		response.addValidationErrors(ex.getConstraintViolations());
		return buildResponseEntity(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex){
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
		response.setErrorResponseMessage(ex.getMessage());
		return buildResponseEntity(response);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request){
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		logger.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
		String error = "Malformed JSON request";
		return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		String error = "Error writing JSON output";
		return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus stauts, WebRequest request){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
		response.setErrorResponseMessage(String.format("Could not find the %s method for the URL %s", ex.getHttpMethod(), ex.getRequestURL()));
		response.setErrorResponseDebugMessage(ex.getMessage());
		return buildResponseEntity(response);
	}
	
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex){
		return buildResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND, ex));
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
																	WebRequest request){
		if(ex.getCause() instanceof ConstraintViolationException) {
			return buildResponseEntity(new ErrorResponse(HttpStatus.CONFLICT, "Database error", ex.getCause()));
		}
		return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request){
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
		response.setErrorResponseMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName() ));
		response.setErrorResponseDebugMessage(ex.getMessage());
		return buildResponseEntity(response);
	}
	
}

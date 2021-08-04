package com.applaudostudios.interview.response;

public class ValidationError extends SubError{

	private String validationErrorObject;
	
	private String validationErrorField;
	
	private Object validationErrorRejectedValue;
	
	private String validationErrorMessage;
	
	public ValidationError(String object, String message) {
		this.validationErrorObject = object;
		this.validationErrorMessage = message;
	}

	public ValidationError(String validationErrorObject, String validationErrorField,
			Object validationErrorRejectedValue, String validationErrorMessage) {
		super();
		this.validationErrorObject = validationErrorObject;
		this.validationErrorField = validationErrorField;
		this.validationErrorRejectedValue = validationErrorRejectedValue;
		this.validationErrorMessage = validationErrorMessage;
	}

	public String getValidationErrorObject() {
		return validationErrorObject;
	}

	public void setValidationErrorObject(String validationErrorObject) {
		this.validationErrorObject = validationErrorObject;
	}

	public String getValidationErrorField() {
		return validationErrorField;
	}

	public void setValidationErrorField(String validationErrorField) {
		this.validationErrorField = validationErrorField;
	}

	public Object getValidationErrorRejectedValue() {
		return validationErrorRejectedValue;
	}

	public void setValidationErrorRejectedValue(Object validationErrorRejectedValue) {
		this.validationErrorRejectedValue = validationErrorRejectedValue;
	}

	public String getValidationErrorMessage() {
		return validationErrorMessage;
	}

	public void setValidationErrorMessage(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
	}
}

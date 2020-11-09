package org.openmrs.module.datamigration.exception;

import org.openmrs.module.webservices.rest.web.response.ResponseException;

public class CustomException extends ResponseException {
	
	String message;
	
	public CustomException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}

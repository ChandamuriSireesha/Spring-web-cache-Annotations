package com.app.exception;

//unchecked exception
public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String message) {
		super(message);
	}
}

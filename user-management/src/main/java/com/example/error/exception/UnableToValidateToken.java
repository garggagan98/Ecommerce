package com.example.error.exception;

public class UnableToValidateToken extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnableToValidateToken(String message) {
		super(message);
	}

	public UnableToValidateToken(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToValidateToken(Throwable cause) {
		super(cause);
	}
}

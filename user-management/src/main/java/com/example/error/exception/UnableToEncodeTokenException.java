package com.example.error.exception;

public class UnableToEncodeTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnableToEncodeTokenException(String message) {
		super(message);
	}

	public UnableToEncodeTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToEncodeTokenException(Throwable cause) {
		super(cause);
	}
}

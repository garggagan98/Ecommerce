package com.example.error.exception;

public class DuplicateUserFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateUserFoundException(String message) {
		super(message);
	}

	public DuplicateUserFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateUserFoundException(Throwable cause) {
		super(cause);
	}
}

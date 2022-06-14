package com.example.error.exception;

public class UnableToSendMailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnableToSendMailException(String message) {
		super(message);
	}

	public UnableToSendMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToSendMailException(Throwable cause) {
		super(cause);
	}
}

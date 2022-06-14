package com.example.error.exception;

public class InvalidOtpException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOtpException(String message) {
		super(message);
	}

	public InvalidOtpException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOtpException(Throwable cause) {
		super(cause);
	}
}

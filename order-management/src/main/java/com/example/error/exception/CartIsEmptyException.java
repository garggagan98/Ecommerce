package com.example.error.exception;

public class CartIsEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CartIsEmptyException(String message) {
		super(message);
	}

	public CartIsEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CartIsEmptyException(Throwable cause) {
		super(cause);
	}
}
package com.example.error.exception;

public class ProductNotExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductNotExistException(String message) {
		super(message);
	}

	public ProductNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotExistException(Throwable cause) {
		super(cause);
	}
}

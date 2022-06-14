package com.example.error;

public class ErrorCodes {

	private ErrorCodes() {
	}

	public static final String INTERNAL_SERVER_ERROR = "10001";
	public static final String MAIL_SEND_ERROR = "10002";
	public static final String USER_ALREADY_EXISTS = "10003";
	public static final String UNABLE_TO_ENCODE_TOKEN_ERROR = "10004";
	public static final String RESOURCE_NOT_FOUND_ERROR = "10005";
	public static final String INVALID_OTP_ERROR = "10006";
	public static final String INVALID_JWT_TOKEN = "10007";
}

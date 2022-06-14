package com.example.error.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.error.ErrorCodes;
import com.example.error.ErrorMessageSource;
import com.example.error.ErrorResponse;

@ControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private ErrorMessageSource errorMessageSource;

	@ExceptionHandler({ UnableToValidateToken.class, ResourceNotFoundException.class,
			UnableToEncodeTokenException.class, DuplicateUserFoundException.class, UnableToSendMailException.class,
			Exception.class })
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		if (ex instanceof UnableToSendMailException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.MAIL_SEND_ERROR);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.FAILED_DEPENDENCY.value(), ErrorCodes.MAIL_SEND_ERROR, localizedMsg),
					HttpStatus.FAILED_DEPENDENCY);
		} else if (ex instanceof DuplicateUserFoundException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.USER_ALREADY_EXISTS);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.CONFLICT.value(), ErrorCodes.USER_ALREADY_EXISTS, localizedMsg),
					HttpStatus.CONFLICT);
		} else if (ex instanceof UnableToEncodeTokenException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.UNABLE_TO_ENCODE_TOKEN_ERROR);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
					ErrorCodes.UNABLE_TO_ENCODE_TOKEN_ERROR, localizedMsg), HttpStatus.BAD_REQUEST);
		} else if (ex instanceof ResourceNotFoundException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.RESOURCE_NOT_FOUND_ERROR);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
					ErrorCodes.RESOURCE_NOT_FOUND_ERROR, localizedMsg), HttpStatus.BAD_REQUEST);
		} else if (ex instanceof InvalidOtpException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.INVALID_OTP_ERROR);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCodes.INVALID_OTP_ERROR, localizedMsg),
					HttpStatus.BAD_REQUEST);
		} else if (ex instanceof UnableToValidateToken) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.INVALID_JWT_TOKEN);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCodes.INVALID_JWT_TOKEN, localizedMsg),
					HttpStatus.BAD_REQUEST);
		} else {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCodes.INTERNAL_SERVER_ERROR, localizedMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

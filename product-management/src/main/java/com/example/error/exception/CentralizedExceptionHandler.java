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

	@ExceptionHandler({ AuthorizationException.class, ResourceNotFoundException.class, ProductNotExistException.class,
			Exception.class })
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		if (ex instanceof ProductNotExistException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.PRODUCT_NOT_EXISTS_ERROR);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorCodes.PRODUCT_NOT_EXISTS_ERROR, localizedMsg),
					HttpStatus.NOT_FOUND);
		} else if (ex instanceof ResourceNotFoundException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.RESOURCE_NOT_FOUND);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorCodes.RESOURCE_NOT_FOUND, localizedMsg),
					HttpStatus.NOT_FOUND);
		} else if (ex instanceof AuthorizationException) {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.AUTHORIZATION_ERROR);
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCodes.AUTHORIZATION_ERROR, localizedMsg),
					HttpStatus.BAD_REQUEST);
		} else {
			String localizedMsg = errorMessageSource.getMessage(ErrorCodes.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCodes.INTERNAL_SERVER_ERROR, localizedMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

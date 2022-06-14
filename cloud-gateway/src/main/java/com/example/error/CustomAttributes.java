package com.example.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CustomAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, options);
		Throwable throwable = getError(request);
		if (throwable instanceof ResponseStatusException) {
			ResponseStatusException ex = (ResponseStatusException) throwable;
			errorAttributesMap.put("message", ex.getMessage());
			errorAttributesMap.put("developerMessage", "A Response Status Exception Happened");
			return errorAttributesMap;
		} else if (throwable instanceof WebClientResponseException) {
			WebClientResponseException ex = (WebClientResponseException) throwable;
			errorAttributesMap.put("message", ex.getMessage());
			errorAttributesMap.put("developerMessage", "A Web Client Response Exception Happened");
			return errorAttributesMap;
		} else if (throwable instanceof AuthorizationException) {
			AuthorizationException ex = (AuthorizationException) throwable;
			errorAttributesMap.put("message", ex.getMessage());
			errorAttributesMap.put("developerMessage",
					"Missing authorization information or Incorrect authorization structure");
			return errorAttributesMap;
		} else
			return errorAttributesMap;
	}

}

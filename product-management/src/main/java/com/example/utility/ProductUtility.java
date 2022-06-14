package com.example.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.dto.ValidatedTokenResponse;
import com.example.error.exception.AuthorizationException;
import com.example.error.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductUtility {

	@Autowired
	private RestTemplate restTemplate;

	public String fetchUserByToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			log.info("fetching user based on token !!");
			String token = authorizationHeader.substring("Bearer ".length());
			ValidatedTokenResponse response = restTemplate.postForObject(
					"http://user-management-service/user/validateToken?token=" + token, null,
					ValidatedTokenResponse.class);
			if (response != null) {
				return response.getUsername();
			} else {
				throw new ResourceNotFoundException("Unable to fetch user based on token!!");
			}
		} else {
			throw new AuthorizationException("Either authorizationHeader is null or having wrong format !!");
		}
	}
}

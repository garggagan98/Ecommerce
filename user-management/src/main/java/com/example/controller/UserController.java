package com.example.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.ApiResponse;
import com.example.dto.UserDto;
import com.example.dto.ValidateOtpRequest;
import com.example.dto.ValidatedTokenResponse;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDto userDto, HttpServletRequest request)
			throws UnsupportedEncodingException {
		User registerNewUser = userService.registerNewUser(userDto, UserUtils.getSiteURL(request));
		return new ResponseEntity<>(
				new ApiResponse(true, "User Registered Successfully -> " + registerNewUser.getEmail()), HttpStatus.OK);
	}

	@PostMapping("/validateToken")
	public ResponseEntity<ValidatedTokenResponse> validateToken(@RequestParam String token) {
		log.info("Trying to validate token {}", token);
		ValidatedTokenResponse response = userService.checkToken(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/verify")
	public ModelAndView handleAccountVerification(@RequestParam String code) {
		String alertMessage;
		if (Boolean.TRUE.equals(userService.verify(code))) {
			alertMessage = "Email verified successfully.";
		} else {
			alertMessage = "User is either already verified or an invalid token is supplied.";
		}
		Map<String, Object> params = new HashMap<>();
		params.put("alertMessage", alertMessage);
		return new ModelAndView("verifyUserAccount", params);
	}

	@GetMapping("/generateOTP")
	public ResponseEntity<ApiResponse> generateOTP(@RequestParam String email) {
		userService.generateOtp(email);
		return new ResponseEntity<>(new ApiResponse(true, "OTP sent successfully !!"), HttpStatus.OK);
	}

	@PostMapping("/validateOTP")
	public ResponseEntity<ApiResponse> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest,
			HttpServletRequest request) {
		String token = userService.validateOtp(validateOtpRequest, request);
		return new ResponseEntity<>(new ApiResponse(true, token), HttpStatus.OK);
	}

}

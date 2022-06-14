package com.example.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.example.dto.UserDto;
import com.example.dto.ValidateOtpRequest;
import com.example.dto.ValidatedTokenResponse;
import com.example.entity.User;

public interface UserService {
	User registerNewUser(UserDto userDto, String siteURL) throws UnsupportedEncodingException;

	Boolean verify(String verificationCode);

	void generateOtp(String email);

	String validateOtp(ValidateOtpRequest validateOtpRequest, HttpServletRequest request);

	ValidatedTokenResponse checkToken(String jwtToken);
}

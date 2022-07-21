package com.example.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.dto.UserDto;
import com.example.dto.ValidateOtpRequest;
import com.example.dto.ValidatedTokenResponse;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.error.exception.DuplicateUserFoundException;
import com.example.error.exception.InvalidOtpException;
import com.example.error.exception.ResourceNotFoundException;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.utils.OTPService;
import com.example.utils.UserUtils;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserUtils userUtils;
	@Autowired
	private OTPService otpService;

	@Override
	public User registerNewUser(UserDto userDto, String siteURL) throws UnsupportedEncodingException {
		Optional<User> userByEmail = userRepository.findByEmail(userDto.getEmail());
		System.out.println(siteURL);
		if (userByEmail.isEmpty()) {
			User user = modelMapper.map(userDto, User.class);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setVerificationCode(RandomString.make(64));
			user.setCreatedOn(new Timestamp(new Date().getTime()));
			user.setModifiedOn(new Timestamp(new Date().getTime()));
			user.setIsActive(Boolean.FALSE);
			user.setUsername(userDto.getName());
			user.setEmail(userDto.getEmail());
			Role role = roleRepository.findById(502).get();
			user.getRoles().add(role);
			userUtils.sendVerificationEmail(user, siteURL);
			return userRepository.save(user);
		} else {
			throw new DuplicateUserFoundException("Duplicate User Found !!");
		}
	}

	@Override
	public Boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		if (user == null || user.getIsActive()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setIsActive(Boolean.TRUE);
			userRepository.save(user);
			return true;
		}
	}

	@Override
	public void generateOtp(String email) {
		if (StringUtils.isNotEmpty(email)) {
			User user = userRepository.findByEmail(email)
					.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
			// Generate OTP code
			String twilioMessage = otpService.generateOTP(email)
					+ " is your verification code from drone delivery system.";
			userUtils.sendVerificationSMS(user, twilioMessage);
		} else {
			throw new ResourceNotFoundException("Could not found user associated with " + email);
		}
	}

	@Override
	public String validateOtp(ValidateOtpRequest validateOtpRequest, HttpServletRequest request) {
		String email = validateOtpRequest.getEmail();
		if (StringUtils.isNotEmpty(validateOtpRequest.getOtp()) && StringUtils.isNotEmpty(email)) {
			Integer serverOtp = otpService.getOtp(email);
			Integer clientOtp = Integer.valueOf(validateOtpRequest.getOtp());
			if (serverOtp.equals(clientOtp)) {
				otpService.clearOTP(email);
				// Generate token to login
				User user = userRepository.findByEmail(email)
						.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
				Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
				return JWT.create().withSubject(email)
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);

			} else {
				throw new InvalidOtpException("One time password is not valid !!");
			}
		} else {
			throw new ResourceNotFoundException("Could not found user associated with " + email);
		}
	}

	@Override
	public ValidatedTokenResponse checkToken(String jwtToken) {
		Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(jwtToken);
		String username = decodedJWT.getSubject();
		String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Arrays.stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		return new ValidatedTokenResponse(authenticationToken.isAuthenticated(), authenticationToken.getName());
	}

}

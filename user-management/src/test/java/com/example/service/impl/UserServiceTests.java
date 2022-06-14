package com.example.service.impl;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserPrototype;
import com.example.service.UserService;
import com.example.utils.OTPService;
import com.example.utils.UserUtils;

@ExtendWith(SpringExtension.class)
class UserServiceTests {

	@InjectMocks
	private UserService userService = new UserServiceImpl();
	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Spy
	private ModelMapper modelMapper = new ModelMapper();
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private UserUtils userUtils;
	@Mock
	private OTPService otpService;

	@Test
	void testVerify() {
		// Given
		User user = new User(1, "Gagan", "garggagan98@gmail.com", "Gagan@1234", "123344555", null, true,
				List.of("Address"), null, null, null);
		given(userRepository.findByVerificationCode("")).willReturn(UserPrototype.getMockedUser());
		// When
		userService.verify("");
		// Then
		BDDMockito.then(userRepository).should().save(user);
	}

	@Test
	void testGenerateOtp() {
		// Given
		given(userRepository.findByEmail("garggagan98@gmail.com"))
				.willReturn(Optional.of(UserPrototype.getMockedUser()));
		given(otpService.generateOTP("garggagan98@gmail.com")).willReturn(23245);
		// When
		userService.generateOtp("garggagan98@gmail.com");
		// Then
		BDDMockito.then(userRepository).should().findByEmail("garggagan98@gmail.com");
	}
}

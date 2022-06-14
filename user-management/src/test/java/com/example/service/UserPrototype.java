package com.example.service;

import java.util.List;

import com.example.entity.User;

public class UserPrototype {

	public static User getMockedUser() {
		return new User(1, "Gagan", "garggagan98@gmail.com", "Gagan@1234", "123344555", "", false, List.of("Address"),
				null, null, null);
	}

}

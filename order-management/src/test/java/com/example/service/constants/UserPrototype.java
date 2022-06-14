package com.example.service.constants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.entity.Category;
import com.example.entity.Order;
import com.example.entity.Product;
import com.example.entity.User;

public class UserPrototype {

	public static String EMAIL = "garggagan98@gmail.com";

	public static Optional<User> getMockedUser() {
		List<String> address = new ArrayList<>();
		address.add("77 Vidhya Nagar Behind Sapna Sangeeta Indore");
		return Optional.of(new User(1, "Gagan Garg", EMAIL, "Gagan@123", "+917771997372", null, true, address,
				new Timestamp(2), new Timestamp(3), new HashSet<>(), new ArrayList<>()));
	}

	public static List<Order> getMockedOrders() {
		List<Order> orderList = new ArrayList<>();
		Order order = new Order(1, new Date(2), 12000.00, new ArrayList<>(), new User());
		orderList.add(order);
		return orderList;
	}

	public static CartDto getMockedCartDto() {
		List<CartItemDto> list = new ArrayList<CartItemDto>();
		Product p = new Product(1, "Product Name", "", 12000.00, "Description", new Category(), new ArrayList<>());
		CartItemDto cartItemDto = new CartItemDto(1, 10, p);
		list.add(cartItemDto);
		return new CartDto(list, 12000.00);
	}

}

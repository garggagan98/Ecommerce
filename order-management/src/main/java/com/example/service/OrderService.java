package com.example.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.entity.Order;

@Service
public interface OrderService {
	void placeOrder(HttpServletRequest httpServletRequest);

	List<Order> listOrders(HttpServletRequest httpServletRequest);
}

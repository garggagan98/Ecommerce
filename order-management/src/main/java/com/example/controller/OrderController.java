package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.entity.Order;
import com.example.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/add")
	public ResponseEntity<ApiResponse> placeOrder(HttpServletRequest httpServletRequest) {
		orderService.placeOrder(httpServletRequest);
		return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders(HttpServletRequest httpServletRequest) {
		List<Order> orderDtoList = orderService.listOrders(httpServletRequest);

		return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
	}
}

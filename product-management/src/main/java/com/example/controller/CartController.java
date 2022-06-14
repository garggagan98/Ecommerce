package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddToCartDto;
import com.example.dto.ApiResponse;
import com.example.dto.CartDto;
import com.example.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
			HttpServletRequest httpServletRequest) {
		cartService.addToCart(addToCartDto, httpServletRequest);
		return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<CartDto> getCartItems(HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(cartService.getAllItems(httpServletRequest), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteCartItemByUser(HttpServletRequest httpServletRequest) {
		cartService.deleteUserCartItems(httpServletRequest);
		return new ResponseEntity<>(new ApiResponse(true, "deleted from cart"), HttpStatus.OK);
	}
}

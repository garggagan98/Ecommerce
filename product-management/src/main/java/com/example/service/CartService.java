package com.example.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.dto.AddToCartDto;
import com.example.dto.CartDto;

@Service
public interface CartService {
	void addToCart(AddToCartDto addToCartDto, HttpServletRequest httpServletRequest);

	CartDto getAllItems(HttpServletRequest httpServletRequest);

	public void deleteUserCartItems(HttpServletRequest httpServletRequest);
}

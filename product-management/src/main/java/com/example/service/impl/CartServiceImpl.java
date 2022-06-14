package com.example.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AddToCartDto;
import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.dto.User;
import com.example.entity.Cart;
import com.example.entity.Product;
import com.example.error.exception.ResourceNotFoundException;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.utility.ProductUtility;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductUtility utility;

	@Override
	public void addToCart(AddToCartDto addToCartDto, HttpServletRequest httpServletRequest) {
		String username = utility.fetchUserByToken(httpServletRequest);
		User fetchedUser = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
		Product fetchedProduct = productService.getProductById(addToCartDto.getProductId());
		Cart cart = new Cart();
		cart.setProduct(fetchedProduct);
		cart.setUser(fetchedUser);
		cart.setQuantity(addToCartDto.getQuantity());
		cart.setCreatedDate(new Date());
		cartRepository.save(cart);
	}

	@Override
	public CartDto getAllItems(HttpServletRequest httpServletRequest) {
		String username = utility.fetchUserByToken(httpServletRequest);
		User fetchedUser = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
		List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(fetchedUser);
		List<CartItemDto> cartItems = new ArrayList<>();
		for (Cart cart : cartList) {
			CartItemDto cartItemDto = new CartItemDto(cart.getId(), cart.getQuantity(), cart.getProduct());
			cartItems.add(cartItemDto);
		}
		double totalCost = 0;
		for (CartItemDto cartItemDto : cartItems) {
			totalCost += (cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
		}
		return new CartDto(cartItems, totalCost);
	}

	@Override
	public void deleteUserCartItems(HttpServletRequest httpServletRequest) {
		String username = utility.fetchUserByToken(httpServletRequest);
		User fetchedUser = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
		cartRepository.deleteByUser(fetchedUser);
	}

}

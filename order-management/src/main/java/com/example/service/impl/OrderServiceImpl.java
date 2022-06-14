package com.example.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.User;
import com.example.error.exception.CartIsEmptyException;
import com.example.error.exception.ResourceNotFoundException;
import com.example.repository.OrderItemsRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.service.OrderService;
import com.example.utility.OrderUtils;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	OrderItemsRepository orderItemsRepository;
	@Autowired
	private OrderUtils orderUtils;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void placeOrder(HttpServletRequest httpServletRequest) {
		// check if cart is empty
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		CartDto cartDto = restTemplate
				.exchange("http://product-management-service/cart", HttpMethod.GET, requestEntity, CartDto.class)
				.getBody();
		if (cartDto != null && !cartDto.getCartItems().isEmpty()) {
			List<CartItemDto> cartItemDtoList = cartDto.getCartItems();
			String userByToken = orderUtils.fetchUserByToken(httpServletRequest);
			User fetchedUser = userRepository.findByEmail(userByToken)
					.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
			// create the order and save it
			Order newOrder = new Order();
			newOrder.setCreatedDate(new Date());
			newOrder.setUser(fetchedUser);
			newOrder.setTotalPrice(cartDto.getTotalCost());
			
			orderRepository.save(newOrder);
			for (CartItemDto cartItemDto : cartItemDtoList) {
				// create orderItem and save each one
				OrderItem orderItem = new OrderItem();
				orderItem.setCreatedDate(new Date());
				orderItem.setPrice(cartItemDto.getProduct().getPrice());
				orderItem.setProduct(cartItemDto.getProduct());
				orderItem.setQuantity(cartItemDto.getQuantity());
				orderItem.setOrder(newOrder);
				// add to order item list
				orderItemsRepository.save(orderItem);
			}
			// delete cart items by user
			restTemplate.exchange("http://product-management-service/cart/delete", HttpMethod.DELETE, requestEntity,
					Void.class);
		} else {
			throw new CartIsEmptyException("Cart is empty");
		}
	}

	@Override
	public List<Order> listOrders(HttpServletRequest httpServletRequest) {
		String userByToken = orderUtils.fetchUserByToken(httpServletRequest);
		User fetchedUser = userRepository.findByEmail(userByToken)
				.orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
		return orderRepository.findAllByUserOrderByCreatedDateDesc(fetchedUser);
	}
}

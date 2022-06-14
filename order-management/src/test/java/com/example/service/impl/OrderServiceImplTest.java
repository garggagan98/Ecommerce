package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.repository.OrderItemsRepository;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.service.OrderService;
import com.example.service.constants.UserPrototype;
import com.example.utility.OrderUtils;

@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {
	@InjectMocks
	private OrderService orderService = new OrderServiceImpl();
	@Mock
	private HttpServletRequest httpServletRequest;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private OrderUtils orderUtils;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private OrderItemsRepository orderItemsRepository;

	@Test
	@DisplayName("Given- AUser When- PlaceOrder Then- OrderIsPlacedAndCartIsEmptied")
	void testPlaceOrder() {
		// Given
		OrderItem orderItem = new OrderItem();

		Order newOrder = new Order();
		newOrder.setCreatedDate(new Date());
		newOrder.setUser(UserPrototype.getMockedUser().get());
		newOrder.setTotalPrice(UserPrototype.getMockedCartDto().getTotalCost());

		CartItemDto cartItems = UserPrototype.getMockedCartDto().getCartItems().get(0);

		orderItem.setCreatedDate(new Date());
		orderItem.setCreatedDate(new Date());
		orderItem.setPrice(cartItems.getProduct().getPrice());
		orderItem.setProduct(cartItems.getProduct());
		orderItem.setQuantity(cartItems.getQuantity());
		orderItem.setOrder(newOrder);

		when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod>any(),
				Mockito.<HttpEntity<Void>>any(), Mockito.<Class<CartDto>>any()))
				.thenReturn(ResponseEntity.ok(UserPrototype.getMockedCartDto()));

		given(orderUtils.fetchUserByToken(httpServletRequest)).willReturn(UserPrototype.EMAIL);
		given(userRepository.findByEmail(UserPrototype.EMAIL)).willReturn(UserPrototype.getMockedUser());

		orderService.placeOrder(httpServletRequest);

		BDDMockito.then(orderRepository).should().save(newOrder);
		BDDMockito.then(orderItemsRepository).should().save(orderItem);
	}

	@Test
	@DisplayName("Given- AUser When- FetchUserByTokenIsCalled Then- ListOfOrderIsReturned")
	void testListOrders() {
		// Given - Arrange
		given(orderUtils.fetchUserByToken(httpServletRequest)).willReturn(UserPrototype.EMAIL);
		given(userRepository.findByEmail(UserPrototype.EMAIL)).willReturn(UserPrototype.getMockedUser());
		given(orderRepository.findAllByUserOrderByCreatedDateDesc(UserPrototype.getMockedUser().get()))
				.willReturn(UserPrototype.getMockedOrders());

		// When - Act
		List<Order> listOrders = orderService.listOrders(httpServletRequest);

		// Then - Assert
		assertNotNull(listOrders);
		assertEquals(1, listOrders.size());
	}

}

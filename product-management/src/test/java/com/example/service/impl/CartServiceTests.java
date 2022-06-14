package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.dto.AddToCartDto;
import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.dto.User;
import com.example.entity.Cart;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.constant.ProductPrototype;
import com.example.utility.ProductUtility;

@ExtendWith(SpringExtension.class)
class CartServiceTests {
	@InjectMocks
	private CartService cartService = new CartServiceImpl();
	@Mock
	private CartRepository cartRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductService productService;
	@Mock
	private ProductUtility utility;
	@Mock
	private HttpServletRequest httpServletRequest;

	Product mockedProduct = ProductPrototype.getMockedProduct();
	Optional<User> mockedUser = ProductPrototype.getMockedUser();

	@BeforeEach
	void init() {
		given(utility.fetchUserByToken(httpServletRequest)).willReturn(ProductPrototype.EMAIL);
		given(userRepository.findByEmail(ProductPrototype.EMAIL)).willReturn(mockedUser);
	}

	@Test
	@DisplayName("Given- AItemsToCart When- AddToCartIsCalled Then- ItemsShouldBeSavedToCart")
	void testAddToCart() {
		// Given
		Cart cart = new Cart();
		cart.setProduct(new Product(101, "Gagan Garg", "", 12000.00, "Description", new Category(), new ArrayList<>(),
				new HashSet<>()));
		cart.setUser(new User(1, "Gagan Garg", "garggagan98@gmail.com", "Gagan@123", "+917771997372", null, true,
				List.of("77 Vidhya Nagar Behind Sapna Sangeeta Indore"), new Timestamp(2), new Timestamp(3),
				new HashSet<>()));
		cart.setQuantity(12);
		cart.setCreatedDate(new Date());

		AddToCartDto addToCartDto = new AddToCartDto(1, 101, 12);

		given(productService.getProductById(101)).willReturn(mockedProduct);

		// When
		cartService.addToCart(addToCartDto, httpServletRequest);

		// Then
		BDDMockito.then(cartRepository).should().save(cart);

	}

	@Test
	@DisplayName("Given- CartItems When- DeleteUserCartItemsIsCalled Then- CartShouldBeEmptied")
	void testDeleteUserCartItems() {
		// Given
		cartService.deleteUserCartItems(httpServletRequest);

		// Then
		BDDMockito.then(cartRepository).should().deleteByUser(mockedUser.get());
	}

	@Test
	@DisplayName("Given- User When- GetAllItemsIsCalled Then- AllCartItemsShouldBeReturned")
	void testGetAllItems() {
		// Given
		List<CartItemDto> cartItems = new ArrayList<>();
		CartItemDto cartItemDto = new CartItemDto(1, 12, new Product(101, "Gagan Garg", "", 12000.00, "Description",
				new Category(), new ArrayList<>(), new HashSet<>()));
		cartItems.add(cartItemDto);
		CartDto cartDtoExpected = new CartDto(cartItems, 144000.0);

		given(cartRepository.findAllByUserOrderByCreatedDateDesc(mockedUser.get()))
				.willReturn(ProductPrototype.getMockedCartList());

		// When
		CartDto actualResponseCartDto = cartService.getAllItems(httpServletRequest);

		// Then
		assertEquals(cartDtoExpected, actualResponseCartDto);
	}

}

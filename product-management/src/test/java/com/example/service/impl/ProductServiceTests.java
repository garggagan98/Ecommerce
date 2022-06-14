package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.dto.ProductDto;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.service.constant.ProductPrototype;

@ExtendWith(SpringExtension.class)
class ProductServiceTests {

	@InjectMocks
	private ProductService productService = new ProductServiceImpl();

	@Mock
	private ProductRepository productRepository;

	@Spy
	private ModelMapper modelMapper = new ModelMapper();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("Given- AUser When- ListProductsIsCalled Then- ProductsListShouldBeReturned")
	void testListProducts() {
		// Given
		List<Product> productList = new ArrayList<Product>();
		productList.add(ProductPrototype.getMockedProduct());

		List<ProductDto> productDtosExpected = new ArrayList<>();
		ProductDto productDto = new ProductDto(101, "Gagan Garg", "", 12000.00, "Description", null);
		productDtosExpected.add(productDto);

		given(productRepository.findAll()).willReturn(productList);

		// When
		List<ProductDto> productsListActual = productService.listProducts();

		// Then
		assertEquals(productDtosExpected, productsListActual);
	}

	@Test
	@DisplayName("Given- AProductAndCategory When- AddProductIsCalled Then- ProductShouldBeSaved")
	void testAddProduct() {
		// Given
		ProductDto productDto = new ProductDto(101, "Gagan Garg", "", 12000.00, "Description", null);
		Category category = new Category(1, "Computers", "Everything About Computers", "", null);
		Product product = new Product(101, "Gagan Garg", "", 12000.00, "Description", category, null, null);

		// When
		productService.addProduct(productDto, category);

		// Then
		BDDMockito.then(productRepository).should().save(product);
	}

	@Test
	@DisplayName("Given- AProductId When- GetProductByIdIsCalled Then- ProductIsReturned")
	void testGetProductById() {
		// Given
		Product productExpected = new Product(101, "Gagan Garg", "", 12000.00, "Description", new Category(), null,
				null);
		given(productRepository.findById(101)).willReturn(Optional.of(ProductPrototype.getMockedProduct()));

		// When
		Product productByIdActual = productService.getProductById(101);

		// Then
		assertEquals(productExpected, productByIdActual);
	}

}

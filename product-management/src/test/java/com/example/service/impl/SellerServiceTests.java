package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.dto.SellerDto;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.Seller;
import com.example.repository.SellerRepository;
import com.example.service.SellerService;
import com.example.service.constant.ProductPrototype;

@ExtendWith(SpringExtension.class)
class SellerServiceTests {
	@InjectMocks
	private SellerService sellerService = new SellerServiceImpl();

	@Mock
	private SellerRepository sellerRepository;
	@Spy
	private ModelMapper modelMapper = new ModelMapper();

	@Test
	@DisplayName("When- ListAllSellersIsCalled Then- SellersListIsReturned")
	void testListAllSellers() {
		// Given
		List<SellerDto> sellerDtoListExpected = new ArrayList<SellerDto>();
		SellerDto sellerDto = new SellerDto(1, "Seller Name", 101);
		sellerDtoListExpected.add(sellerDto);

		given(sellerRepository.findAll()).willReturn(ProductPrototype.getMockedSellersList());

		// When
		List<SellerDto> SellersListActual = sellerService.listAllSellers();

		// Then
		assertEquals(sellerDtoListExpected, SellersListActual);
	}

	@Test
	@DisplayName("Given- SellerAndProduct When- AddSellerIsCalled Then- SellerShouldBeSaved")
	void testAddSeller() {
		// Given
		SellerDto sellerDto = new SellerDto(1, "Seller Name", 101);
		Product product = new Product(101, "Gagan Garg", "", 12000.00, "Description", new Category(), new ArrayList<>(),
				new HashSet<>());
		Seller seller = new Seller(1, "Seller Name", product);

		// When
		sellerService.addSeller(sellerDto, product);

		// Then
		BDDMockito.then(sellerRepository).should().save(seller);
	}

}

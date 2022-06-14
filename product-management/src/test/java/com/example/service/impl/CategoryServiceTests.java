package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
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

import com.example.dto.CategoryDto;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import com.example.service.constant.ProductPrototype;

@ExtendWith(SpringExtension.class)
class CategoryServiceTests {
	@InjectMocks
	private CategoryService categoryService = new CategoryServiceImpl();

	@Mock
	private CategoryRepository categoryRepository;
	@Spy
	private ModelMapper modelMapper = new ModelMapper();

	@Test
	@DisplayName("Given- User When- ListCategoriesIsCalled Then- ListOfCategoriesShouldBeReturned")
	void testListCategories() {
		// Given
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
		CategoryDto categoryDto = new CategoryDto(1, "Computers", "Everything About Computers", "");
		categoryDtos.add(categoryDto);

		given(categoryRepository.findAll()).willReturn(ProductPrototype.getMockedCategoryList());

		// when
		List<CategoryDto> categoryListActual = categoryService.listCategories();

		// Then
		assertEquals(categoryDtos, categoryListActual);
	}

	@Test
	@DisplayName("Given- AUser When- CreateCategoryIsCalled Then- CategoryShouldBeCreated")
	void testCreateCategory() {
		// Given
		CategoryDto categoryDto = new CategoryDto(1, "Computers", "Everything About Computers", "");
		Category category = new Category(1, "Computers", "Everything About Computers", "", null);

		// when
		categoryService.createCategory(categoryDto);

		// Then
		BDDMockito.then(categoryRepository).should().save(category);
	}

	@Test
	@DisplayName("Given- CategoryId When- ReadCategoryIsCalled Then- CategoryShouldBeReturned")
	void testReadCategoryInteger() {
		// Given
		Category categoryExpectedResponse = new Category(1, "Computers", "Everything About Computers", "", null);
		given(categoryRepository.findById(1)).willReturn(ProductPrototype.getMockedCategory());

		// when
		Category categoryActualResponse = categoryService.readCategory(1).get();

		// Then
		assertEquals(categoryExpectedResponse, categoryActualResponse);
	}

	@Test
	@DisplayName("Given- CategoryName When- ReadCategoryIsCalled Then- CategoryWithThatNameShouldBeReturned")
	void testReadCategoryString() {
		// Given
		Category categoryExpectedResponse = new Category(1, "Computers", "Everything About Computers", "", null);
		given(categoryRepository.findByCategoryName("Computers"))
				.willReturn(ProductPrototype.getMockedCategory().get());

		// when
		Category categoryActualResponse = categoryService.readCategory("Computers");

		// Then
		assertEquals(categoryExpectedResponse, categoryActualResponse);
	}

}

package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.dto.CategoryDto;
import com.example.entity.Category;

public interface CategoryService {

	List<CategoryDto> listCategories();

	Category createCategory(CategoryDto categoryDto);

	Optional<Category> readCategory(Integer categoryId);

	Category readCategory(String categoryName);

}

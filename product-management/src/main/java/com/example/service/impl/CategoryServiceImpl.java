package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CategoryDto;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CategoryDto> listCategories() {
		return categoryRepository.findAll().stream().map(i -> modelMapper.map(i, CategoryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Category createCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return categoryRepository.save(category);
	}

	@Override
	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Override
	public Category readCategory(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

}

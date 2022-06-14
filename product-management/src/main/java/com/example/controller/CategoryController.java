package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.CategoryDto;
import com.example.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> list = categoryService.listCategories();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto) {
		if (categoryService.readCategory(categoryDto.getCategoryName()) != null) {
			return new ResponseEntity<>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
		}
		categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
	}
}

package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApiResponse;
import com.example.dto.ProductDto;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.example.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> body = productService.listProducts();
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
		Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
		}
		Category category = optionalCategory.get();
		productService.addProduct(productDto, category);
		return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
	}
}

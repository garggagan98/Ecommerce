package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.ProductDto;
import com.example.entity.Category;
import com.example.entity.Product;

@Service
public interface ProductService {
	List<ProductDto> listProducts();

	void addProduct(ProductDto productDto, Category category);

	Product getProductById(Integer productId);
}

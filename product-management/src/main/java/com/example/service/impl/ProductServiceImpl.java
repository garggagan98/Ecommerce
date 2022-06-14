package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ProductDto;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.error.exception.ProductNotExistException;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProductDto> listProducts() {
		return productRepository.findAll().stream().map(i -> modelMapper.map(i, ProductDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public void addProduct(ProductDto productDto, Category category) {
		Product product = modelMapper.map(productDto, Product.class);
		product.setCategory(category);
		productRepository.save(product);
	}

	@Override
	public Product getProductById(Integer productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (!optionalProduct.isPresent())
			throw new ProductNotExistException("Product id is invalid " + productId);
		return optionalProduct.get();
	}
}

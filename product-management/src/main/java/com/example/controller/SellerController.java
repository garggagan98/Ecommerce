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
import com.example.dto.SellerDto;
import com.example.entity.Product;
import com.example.service.ProductService;
import com.example.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<SellerDto>> getProducts() {
		List<SellerDto> body = sellerService.listAllSellers();
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addSeller(@RequestBody SellerDto sellerDto) {
		Product productById = productService.getProductById(sellerDto.getProductId());
		if (productById.getId() == null) {
			return new ResponseEntity<>(new ApiResponse(false, "No Products Found with this " + productById.getId()),
					HttpStatus.CONFLICT);
		}
		sellerService.addSeller(sellerDto, productById);
		return new ResponseEntity<>(new ApiResponse(true, "Seller has been added"), HttpStatus.CREATED);
	}

}

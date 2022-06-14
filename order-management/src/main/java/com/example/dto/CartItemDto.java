package com.example.dto;

import com.example.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
	private Integer id;
	private Integer quantity;
	private Product product;
}

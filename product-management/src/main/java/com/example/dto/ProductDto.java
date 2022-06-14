package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	private Integer id;
	private String name;
	private String imageURL;
	private Double price;
	private String description;
	private Integer categoryId;
}

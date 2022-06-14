package com.example.service;

import java.util.List;

import com.example.dto.SellerDto;
import com.example.entity.Product;

public interface SellerService {

	List<SellerDto> listAllSellers();

	void addSeller(SellerDto sellerDto, Product productById);

}

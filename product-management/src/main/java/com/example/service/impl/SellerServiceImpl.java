package com.example.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.SellerDto;
import com.example.entity.Product;
import com.example.entity.Seller;
import com.example.repository.SellerRepository;
import com.example.service.SellerService;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<SellerDto> listAllSellers() {
		return sellerRepository.findAll().stream().map(i -> modelMapper.map(i, SellerDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public void addSeller(SellerDto sellerDto, Product productById) {
		Seller seller = modelMapper.map(sellerDto, Seller.class);
		seller.setProduct(productById);
		sellerRepository.save(seller);
	}

}

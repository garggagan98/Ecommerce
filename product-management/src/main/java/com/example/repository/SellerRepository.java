package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

}

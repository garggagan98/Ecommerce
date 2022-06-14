package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dto.User;
import com.example.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

	List<Cart> deleteByUser(User user);
}

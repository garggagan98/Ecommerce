package com.example.service.constant;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.example.dto.User;
import com.example.entity.Cart;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.Seller;

public class ProductPrototype {
	public static String EMAIL = "garggagan98@gmail.com";

	public static Optional<User> getMockedUser() {
		List<String> address = new ArrayList<>();
		address.add("77 Vidhya Nagar Behind Sapna Sangeeta Indore");
		return Optional.of(new User(1, "Gagan Garg", EMAIL, "Gagan@123", "+917771997372", null, true, address,
				new Timestamp(2), new Timestamp(3), new HashSet<>()));
	}

	public static Product getMockedProduct() {
		return new Product(101, "Gagan Garg", "", 12000.00, "Description", new Category(), new ArrayList<>(),
				new HashSet<>());
	}

	public static List<Cart> getMockedCartList() {
		List<Cart> cartList = new ArrayList<Cart>();
		Cart cart = new Cart(1, new Date(), getMockedProduct(), new User(), 12);
		cartList.add(cart);
		return cartList;
	}

	public static List<Category> getMockedCategoryList() {
		List<Category> categories = new ArrayList<Category>();
		Category category = new Category(1, "Computers", "Everything About Computers", "", new HashSet<>());
		categories.add(category);
		return categories;
	}

	public static Optional<Category> getMockedCategory() {
		return Optional.of(new Category(1, "Computers", "Everything About Computers", "", null));
	}

	public static List<Seller> getMockedSellersList() {
		List<Seller> sellers = new ArrayList<Seller>();
		Seller seller = new Seller(1, "Seller Name", new Product(101, "Gagan Garg", "", 12000.00, "Description",
				new Category(), new ArrayList<>(), new HashSet<>()));
		sellers.add(seller);
		return sellers;
	}
}
